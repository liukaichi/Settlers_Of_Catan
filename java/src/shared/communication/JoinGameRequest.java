package shared.communication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.facade.AbstractServerFacade;
import shared.definitions.CatanColor;

/**
 * The ID of the game the player wants to join, and the color they want.
 * 
 * @author Cache Staheli
 *
 */
public class JoinGameRequest implements CatanCommand
{
    private int id;
    private CatanColor color;

    public JoinGameRequest(int id, CatanColor color)
    {
        this.id = id;
        this.color = color;
    }

    /**
     * Instantiate a CreateGameRequest from JSON with the injected facade
     * @param json JSON of the CreateGameRequest
     * @param facade Facade to be used
     */
    public JoinGameRequest(String json, AbstractServerFacade facade)
    {
        JsonParser parser = new JsonParser();
        JsonObject joinGameObject = (JsonObject) parser.parse(json);
        this.id = joinGameObject.getAsJsonPrimitive("id").getAsInt();
        this.color = CatanColor.valueOf(joinGameObject.getAsJsonPrimitive("color").getAsString());
    }

    /**
     * Calls JoinGame method on the Server Facade
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute()
    {
        return null;
    }
}
