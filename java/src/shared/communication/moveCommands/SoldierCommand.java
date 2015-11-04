package shared.communication.moveCommands;

import client.data.RobPlayerInfo;
import shared.definitions.*;
import shared.locations.HexLocation;

/**
 * Soldier command object.
 * 
 * @author Cache Staheli
 *
 */
public class SoldierCommand extends RobPlayerCommand
{

    /**
     * @param playerIndex the index of the player calling the command
     * @param victimIndex the index of the player being robbed.
     * @param location the new location of the robber.
     */
    public SoldierCommand(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        super(playerIndex, victimIndex, location);
        this.type = MoveType.Soldier;
    }

    @Override public void execute()
    {

    }

}
