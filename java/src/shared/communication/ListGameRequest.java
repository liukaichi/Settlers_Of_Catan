package shared.communication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.facade.AbstractServerFacade;

/**
 * The name and settings to use for the new game. Can be sent as form encoded
 * key-value pairs as well. The new game's ID can be read from the response.
 *
 * @author Cache Staheli
 *
 */
public class ListGameRequest implements CatanCommand
{
    private boolean randomTiles, randomNumbers, randomPorts;
    private String name;

    /**
     * Creates a ListGameRequest.
     */
    public ListGameRequest(String json)
    {
       //do nothing
    }

    /**
     * Calls createGame method on the Server Facade
     * @return Json String representing the current state of the Server Model
     * @param gameID possibly not used.
     */
    @Override public String execute(int gameID)
    {
        return AbstractServerFacade.getInstance().listGames().toString();
    }
}
