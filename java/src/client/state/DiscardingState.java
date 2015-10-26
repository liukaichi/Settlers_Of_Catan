/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.discard.DiscardController;
import shared.definitions.TurnStatus;
import shared.model.bank.resource.Resources;

import java.util.logging.Logger;

/**
 * @author cstaheli
 */
public class DiscardingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public DiscardingState(ObserverController controller)
    {
        super(controller);
        currentTurnStatus = TurnStatus.Discarding;
    }

    @Override public void updateView()
    {
        super.updateView();
        if (controller instanceof DiscardController)
        {
            ((DiscardController) controller).getDiscardView().showModal();
        }
    }

    @Override public void discardResources(Resources discardedResources)
    {
        super.discardResources(discardedResources);
    }
}
