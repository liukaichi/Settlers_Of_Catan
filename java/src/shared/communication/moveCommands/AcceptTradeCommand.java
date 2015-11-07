package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.AbstractServerFacade;
import shared.definitions.*;
import shared.model.bank.resource.Resources;

/**
 * acceptTrade command object.
 * 
 * @author Cache Staheli
 *
 */
public class AcceptTradeCommand extends MoveCommand implements JsonSerializer<AcceptTradeCommand>
{
    /**
     * Whether you accept the trade or not.
     */
    private boolean willAccept;

    /**
     * @param playerIndex
     * @param willAccept
     */
    public AcceptTradeCommand(PlayerIndex playerIndex, boolean willAccept)
    {
        super(MoveType.acceptTrade, playerIndex);
        this.willAccept = willAccept;
    }

    /**
     * Instantiate a AcceptTradeCommand from JSON with the injected facade
     * @param json JSON of the AcceptTradeCommand
     * @param facade Facade to be used
     */
    public AcceptTradeCommand(String json, AbstractServerFacade facade)
    {
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(tradeObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(tradeObject.getAsJsonPrimitive("receiver").getAsInt());
        this.willAccept = tradeObject.get("willAccept").getAsBoolean();
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(AcceptTradeCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("willAccept", willAccept);
        return obj;
    }

    /**
     * Calls acceptTrade method on the Server Facade
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute()
    {
        return null;
    }
}
