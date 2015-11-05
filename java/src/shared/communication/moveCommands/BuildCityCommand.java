package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.IServerFacade;
import shared.definitions.*;
import shared.locations.VertexLocation;

/**
 * buildCity command object.
 * 
 * @author Cache Staheli
 * @see VertexLocation
 *
 */
public class BuildCityCommand extends MoveCommand implements JsonSerializer<BuildCityCommand>
{

    /**
     * Location of the city.
     */
    private VertexLocation cityLocation;

    /**
     * @param playerIndex
     * @param cityLocation
     */
    public BuildCityCommand(PlayerIndex playerIndex, VertexLocation cityLocation)
    {
        super(MoveType.buildCity, playerIndex);
        this.cityLocation = cityLocation;
    }

    /**
     * Parses and creates a BuildCityCommand from Json.
     * @param json the Json to parse.
     */
    public BuildCityCommand(String json)
    {
        super(MoveType.buildCity, PlayerIndex.NONE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(BuildCityCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("vertexLocation", src.cityLocation.serialize(src.cityLocation, src.cityLocation.getClass(), context));
        return obj;
    }

    /**
     * Calls the ServerFacade to build a city with the data stored inside this command.
     * @param facade
     */
    @Override public String execute(IServerFacade facade)
    {
        return null;
    }
}
