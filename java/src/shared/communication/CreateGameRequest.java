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
public class CreateGameRequest implements CatanCommand
{
    private boolean randomTiles, randomNumbers, randomPorts;
    private String name;

    public CreateGameRequest(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;
        this.randomPorts = randomPorts;
        this.name = name;
    }

    /**
     * Instantiate a CreateGameRequest from JSON with the injected facade
     * @param json JSON of the CreateGameRequest
     * @param facade Facade to be used
     */
    public CreateGameRequest(String json, AbstractServerFacade facade)
    {
        JsonParser parser = new JsonParser();
        JsonObject createGameObject = (JsonObject) parser.parse(json);
        this.randomTiles = createGameObject.getAsJsonPrimitive("randomTiles").getAsBoolean();
        this.randomNumbers = createGameObject.getAsJsonPrimitive("randomNumbers").getAsBoolean();
        this.randomPorts = createGameObject.getAsJsonPrimitive("randomPorts").getAsBoolean();
        this.name = createGameObject.get("name").getAsString();
    }

    /**
     * Calls createGame method on the Server Facade
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute()
    {
        return null;
    }
}
