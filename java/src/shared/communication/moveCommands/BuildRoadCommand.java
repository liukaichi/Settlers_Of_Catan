package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.locations.EdgeLocation;

/**
 * buildRoad command object.
 * 
 * @author Cache Staheli
 *
 */
public class BuildRoadCommand extends MoveCommand implements JsonSerializer<BuildRoadCommand>
{

    /**
     * Location of the Road.
     */
    private EdgeLocation roadLocation;
    /**
     * Whether this is placed for free (setup).
     */
    private boolean isFree;


    /**
     * Constructs a BuildRoadCommand based off of the index of the builder, the location, and whether it is free or not.
     * @param playerIndex the builder of the road.
     * @param roadLocation the location of the road.
     * @param isFree whether or not the road is free. True in setup round.
     */
    public BuildRoadCommand(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean isFree)
    {
        super(MoveType.buildRoad, playerIndex);
        this.roadLocation = roadLocation;
        this.isFree = isFree;
    }

    /**
     * Constructs a BuildRoadCommand from json.
     * @param json the json to parse.
     */
    public BuildRoadCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject buildRoadCommand = (JsonObject) parser.parse(json);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(BuildRoadCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("roadLocation", src.roadLocation.serialize(src.roadLocation, src.roadLocation.getClass(), context));
        obj.addProperty("free", isFree);
        return obj;
    }

    @Override public String execute()
    {
        return null;
    }
}
