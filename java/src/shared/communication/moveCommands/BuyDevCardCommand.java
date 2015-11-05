package shared.communication.moveCommands;

import shared.definitions.*;

/**
 * buyDevCard command object.
 * 
 * @author Cache Staheli
 *
 */
public class BuyDevCardCommand extends SimpleSerializableCommand
{

    /**
     * Player of playerIndex receives a Development Card and loses a sheep, ore and wheat each.
     * @param playerIndex Index of player buying a Development Card
     */
    public BuyDevCardCommand(PlayerIndex playerIndex)
    {
        super(MoveType.buyDevCard, playerIndex);
    }

    @Override public String execute()
    {
        return null;
    }
}
