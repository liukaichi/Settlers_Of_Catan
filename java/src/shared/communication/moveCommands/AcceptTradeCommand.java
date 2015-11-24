package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * acceptTrade command object.
 *
 * @author Cache Staheli
 */
public class AcceptTradeCommand extends MoveCommand implements JsonSerializer<AcceptTradeCommand>
{
    /**
     * Whether you accept the trade or not.
     */
    private boolean willAccept;

    private static final Logger LOGGER = Logger.getLogger(AcceptTradeCommand.class.getName());

    /**
     * Instantiates a AcceptTradeCommand with the given PlayerIndex and whether the player will accept the trade.
     *
     * @param playerIndex the index of the player sending the trade reply.
     * @param willAccept  whether or not the player will accept the trade offered.
     */
    public AcceptTradeCommand(PlayerIndex playerIndex, boolean willAccept)
    {
        super(MoveType.acceptTrade, playerIndex);
        this.willAccept = willAccept;
    }

    /**
     * Instantiate a AcceptTradeCommand from JSON.
     *
     * @param json JSON of the AcceptTradeCommand
     */
    public AcceptTradeCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(tradeObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(tradeObject.getAsJsonPrimitive("playerIndex").getAsInt());
        this.willAccept = tradeObject.get("willAccept").getAsBoolean();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(AcceptTradeCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("willAccept", willAccept);
        return obj;
    }

    /**
     * Calls acceptTrade method on the Server Facade
     *
     * @param gameID the ID of the game to call accept trade on.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing AcceptTradeCommand(%d, %s) for game %d", getPlayerIndex().getIndex(), willAccept, gameID));
        String model = AbstractServerFacade.getInstance().acceptTrade(gameID, this.getPlayerIndex(), willAccept).toString();
        LOGGER.fine(model);
        return model;
    }
}
