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
     * 
     */
    public ListAIResponse()
    {
        aiTypes = new ArrayList<>();
    }

    /**
     * Parses the Json to create a List of AIType.
     * 
     * @param json
     *        the json to parse
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
