/**
 * 
 */
package client.state;

import client.base.ObserverController;
import client.domestic.DomesticTradeController;
import client.domestic.IAcceptTradeOverlay;
import client.maritime.MaritimeTradeController;
import client.resources.ResourceBarController;
import client.turntracker.TurnTrackerController;
import shared.model.TurnTracker;

import java.util.logging.Logger;

/**
 * @author cstaheli
 *
 */
public class NotMyTurnState extends GameplayState
{

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
        if(controller instanceof DomesticTradeController) {
            IAcceptTradeOverlay accept = ((DomesticTradeController)controller).getAcceptOverlay();
            if (accept.isModalShowing()) {
                accept.closeModal();
            }
            LOGGER.fine("State calling facade willAccept(" + willAccept + ").");
            facade.acceptTrade(willAccept);
        }
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
        else if (controller instanceof TurnTrackerController)
        {
            ((TurnTrackerController) controller).updatePlayers(facade.getModel());
        }
        else if (controller instanceof ResourceBarController)
        {
            ((ResourceBarController) controller).disableAllActions();
        }
    }
}
