package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.definitions.exceptions.CatanException;
import shared.model.player.TradeOffer;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * maritimeTrade command object.
 *
 * @author Cache Staheli
 * @see TradeRatio
 * @see TradeOffer
 */
public class MaritimeTradeCommand extends MoveCommand implements JsonSerializer<MaritimeTradeCommand>
{
    private static final long serialVersionUID = -9010157899674313852L;
    /**
     * The ratio at which the maritime offer is being extended.
     */
    private TradeRatio ratio;
    /**
     * What is being offered in the trade.
     */
    private ResourceType inputResource, outputResource;

    private static final Logger LOGGER = Logger.getLogger(MaritimeTradeCommand.class.getName());

    /**
     * Instantiates a MaritimeTradeCommand with the given player, ratio of trade, and which resources area being traded.
     *
     * @param playerIndex    the player sending the maritime trade.
     * @param ratio          the ratio at which the trade is being offered.
     * @param inputResource  the resource the player is sending.
     * @param outputResource the resource the player is receiving.
     */
    public MaritimeTradeCommand(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource, ResourceType outputResource)
    {
        super(MoveType.maritimeTrade, playerIndex);
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }

    /**
     * Instantiate a MaritimeTradeCommand from JSON.
     *
     * @param json JSON of the MaritimeTradeCommand.
     */
    public MaritimeTradeCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(tradeObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(tradeObject.getAsJsonPrimitive("playerIndex").getAsInt());
        this.ratio = TradeRatio.fromInt(tradeObject.get("ratio").getAsInt());
        this.inputResource = ResourceType.valueOf(tradeObject.get("inputResource").getAsString().toUpperCase());
        this.outputResource = ResourceType.valueOf(tradeObject.get("outputResource").getAsString().toUpperCase());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(MaritimeTradeCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("ratio", src.ratio.getRatio());
        obj.addProperty("inputResource", src.inputResource.toString().toLowerCase());
        obj.addProperty("outputResource", src.outputResource.toString().toLowerCase());
        return obj;
    }

    /**
     * Calls maritimeTrade method on the Server Facade
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing MaritimeTradeCommand(%d, %d, %s, %s) for game %d", getPlayerIndex().getIndex(), ratio.getRatio(),
                inputResource.toString(), outputResource.toString(), gameID));
        String model = AbstractServerFacade.getInstance().maritimeTrade(gameID, getPlayerIndex(), this.ratio, this.inputResource, this.outputResource)
                .toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;

    }
}
