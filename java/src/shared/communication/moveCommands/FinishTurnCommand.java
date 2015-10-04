package shared.communication.moveCommands;

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

}
