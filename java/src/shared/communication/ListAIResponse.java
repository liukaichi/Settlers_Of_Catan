/**
 * 
 */
package shared.communication;

import java.util.*;

import com.google.gson.*;

import shared.definitions.AIType;

/**
 * @author cstaheli
 *
 */
public class ListAIResponse
{
    private List<AIType> aiTypes;

    /**
     * TODO
     */
    public ListAIResponse()
    {
        aiTypes = new ArrayList<>();
    }

    /**
     * Instantiate a ListAIResponse from JSON with the injected facade
     * @param json JSON of the ListAIResponse
     */
    public ListAIResponse(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonArray array = (JsonArray) parser.parse(json);
        for (JsonElement element : array)
        {
            JsonPrimitive prim = (JsonPrimitive) element;
            AIType type = AIType.valueOf(prim.getAsString());
            aiTypes.add(type);
        }
    }

    public List<AIType> getAITypes()
    {
        return Collections.unmodifiableList(aiTypes);
    }
}
