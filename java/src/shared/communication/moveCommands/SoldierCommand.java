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
     * @param playerIndex
     * @param robberInfo
     * @param location
     */
    public SoldierCommand(PlayerIndex playerIndex, RobPlayerInfo robberInfo, HexLocation location)
    {
        super(playerIndex, robberInfo, location);
        this.type = MoveType.Soldier;
    }

}
