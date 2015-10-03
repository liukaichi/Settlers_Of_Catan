/**
 * 
 */
package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;

/**
 * Used merely for serialization
 * 
 * @author cstaheli
 *
 */
public abstract class SimpleCommandSerializer extends MoveCommand implements JsonSerializer<SimpleCommandSerializer>
{

    /**
     * @param type
     * @param playerIndex
     */
    public SimpleCommandSerializer(MoveType type, PlayerIndex playerIndex)
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
    public JsonElement serialize(SimpleCommandSerializer src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        return obj;
    }

}
