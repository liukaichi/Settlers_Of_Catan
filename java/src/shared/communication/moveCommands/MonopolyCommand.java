package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;

/**
 * Monopoly command object.
 * 
 * @author Cache Staheli
 * @see ResourceType
 *
 */
public class MonopolyCommand extends MoveCommand implements JsonSerializer<MonopolyCommand>
{
    /**
     * @param playerIndex
     * @param resource
     */
    public MonopolyCommand(PlayerIndex playerIndex, ResourceType resource)
    {
        super(MoveType.Monopoly, playerIndex);
        this.resource = resource;
    }

    /**
     * The resource the Monopoly Card applies to.
     */
    private ResourceType resource;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(MonopolyCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("resource", src.resource.toString().toLowerCase());
        return obj;
    }
}
