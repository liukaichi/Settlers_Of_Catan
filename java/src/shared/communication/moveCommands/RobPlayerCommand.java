package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.locations.HexLocation;

/**
 * robPlayer command object
 *
 * @author Cache Staheli
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
     * Instantiates a RobPlayerCommand  with the given player, victim, and new location of the robber.
     * @param playerIndex the index of the player who is robbing.
     * @param victimIndex the idex of the player being robbed.
     * @param location the location where the robber is now.
     */
    public RobPlayerCommand(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        super(MoveType.robPlayer, playerIndex);
        this.victimIndex = victimIndex;
        this.location = location;
    }

    /**
     * Instantiate a RobPlayerCommand from JSON.
     * @param json JSON of the RobPlayerCommand.
     */
    public RobPlayerCommand(String json)
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
     * @return the Json representation of the model after the command is executed.
     * @param gameID the ID of the game for which to execute the command.
     */
    @Override public String execute(int gameID)
    {
        return null;
    }
}
