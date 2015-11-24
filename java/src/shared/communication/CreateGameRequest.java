package shared.communication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.facade.AbstractServerFacade;
import shared.definitions.exceptions.CatanException;

import java.util.logging.Logger;

/**
 * The name and settings to use for the new game. Can be sent as form encoded
 * key-value pairs as well. The new game's ID can be read from the response.
 *
 * @author Cache Staheli
 */
public class CreateGameRequest implements CatanCommand
{
    private boolean randomTiles, randomNumbers, randomPorts;
    private static final Logger LOGGER = Logger.getLogger(CreateGameRequest.class.getName());
    private String name;

    /**
     * Creates a CreateGameRequest with the specified options and name.
     *
     * @param randomTiles   whether the created game will have randomly generated hexes.
     * @param randomNumbers whether the created game will have randomly placed numbers on the hexes.
     * @param randomPorts   whether the created game will have randomly placed ports.
     * @param name          the name of the game.
     */
    public CreateGameRequest(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;
        this.randomPorts = randomPorts;
        this.name = name;
    }

    /**
     * Instantiate a CreateGameRequest from JSON.
     *
     * @param json JSON of the CreateGameRequest
     */
    public CreateGameRequest(String json)
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
     *
     * @param gameID possibly not used.
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(
                String.format("executing CreateGameRequest(%s, %s, %s, %s) for game %d", String.valueOf(randomTiles), String.valueOf(randomNumbers),
                        String.valueOf(randomPorts), name, gameID));
        String model = AbstractServerFacade.getInstance().createGame(randomTiles, randomNumbers, randomPorts, name).toString();
        LOGGER.fine(model);
        return model;
    }
}
