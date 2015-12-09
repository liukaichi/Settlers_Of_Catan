package shared.communication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.manager.GameManager;
import shared.definitions.CatanColor;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.GameQueryException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ID of the game the player wants to join, and the color they want.
 *
 * @author Cache Staheli
 */
public class JoinGameRequest extends CatanCommand
{
    private int gameID;
    private CatanColor color;
    private static final Logger LOGGER = Logger.getLogger(JoinGameRequest.class.getName());

    /**
     * Initializes a JoinGameRequest with the gameID of the game to join, and the color the player is joining with.
     *
     * @param id    the gameID of the game to join.
     * @param color the color the player the is joining.
     */
    public JoinGameRequest(int id, CatanColor color)
    {
        this.gameID = id;
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
        this.gameID = joinGameObject.getAsJsonPrimitive("id").getAsInt();
        this.color = CatanColor.valueOf(joinGameObject.getAsJsonPrimitive("color").getAsString().toUpperCase());
    }

    /**
     * Calls JoinGame method on the Server Facade
     *
     * @param playerID gameID of the player.
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute(int playerID) throws CatanException
    {
        LOGGER.info(String.format("Executing JoinGameRequest(%d, %s) with player %d", gameID, color.name(), playerID));
        GameManager.getInstance().joinGame(playerID, gameID, color);
        //persistMe(playerId);
        return Integer.toString(gameID);
    }

    public void persistMe(int playerID)
    {
    }
}
