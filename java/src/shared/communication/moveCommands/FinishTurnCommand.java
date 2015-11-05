package shared.communication.moveCommands;

import server.facade.IServerFacade;
import shared.definitions.*;

/**
 * finishTurn command object.
 * 
 * @author Cache Staheli
 *
 */
public class FinishTurnCommand extends SimpleCommandSerializer
{
    /**
     * @param playerIndex
     */
    public FinishTurnCommand(PlayerIndex playerIndex)
    {
        super(MoveType.finishTurn, playerIndex);
    }

    @Override public String execute(IServerFacade facade)
    {
        return null;
    }
}
