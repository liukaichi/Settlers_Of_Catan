package shared.communication.moveCommands;

import shared.definitions.*;

/**
 * Monument command object.
 * 
 * @author Cache Staheli
 *
 */
public class MonumentCommand extends SimpleSerializableCommand
{

    /**
     * Player of playerIndex receives a Victory Point
     * @param playerIndex Index of player receving a Victory Point
     */
    public MonumentCommand(PlayerIndex playerIndex)
    {
        super(MoveType.Monument, playerIndex);
    }

    @Override public String execute()
    {
        return null;
    }
}
