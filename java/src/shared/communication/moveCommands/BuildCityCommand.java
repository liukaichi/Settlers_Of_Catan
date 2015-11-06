package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.AbstractServerFacade;
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
    public BuildCityCommand(String json, AbstractServerFacade facade)
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
    @Override
    public JsonElement serialize(BuildCityCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("vertexLocation", src.cityLocation.serialize(src.cityLocation, src.cityLocation.getClass(), context));
        return obj;
    }

    /**
     * Calls the ServerFacade to build a city with the data stored inside this command.
     */
    @Override public String execute()
    {
        return null;
    }
}
