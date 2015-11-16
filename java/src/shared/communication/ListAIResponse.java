/**
 *
 */
package shared.communication;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import shared.definitions.AIType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the Json that is returned from the server using the context "games/list".
 */
public class ListAIResponse
{
    private List<AIType> aiTypes;

    private ListAIResponse()
    {
        aiTypes = new ArrayList<>();
    }

    /**
     * Constructs the ListAIResponse from a list of AIType
     *
     * @param types the types of AI to add.
     */
    public ListAIResponse(List<AIType> types)
    {
        this();
        this.aiTypes = types;
    }

    /**
     * Instantiate a ListAIResponse from JSON with the injected facade
     *
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
