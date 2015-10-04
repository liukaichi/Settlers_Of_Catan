package shared.communication.moveCommands;

import shared.definitions.*;

/**
 * Monument command object.
 * 
 * @author Cache Staheli
 *
 */
public class MonumentCommand extends SimpleCommandSerializer
{

    /**
     * @param playerIndex
     */
    public MonumentCommand(PlayerIndex playerIndex)
    {
        super(MoveType.Monument, playerIndex);
    }

}
