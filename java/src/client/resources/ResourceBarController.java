package client.resources;

import java.util.*;

import client.base.*;
import client.facade.ClientFacade;
import shared.definitions.ResourceType;
import shared.definitions.StructureType;
import shared.model.ClientModel;
import shared.model.bank.PlayerBank;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.Structures;
import shared.model.player.Player;

/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends ObserverController implements IResourceBarController
{

    private Map<ResourceBarElement, IAction> elementActions;
    private ClientFacade facade;

    public ResourceBarController(IResourceBarView view)
    {

        super(view);

        elementActions = new HashMap<ResourceBarElement, IAction>();
        facade = ClientFacade.getInstance();
        initFromModel();
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
    public void initFromModel()
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

            disableActions();
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

    private void disableActions()
    {
        getView().setElementEnabled(ResourceBarElement.ROAD, state.canBuyRoad());
        getView().setElementEnabled(ResourceBarElement.SETTLEMENT, state.canBuySettlement());
        getView().setElementEnabled(ResourceBarElement.CITY, state.canBuyCity());
        getView().setElementEnabled(ResourceBarElement.BUY_CARD, state.canBuyDevCard());
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
        ClientModel model = (ClientModel) o;
        if(facade.getClientPlayer().getNormalizedPlayerIndex() != -1)
        {
            state.update(this, model, arg);
            initFromModel();
        }

    }

}

