package shared.communication.moveCommands;

import server.facade.IServerFacade;
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

    @Override public String execute(IServerFacade facade)
    {
        return null;
    }
}
