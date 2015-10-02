/**
 * 
 */
package shared.communication;

import java.util.*;

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
    }

    public List<AIType> getAITypes()
    {
        return Collections.unmodifiableList(aiTypes);
    }
}
