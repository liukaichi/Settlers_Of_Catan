package client.resources;

import client.base.IAction;
import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.definitions.ResourceType;
import shared.definitions.StructureType;
import shared.model.ClientModel;
import shared.model.bank.PlayerBank;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.Structures;
import shared.model.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends ObserverController implements IResourceBarController
{

    private Map<ResourceBarElement, IAction> elementActions;

    public ResourceBarController(IResourceBarView view)
    {
        super(view);
        elementActions = new HashMap<>();
        facade = ClientFacade.getInstance();
        updateView();
    }

    @Override public IResourceBarView getView()
    {
        return (IResourceBarView) super.getView();
    }

    /**
     * Sets the action to be executed when the specified resource bar element is clicked by the user
     *
     * @param element The resource bar element with which the action is associated
     * @param action  The action to be executed
     */
    public void setElementAction(ResourceBarElement element, IAction action)
    {
        elementActions.put(element, action);
    }

    /**
     * For each resourceType, buildingType, and cardType for your client's player
     * call getView().setElementAmount
     */
    public void updateView()
    {
        Player player = facade.getPlayer();
        if (player != null)
        {
            PlayerBank bank = player.getBank();
            Resources resources = bank.getResources();
            Structures structures = bank.getStructures();
            setResources(resources);
            setStructures(structures);
            getView().setElementAmount(ResourceBarElement.SOLDIERS, bank.getKnights());
        }
    }

    public void setResources(Resources resources)
    {
        getView().setElementAmount(ResourceBarElement.WHEAT, resources.getAmount(ResourceType.WHEAT));
        getView().setElementAmount(ResourceBarElement.SHEEP, resources.getAmount(ResourceType.SHEEP));
        getView().setElementAmount(ResourceBarElement.WOOD, resources.getAmount(ResourceType.WOOD));
        getView().setElementAmount(ResourceBarElement.ORE, resources.getAmount(ResourceType.ORE));
        getView().setElementAmount(ResourceBarElement.BRICK, resources.getAmount(ResourceType.BRICK));
    }

    private void setStructures(Structures structures)
    {
        getView().setElementAmount(ResourceBarElement.ROAD,
                structures.getStructure(StructureType.ROAD).getAmountRemaining());
        getView().setElementAmount(ResourceBarElement.SETTLEMENT,
                structures.getStructure(StructureType.SETTLEMENT).getAmountRemaining());
        getView().setElementAmount(ResourceBarElement.CITY,
                structures.getStructure(StructureType.CITY).getAmountRemaining());
    }

    public void enableActions()
    {
        getView().setElementEnabled(ResourceBarElement.ROAD, state.canBuyRoad());
        getView().setElementEnabled(ResourceBarElement.SETTLEMENT, state.canBuySettlement());
        getView().setElementEnabled(ResourceBarElement.CITY, state.canBuyCity());
        getView().setElementEnabled(ResourceBarElement.BUY_CARD, state.canBuyDevCard());
        getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
    }

    /**
     * Verify they can build a road (resources and remaining settlement amount)
     * if so: call the 	executeElementAction(ResourceBarElement.ROAD);
     * else
     * display message why they can't
     */
    @Override public void buildRoad()
    {
        executeElementAction(ResourceBarElement.ROAD);
    }

    /**
     * same as for build road
     */
    @Override public void buildSettlement()
    {
        executeElementAction(ResourceBarElement.SETTLEMENT);
    }

    /**
     * same as for build road
     */
    @Override public void buildCity()
    {
        executeElementAction(ResourceBarElement.CITY);
    }

    @Override public void buyCard()
    {
       executeElementAction(ResourceBarElement.BUY_CARD);
    }

    /**
     * same as for build road
     */
    @Override public void playCard()
    {
        executeElementAction(ResourceBarElement.PLAY_CARD);
    }

    private void executeElementAction(ResourceBarElement element)
    {

        if (elementActions.containsKey(element))
        {
            IAction action = elementActions.get(element);
            action.execute();
        }
    }

    @Override public void update(Observable o, Object arg)
    {
        if(facade.getClientPlayer().getNormalizedPlayerIndex() != -1)
        {
            updateView();
        }
    }

    /**
     * Disables all the things in the Resource Bar. Usually used for NotMyTurnState
     */
    public void disableAllActions()
    {
        getView().setElementEnabled(ResourceBarElement.BUY_CARD,false);
        getView().setElementEnabled(ResourceBarElement.PLAY_CARD,false);
        getView().setElementEnabled(ResourceBarElement.ROAD,false);
        getView().setElementEnabled(ResourceBarElement.CITY,false);
        getView().setElementEnabled(ResourceBarElement.SETTLEMENT,false);
    }
}

