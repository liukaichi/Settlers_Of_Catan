/**
 *
 */
package shared.communication.moveCommands;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;

import java.lang.reflect.Type;

/**
 * Used merely for serialization since several classes serialize exactly the same way.
 *
 * @author cstaheli
 */
public abstract class SimpleSerializableCommand extends MoveCommand implements JsonSerializer<SimpleSerializableCommand>
{

    private static final long serialVersionUID = 5919426977623219793L;

    /**
     * @param type        the type of move.
     * @param playerIndex the index of the player sending the command.
     */
    public SimpleSerializableCommand(MoveType type, PlayerIndex playerIndex)
    {
        super(type, playerIndex);
    }

    /**
     * Default empty constructor.
     */
    protected SimpleSerializableCommand()
    {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(SimpleSerializableCommand src, Type srcType,
            JsonSerializationContext context)
    {
        return serializeCommand(src);
    }
}
