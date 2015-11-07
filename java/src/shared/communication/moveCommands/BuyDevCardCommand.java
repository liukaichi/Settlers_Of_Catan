package shared.communication.moveCommands;

import server.facade.AbstractServerFacade;
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

    /**
     * Instantiate a BuyDevCardCommand from JSON with the injected facade
     * @param json JSON of the BuyDevCardCommand
     * @param facade Facade to be used
     */
    public BuyDevCardCommand(String json, AbstractServerFacade facade)
    {

    }

    @Override public String execute()
    {
        return null;
    }
}
