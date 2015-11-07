package shared.communication.moveCommands;

import server.facade.AbstractServerFacade;
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

    /**
     * Instantiate a SoldierCommand from JSON with the injected facade
     * @param json JSON of the SoldierCommand
     * @param facade Facade to be used
     */
    public SoldierCommand(String json, AbstractServerFacade facade)
    {

    }

    /**
     * Robs the player that is specified
     * @return returns the updated model
     */
    @Override public String execute()
    {
        return null;
    }

}
