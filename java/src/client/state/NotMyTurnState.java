/**
 * 
 */
package client.state;

import client.base.ObserverController;
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
}
