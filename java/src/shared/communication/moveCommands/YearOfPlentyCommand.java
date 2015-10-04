package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;

/**
 * Year_of_Plenty command object.
 * 
 * @author Cache Staheli
 * @see ResourceType
 *
 */
public class YearOfPlentyCommand extends MoveCommand implements JsonSerializer<YearOfPlentyCommand>
{
    /**
     * The resource(s) you collect.
     */
    private ResourceType resource1, resource2;

    /**
     * @param playerIndex
     * @param resource1
     * @param resource2
     */
    public YearOfPlentyCommand(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        super(MoveType.Year_Of_Plenty, playerIndex);
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(YearOfPlentyCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("resource1", src.resource1.toString().toLowerCase());
        obj.addProperty("resource2", src.resource2.toString().toLowerCase());
        return obj;
    }

}
