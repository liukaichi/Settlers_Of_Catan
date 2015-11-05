package shared.communication.moveCommands;

import server.facade.IServerFacade;
import shared.definitions.*;

/**
 * buyDevCard command object.
 * 
 * @author Cache Staheli
 *
 */
public class BuyDevCardCommand extends SimpleCommandSerializer
{

    /**
     * @param playerIndex
     */
    public BuyDevCardCommand(PlayerIndex playerIndex)
    {
        super(MoveType.buyDevCard, playerIndex);
    }

    @Override public String execute(IServerFacade facade)
    {
        return null;
    }
}
