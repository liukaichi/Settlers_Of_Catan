package shared.communication.moveCommands;

import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.locations.HexLocation;

import java.util.logging.Logger;

/**
 * Soldier command object.
 *
 * @author Cache Staheli
 */
public class SoldierCommand extends RobPlayerCommand
{
    private static final Logger LOGGER = Logger.getLogger(SoldierCommand.class.getName());
    private static final long serialVersionUID = -794275463663006069L;

    /**
     * Instantiates a SoldierCommand with the given player, victim, and location of the new robber.
     *
     * @param playerIndex the index of the player calling the command
     * @param victimIndex the index of the player being robbed.
     * @param location    the new location of the robber.
     */
    public SoldierCommand(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        super(playerIndex, victimIndex, location);
        this.type = MoveType.Soldier;
    }

    /**
     * Instantiate a SoldierCommand from JSON.
     *
     * @param json JSON of the SoldierCommand.
     */
    public SoldierCommand(String json)
    {
        super(json);
        setType(MoveType.Soldier);
    }

    /**
     * Robs the player that is specified
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing SoldierCommand(%d, %d, %s) for game %d", getPlayerIndex().getIndex(), getVictimIndex().getIndex(),
                getLocation().toString(), gameID));
        String model = AbstractServerFacade.getInstance().soldier(gameID, getPlayerIndex(), this.getVictimIndex(), this.getLocation()).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;

    }

}
