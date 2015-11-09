package shared.definitions;

import java.lang.reflect.Type;

import com.google.gson.*;

/**
 * The allowed AI player types. <br>
 * Currently, LARGEST_ARMY is the only supported type.
 *
 */
public enum AIType implements JsonSerializer<AIType>
{
    /**
     * Largest Army AIType. Tries to build the largest army during game play.
     */
    LARGEST_ARMY;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(AIType src, Type srcType, JsonSerializationContext context)
    {
        JsonObject type = new JsonObject();
        type.addProperty("AIType", src.toString());
        return type;
    }
}
