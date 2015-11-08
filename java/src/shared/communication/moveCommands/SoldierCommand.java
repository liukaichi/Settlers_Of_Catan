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
     * Instantiates a SoldierCommand with the given player, victim, and location of the new robber.
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
     * Instantiate a SoldierCommand from JSON.
     * @param json JSON of the SoldierCommand.
     */
    public SoldierCommand(String json)
    {

    }

    /**
     * Robs the player that is specified
     * @return the Json representation of the model after the command is executed.
     * @param gameID the ID of the game for which to execute the command.
     */
    @Override public String execute(int gameID)
    {
        return null;
    }

}
