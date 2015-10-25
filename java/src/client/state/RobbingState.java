/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.map.MapController;
import client.turntracker.TurnTrackerController;

import java.util.logging.Logger;

/**
 * @author cstaheli
 */
public class RobbingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public RobbingState(ObserverController controller)
    {
        super(controller);
        /*showModal();*/
    }

    @Override public void setTurnTrackerInfo(ObserverController newController)
    {
        TurnTrackerController turnTrackerController = ((TurnTrackerController) newController);
        turnTrackerController.getView().updateGameState("Let's rob some peeps, yo.", false);
    }

    @Override public void showModal()
    {
        if (controller instanceof MapController)
        {
            ((MapController) controller).getRobView().showModal();
        }
    }
}
