package shared.communication.moveCommands;

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

    @Override public void execute()
    {

    }
}
