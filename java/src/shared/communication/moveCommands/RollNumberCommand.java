package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;

/**
 * rollNumber command object.
 * 
 * @author Cache Staheli
 *
 */
public class RollNumberCommand extends MoveCommand implements JsonSerializer<RollNumberCommand>
{
    /**
     * The number rolled.
     */
    private int number;

    /**
     * @param playerIndex
     * @param number
     */
    public RollNumberCommand(PlayerIndex playerIndex, int number)
    {
        super(MoveType.rollNumber, playerIndex);
        this.number = number;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(RollNumberCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("number", number);
        return obj;
    }

    @Override public void execute()
    {

    }
}
