/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.discard.DiscardController;
import client.discard.IDiscardView;
import client.facade.ClientFacade;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.model.TurnTracker;
import shared.model.bank.resource.Resources;

import java.util.logging.Logger;

/**
 * @author cstaheli
 */
public class DiscardingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private Resources discardHand;
    private Resources playersHand;
    private ClientFacade facade;

    public DiscardingState(ObserverController controller)
    {
        super(controller);
        currentTurnStatus = TurnStatus.Discarding;
        facade = ClientFacade.getInstance();
    }

    @Override public void updateView()
    {
        super.updateView();
        if (controller instanceof DiscardController)
        {
            DiscardController control = ((DiscardController) controller);
            TurnTracker tracker = facade.getModel().getTurnTracker();
            PlayerIndex turn = tracker.getCurrentTurn();
            playersHand = facade.getPlayer().getResources();
            if(playersHand.totalResources() > 7)
            {
                updateResources();
                control.getDiscardView().showModal();
            }
            else
            {
                control.getWaitView().showModal();
            }

        }
    }

    private void updateResources()
    {
        if (discardHand == null)
            discardHand = new Resources(0, 0, 0, 0, 0);
        IDiscardView view = ((DiscardController) controller).getDiscardView();
        view.setDiscardButtonEnabled(discardHand.totalResources() == (playersHand.totalResources() / 2));
        for (ResourceType resourceType : ResourceType.values())
        {
            view.setResourceAmountChangeEnabled(resourceType,
                    playersHand.getAmount(resourceType) > discardHand.getAmount(resourceType) && (
                            discardHand.totalResources() != (playersHand.totalResources() / 2)),
                    discardHand.getAmount(resourceType) > 0);
            view.setResourceDiscardAmount(resourceType, discardHand.getAmount(resourceType));
            view.setResourceMaxAmount(resourceType, playersHand.getAmount(resourceType));
        }

        view.setStateMessage("Discarding: " + discardHand.totalResources() + " Left to Discard: " + (int) (
                (playersHand.totalResources() / 2) - discardHand.totalResources()));
    }

    @Override public void increaseAmount(ResourceType resource)
    {
        if (playersHand.getAmount(resource) > discardHand.getAmount(resource))
        {
            discardHand.increase(resource);
            updateResources();
        }
    }

    @Override public void decreaseAmount(ResourceType resource)
    {
        if (discardHand.getAmount(resource) > 0)
        {
            discardHand.decrease(resource);
            updateResources();
        }
    }

    @Override public void discardResources()
    {
        DiscardController control = ((DiscardController) controller);
        control.getDiscardView().closeModal();
        control.getWaitView().showModal();
        facade.discardResources(discardHand);
        discardHand = null;
        //TODO need to use this method to show modal, close modal when turnstatus is your turn again. ((DiscardController) controller).getWaitView().showModal();
    }
}
