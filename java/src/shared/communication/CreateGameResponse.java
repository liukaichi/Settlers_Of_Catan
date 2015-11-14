package shared.communication;

import client.data.GameInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class that creates the game response
 * 
 * @author dtaylor
 *
 */
public class CreateGameResponse
{
    private GameInfo gameInfo;

    /**
     * Initializes the response from the server with the given ID and title.
     * @param id the id of the game that was created.
     * @param title the title of the game that was created.
     */
    public CreateGameResponse(int id, String title)
    {
        gameInfo = new GameInfo(id, title);
    }

    /**
     * Initializes a new CreateGameResponse from a GameInfo.
     * @param game the game to initialize from.
     */
    public CreateGameResponse(GameInfo game)
    {
        this.gameInfo = game;
    }

    /**
     * Instantiate a CreateGameResponse from JSON with the injected facade
     * @param json JSON of the CreateGameResponse
     */
    public CreateGameResponse(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(json);
        String title = obj.get("title").getAsString();
        int id = obj.get("id").getAsInt();
        this.gameInfo = new GameInfo(id, title);
    }

    public GameInfo getGameInfo()
    {
        return gameInfo;
    }

    public int getGameID()
    {
        return gameInfo.getId();
    }

    @Override public String toString()
    {
        JsonObject response = new JsonObject();
        response.addProperty("title", gameInfo.getTitle());
        response.addProperty("id", gameInfo.getId());
        return response.toString();
    }
}
