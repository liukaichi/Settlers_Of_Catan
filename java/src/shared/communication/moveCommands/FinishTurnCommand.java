package shared.communication.moveCommands;

import shared.definitions.*;

/**
 * finishTurn command object.
 * 
 * @author Cache Staheli
 *
 */
public class FinishTurnCommand extends SimpleSerializableCommand
{
    /**
     * @param playerIndex
     */
    public FinishTurnCommand(PlayerIndex playerIndex)
    {
        super(MoveType.finishTurn, playerIndex);
    }

    @Override public String execute()
    {
        return null;
    }
}
