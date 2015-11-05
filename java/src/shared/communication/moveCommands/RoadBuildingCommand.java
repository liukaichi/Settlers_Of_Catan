package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.locations.EdgeLocation;

/**
 * Road_Building command object.
 * 
 * @author Cache Staheli
 * @see EdgeLocation
 *
 */
public class RoadBuildingCommand extends MoveCommand implements JsonSerializer<RoadBuildingCommand>
{
    /**
     * @param playerIndex the index of the player playing the card.
     * @param spot1 the first location to build a road.
     * @param spot2 the second location to build a road.
     */
    public RoadBuildingCommand(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        super(MoveType.Road_Building, playerIndex);
        this.spot1 = spot1;
        this.spot2 = spot2;
    }

    /**
     * The location of the road(s) to be built.
     */
    private EdgeLocation spot1, spot2;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(RoadBuildingCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("spot1", src.spot1.serialize(src.spot1, src.spot1.getClass(), context));
        obj.add("spot2", src.spot2.serialize(src.spot2, src.spot2.getClass(), context));
        return obj;
    }

    @Override public String execute()
    {
        return null;
    }
}
