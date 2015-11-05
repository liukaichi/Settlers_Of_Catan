package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.IServerFacade;
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
     * @param playerIndex
     * @param roadLocation
     * @param isFree
     */
    public BuildRoadCommand(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean isFree)
    {
        super(MoveType.buildRoad, playerIndex);
        this.roadLocation = roadLocation;
        this.isFree = isFree;
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

    @Override public String execute(IServerFacade facade)
    {
        return null;
    }
}
