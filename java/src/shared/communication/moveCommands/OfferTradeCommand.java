package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * offerTrade command object.
 *
 * @author Cache Staheli
 * @see TradeOffer
 */
public class OfferTradeCommand extends MoveCommand implements JsonSerializer<OfferTradeCommand>
{
    private static final long serialVersionUID = 8231967200724159794L;
    /**
     * What you get (+) and what you give (-), as well as with whom.
     */
    private TradeOffer offer;

    private static final Logger LOGGER = Logger.getLogger(OfferTradeCommand.class.getName());

    /**
     * Instantiates a OfferTradeCommand. If resources are negative, it means they are being sent, and positive
     * means they are being received.
     *
     * @param sender   the PlayerIndex of the sender.
     * @param receiver the PlayerIndex of the receiver.
     * @param brick    the amount of brick being sent or received.
     * @param wood     the amount of wood being sent or received.
     * @param sheep    the amount of sheep being sent or received.
     * @param wheat    the amount of wheat being sent or received.
     * @param ore      the amount of ore being sent or received.
     * @see TradeOffer
     */
    public OfferTradeCommand(PlayerIndex sender, PlayerIndex receiver, int brick, int wood, int sheep, int wheat, int ore)
    {
        super(MoveType.offerTrade, sender);
        offer = new TradeOffer(sender, receiver, brick, wood, sheep, wheat, ore);
        this.type = MoveType.offerTrade;
    }

    /**
     * Instantiate a OfferTradeCommand from JSON.
     *
     * @param json JSON of the OfferTradeCommand.
     */
    public OfferTradeCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject offerTradeCommand = (JsonObject) parser.parse(json);
        String moveType = offerTradeCommand.get("type").getAsString();
        int sender = offerTradeCommand.get("playerIndex").getAsInt();
        int receiver = offerTradeCommand.get("receiver").getAsInt();
        JsonObject offer = offerTradeCommand.get("offer").getAsJsonObject();
        int brick = offer.get("brick").getAsInt();
        int wood = offer.get("wood").getAsInt();
        int sheep = offer.get("sheep").getAsInt();
        int wheat = offer.get("wheat").getAsInt();
        int ore = offer.get("ore").getAsInt();

        this.type = MoveType.valueOf(moveType);
        this.playerIndex = PlayerIndex.fromInt(sender);
        this.offer = new TradeOffer(playerIndex, PlayerIndex.fromInt(receiver), brick, wood, sheep, wheat, ore);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(OfferTradeCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) src.offer.serialize(src.offer, src.offer.getClass(), context);
        obj.remove("sender");
        obj.addProperty("playerIndex", src.playerIndex.getIndex());
        obj.addProperty("type", src.type.toString());
        return obj;
    }

    /**
     * Calls offerTrade method on the Server Facade
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing OfferTradeCommand(%d, %s) for game %d", getPlayerIndex().getIndex(), offer.toString(), gameID));
        String model = AbstractServerFacade.getInstance().offerTrade(gameID, offer.getSenderIndex(), this.offer, offer.getReceiverIndex()).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        AbstractServerFacade.getInstance().playAITurns(gameID);
        return model;
    }
}
