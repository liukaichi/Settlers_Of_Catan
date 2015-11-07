package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import client.data.RobPlayerInfo;
import server.facade.AbstractServerFacade;
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
     * The information about the player being robbed.
     */
    private PlayerIndex victimIndex;
    /**
     * The location of the robber.
     */
    private HexLocation location;

    public RobPlayerCommand()
    {
        super(MoveType.robPlayer, PlayerIndex.NONE);
    }
    /**
     * @param playerIndex
     * @param victimIndex
     * @param location
     */
    public RobPlayerCommand(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        super(MoveType.robPlayer, playerIndex);
        this.victimIndex = victimIndex;
        this.location = location;
    }

    /**
     * Instantiate a RobPlayerCommand from JSON with the injected facade
     * @param json JSON of the RobPlayerCommand
     * @param facade Facade to be used
     */
    public RobPlayerCommand(String json, AbstractServerFacade facade)
    {

    }


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
        obj.addProperty("victimIndex", src.victimIndex.getIndex());
        JsonObject location = new JsonObject();
        location.addProperty("x", Integer.toString(src.location.getX()));
        location.addProperty("y", Integer.toString(src.location.getY()));
        obj.add("location", location);
        return obj;
    }

    /**
     * Robs the player and moves the robber the new hex location
     * @return JSON of the new model after the player is robbed.
     */
    @Override public String execute()
    {
        return null;
    }
}
