package shared.communication.moveCommands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;

import java.lang.reflect.Type;

/**
 * buildRoad command object.
 *
 * @author Cache Staheli
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
     *
     * @param playerIndex  the builder of the road.
     * @param roadLocation the location of the road.
     * @param isFree       whether or not the road is free. True in setup round.
     */
    public BuildRoadCommand(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean isFree)
    {
        super(MoveType.buildRoad, playerIndex);
        this.roadLocation = roadLocation;
        this.isFree = isFree;
    }

    /**
     * Instantiate a BuildRoadCommand from JSON.
     *
     * @param json JSON of the BuildRoadCommand
     */
    public BuildRoadCommand(String json)
    {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(BuildRoadCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("roadLocation", src.roadLocation.serialize(src.roadLocation, src.roadLocation.getClass(), context));
        obj.addProperty("free", isFree);
        return obj;
    }

    /**
     * Calls the ServerFacade to build a road for the person and at the location specified in this command.
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID)
    {
        return AbstractServerFacade.getInstance().buildRoad(gameID, getPlayerIndex(), roadLocation, isFree).toString();
    }
}
