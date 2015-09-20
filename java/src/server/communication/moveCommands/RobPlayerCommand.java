package server.communication.moveCommands;

import client.data.RobPlayerInfo;
import shared.locations.HexLocation;

/**
 * robPlayer command object
 * 
 * @author Cache Staheli
 * @see RobPlayerInfo
 * @see HexLocation
 */
public class RobPlayerCommand extends MoveCommand
{
    /**
     * The information about the player being robbed.
     */
    private RobPlayerInfo robberInfo;
    /**
     * The location of the robber.
     */
    private HexLocation location;
}
