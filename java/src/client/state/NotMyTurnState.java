/**
 * 
 */
package client.state;

import client.base.ObserverController;
import client.domestic.DomesticTradeController;
import client.maritime.MaritimeTradeController;
import client.resources.ResourceBarController;
import client.turntracker.TurnTrackerController;

import java.util.logging.Logger;

/**
 * @author cstaheli
 *
 */
public class NotMyTurnState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public NotMyTurnState(ObserverController controller)
    {
        super(controller);
    }

    @Override public void setTurnTrackerInfo(ObserverController newController)
    {
        TurnTrackerController turnTrackerController = ((TurnTrackerController) newController);
        turnTrackerController.getView().updateGameState("Just hangin' out.", false);
    }

    @Override
    public void acceptTrade(boolean willAccept)
    {
        LOGGER.fine("State calling facade willAccept(" + willAccept + ").");
        facade.acceptTrade(willAccept);
    }

    @Override public void updateView()
    {
        if (controller instanceof MaritimeTradeController)
        {
            ((MaritimeTradeController) controller).getTradeView().enableMaritimeTrade(false);
        }
        else if (controller instanceof DomesticTradeController)
        {
            ((DomesticTradeController) controller).getTradeView().enableDomesticTrade(false);
        }
        else if (controller instanceof ResourceBarController)
        {
            ((ResourceBarController) controller).disableAllActions();
        }
    }
}
