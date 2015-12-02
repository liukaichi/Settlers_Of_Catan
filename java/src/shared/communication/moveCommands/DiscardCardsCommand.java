package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.model.bank.resource.Resources;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * discardCards command object.
 *
 * @author Cache Staheli
 * @see Resources
 */
public class DiscardCardsCommand extends MoveCommand implements JsonSerializer<DiscardCardsCommand>
{
    /**
     * List of discarded cards.
     */
    private Resources discardedCards;

    private static final Logger LOGGER = Logger.getLogger(DiscardCardsCommand.class.getName());

    /**
     * Packages a discard cards command for use by the server.
     *
     * @param index       the player discarding cards.
     * @param brickAmount the amount of brick to discard.
     * @param oreAmount   the amount of ore to discard.
     * @param sheepAmount the amount of sheep to discard.
     * @param wheatAmount the amount of wheat to discard.
     * @param woodAmount  the amount of wood to discard.
     */
    public DiscardCardsCommand(PlayerIndex index, int brickAmount, int woodAmount, int sheepAmount, int wheatAmount, int oreAmount)
    {
        super(MoveType.discardCards, index);
        discardedCards = new Resources(brickAmount, woodAmount, sheepAmount, wheatAmount, oreAmount);
    }

    /**
     * Instantiate a DiscardCardsCommand from JSON.
     *
     * @param json JSON of the DiscardCardsCommand.
     */
    public DiscardCardsCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject discardCardsObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(discardCardsObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(discardCardsObject.getAsJsonPrimitive("playerIndex").getAsInt());
        this.discardedCards = new Resources(discardCardsObject.get("discardedCards").toString());

    }

    /**
     * Packages a discard cards command for use by the server.
     *
     * @param index     the player discarding cards.
     * @param resources the resources to discard.
     */
    public DiscardCardsCommand(PlayerIndex index, Resources resources)
    {
        super(MoveType.discardCards, index);
        discardedCards = resources;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(DiscardCardsCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        // obj.add("discardedCards", context.serialize(src.discardedCards));
        obj.add("discardedCards", src.discardedCards.serialize(src.discardedCards, src.discardedCards.getClass(), context));
        return obj;
    }

    /**
     * This will discard the amount of cards within the resources object to subtract from the ServerModel
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing DiscardCardsCommand(%d, %s) for game %d", getPlayerIndex().getIndex(), discardedCards, gameID));
        String model = AbstractServerFacade.getInstance().discardCards(gameID, getPlayerIndex(), this.discardedCards).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;
    }
}
