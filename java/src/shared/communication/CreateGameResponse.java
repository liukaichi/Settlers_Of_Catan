package shared.communication;

import com.google.gson.*;

import client.data.GameInfo;
import server.facade.AbstractServerFacade;

/**
 * Class that creates the game response
 * 
 * @author dtaylor
 *
 */
public class CreateGameResponse
{
    private GameInfo gameInfo;

    public CreateGameResponse(int id, String title)
    {
        gameInfo = new GameInfo(id, title);
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

}
