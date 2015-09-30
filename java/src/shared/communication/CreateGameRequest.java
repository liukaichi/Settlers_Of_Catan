package shared.communication;

/**
 * The name and settings to use for the new game. Can be sent as form encoded
 * key-value pairs as well. The new game's ID can be read from the response.
 * 
 * @author Cache Staheli
 *
 */
public class CreateGameRequest
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
}
