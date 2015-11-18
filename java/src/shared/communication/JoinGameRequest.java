package shared.communication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.manager.GameManager;
import shared.definitions.CatanColor;
import shared.definitions.exceptions.CatanException;

/**
 * The ID of the game the player wants to join, and the color they want.
 *
 * @author Cache Staheli
 */
public class JoinGameRequest implements CatanCommand
{
    private int id;
    private CatanColor color;

    /**
     * Initializes a JoinGameRequest with the id of the game to join, and the color the player is joining with.
     *
     * @param id    the id of the game to join.
     * @param color the color the player the is joining.
     */
    public JoinGameRequest(int id, CatanColor color)
    {
        this.id = id;
        this.color = color;
    }

    /**
     * Instantiate a CreateGameRequest from JSON.
     *
     * @param json JSON of the CreateGameRequest
     */
    public JoinGameRequest(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject joinGameObject = (JsonObject) parser.parse(json);
        this.id = joinGameObject.getAsJsonPrimitive("id").getAsInt();
        this.color = CatanColor.valueOf(joinGameObject.getAsJsonPrimitive("color").getAsString().toUpperCase());
    }

    /**
     * Calls JoinGame method on the Server Facade
     *
     * @param gameID possibly not used.
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute(int gameID) throws CatanException
    {
        GameManager.getInstance().joinGame(gameID, id, color);
        return Integer.toString(id);
    }
}
