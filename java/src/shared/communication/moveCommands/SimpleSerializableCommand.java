/**
 * 
 */
package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;

/**
 * Used merely for serialization since several classes serialize exactly the same way.
 * 
 * @author cstaheli
 *
 */
public abstract class SimpleSerializableCommand extends MoveCommand implements JsonSerializer<SimpleSerializableCommand>
{

    /**
     * @param type the type of move.
     * @param playerIndex the index of the player sending the command.
     */
    public SimpleSerializableCommand(MoveType type, PlayerIndex playerIndex)
    {
        super(type, playerIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(SimpleSerializableCommand src, Type srcType, JsonSerializationContext context)
    {
        return serializeCommand(src);
    }

    /**
     * Default empty constructor.
     */
    protected SimpleSerializableCommand()
    {
    	
    }
}
