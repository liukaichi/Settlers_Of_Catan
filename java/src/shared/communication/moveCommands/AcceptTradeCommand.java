package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;

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
}
