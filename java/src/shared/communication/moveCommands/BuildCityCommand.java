package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.locations.VertexLocation;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * buildCity command object.
 *
 * @author Cache Staheli
 * @see VertexLocation
 */
public class BuildCityCommand extends MoveCommand implements JsonSerializer<BuildCityCommand>
{

    private static final long serialVersionUID = 1196972435140717156L;
    /**
     * Location of the city.
     */
    private VertexLocation cityLocation;

    private static final Logger LOGGER = Logger.getLogger(BuildCityCommand.class.getName());

    /**
     * Instantiates a BuildCityCommand with the given player and location.
     *
     * @param playerIndex  the index of the player building the city.
     * @param cityLocation the location of the city being built.
     */
    public BuildCityCommand(PlayerIndex playerIndex, VertexLocation cityLocation)
    {
        super(MoveType.buildCity, playerIndex);
        this.cityLocation = cityLocation;
    }

    /**
     * Instantiate a BuildCityCommand from JSON with the injected facade
     *
     * @param json JSON of the BuildCityCommand
     */
    public BuildCityCommand(String json)
    {
        super(MoveType.buildCity, PlayerIndex.NONE);
        JsonParser parser = new JsonParser();
        JsonObject buildCityCommand = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(buildCityCommand.get("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(buildCityCommand.get("playerIndex").getAsInt());
        JsonObject location = (JsonObject) buildCityCommand.get("vertexLocation");
        cityLocation = new VertexLocation(location.toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(BuildCityCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("vertexLocation", src.cityLocation.serialize(src.cityLocation, src.cityLocation.getClass(), context));
        return obj;
    }

    /**
     * Calls the ServerFacade to build a city with the data stored inside this command.
     *
     * @param gameID the ID of the game for which to build the city.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing BuildCityCommand(%d, %s) for game %d", getPlayerIndex().getIndex(), cityLocation.toString(), gameID));
        String model = AbstractServerFacade.getInstance().buildCity(gameID, getPlayerIndex(), cityLocation).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;
    }
}
