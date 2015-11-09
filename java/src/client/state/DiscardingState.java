/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.discard.DiscardController;
import client.discard.IDiscardView;
import client.facade.ClientFacade;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.model.bank.resource.Resources;

/**
 * @author cstaheli
 */
public class DiscardingState extends GameplayState
{
    /* Logger */
    private Resources discardHand;
    private Resources playersHand;
    private ClientFacade facade;
    private DiscardController discardController;

    /**
     * Creates a new Discarding state with the given controller.
     * @param controller the controller.
     */
    public DiscardingState(ObserverController controller)
    {
        super(controller);
        currentTurnStatus = TurnStatus.Discarding;
        if(controller instanceof DiscardController)
        {
            discardController = (DiscardController)controller;
        }
        facade = ClientFacade.getInstance();
    }

    @Override public void updateView()
    {
        super.updateView();
        if (controller instanceof DiscardController)
        {
            playersHand = facade.getPlayer().getResources();
            if(playersHand.totalResources() > 7)
            {
                updateResources();
                if(discardController.getDiscardView().isModalShowing())
                    discardController.getDiscardView().closeModal();
                discardController.getDiscardView().showModal();
            }
            else
            {
                if(discardController.getWaitView().isModalShowing())
                    discardController.getWaitView().closeModal();
                discardController.getWaitView().showModal();
            }

        }
    }

    private void updateResources()
    {
        if (discardHand == null)
            discardHand = new Resources(0, 0, 0, 0, 0);
        IDiscardView view = discardController.getDiscardView();
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

        view.setStateMessage("Discarding: " + discardHand.totalResources() + " Left to Discard: " +
                (playersHand.totalResources() / 2) - discardHand.totalResources());
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
        if(discardController.getDiscardView().isModalShowing())
            discardController.getDiscardView().closeModal();
        if(discardController.getWaitView().isModalShowing())
            discardController.getWaitView().closeModal();
        discardController.getWaitView().showModal();
        facade.discardResources(discardHand);
        discardHand = null;
    }
}
