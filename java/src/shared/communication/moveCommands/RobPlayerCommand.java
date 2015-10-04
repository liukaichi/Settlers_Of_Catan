package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import client.data.RobPlayerInfo;
import shared.definitions.*;
import shared.locations.HexLocation;

/**
 * robPlayer command object
 * 
 * @author Cache Staheli
 * @see RobPlayerInfo
 * @see HexLocation
 */
public class RobPlayerCommand extends MoveCommand implements JsonSerializer<RobPlayerCommand>
{
    /**
     * @param playerIndex
     * @param robberInfo
     * @param location
     */
    public RobPlayerCommand(PlayerIndex playerIndex, RobPlayerInfo robberInfo, HexLocation location)
    {
        super(MoveType.robPlayer, playerIndex);
        this.robberInfo = robberInfo;
        this.location = location;
    }

    /**
     * The information about the player being robbed.
     */
    private RobPlayerInfo robberInfo;
    /**
     * The location of the robber.
     */
    private HexLocation location;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(RobPlayerCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("victimIndex", src.robberInfo.getNormalizedPlayerIndex());
        JsonObject location = new JsonObject();
        location.addProperty("x", Integer.toString(src.location.getX()));
        location.addProperty("y", Integer.toString(src.location.getY()));
        obj.add("location", location);
        return obj;
    }
}
