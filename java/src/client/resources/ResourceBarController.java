package client.resources;

import java.util.*;

import client.base.*;
import client.facade.ClientFacade;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.definitions.StructureType;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.ClientModel;
import shared.model.bank.Bank;
import shared.model.bank.PlayerBank;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.Structure;
import shared.model.bank.structure.Structures;
import shared.model.player.Player;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends ObserverController implements IResourceBarController {

    private Map<ResourceBarElement, IAction> elementActions;
    private ClientFacade facade;

    public ResourceBarController(IResourceBarView view) {

        super(view);

        elementActions = new HashMap<ResourceBarElement, IAction>();
        facade = ClientFacade.getInstance();
        setResources();
    }

    @Override
    public IResourceBarView getView() {
        return (IResourceBarView)super.getView();
    }

    /**
     * Sets the action to be executed when the specified resource bar element is clicked by the user
     *
     * @param element The resource bar element with which the action is associated
     * @param action The action to be executed
     */
    public void setElementAction(ResourceBarElement element, IAction action) {

        elementActions.put(element, action);
    }


    /**
     * For each resourceType, buildingType, and cardType for your client's player
     * call getView().setElementAmount
     */
    public void setResources()
    {
        Player player = facade.getPlayer();
        if(player != null) {
            PlayerBank bank = player.getBank();

            Resources resources = bank.getResources();
            Structures structures = bank.getStructures();

            // testing
            resources.setAmount(ResourceType.WHEAT, 5);
            structures.getStructure(StructureType.ROAD).setAmountBuilt(3);

            getView().setElementAmount(ResourceBarElement.WHEAT, resources.getAmount(ResourceType.WHEAT));
            getView().setElementAmount(ResourceBarElement.SHEEP, resources.getAmount(ResourceType.SHEEP));
            getView().setElementAmount(ResourceBarElement.WOOD, resources.getAmount(ResourceType.WOOD));
            getView().setElementAmount(ResourceBarElement.ORE, resources.getAmount(ResourceType.ORE));
            getView().setElementAmount(ResourceBarElement.BRICK, resources.getAmount(ResourceType.BRICK));

            getView().setElementAmount(ResourceBarElement.ROAD, structures.getStructure(StructureType.ROAD).getAmountBuilt());
            getView().setElementAmount(ResourceBarElement.SETTLEMENT, structures.getStructure(StructureType.SETTLEMENT).getAmountBuilt());
            getView().setElementAmount(ResourceBarElement.CITY, structures.getStructure(StructureType.CITY).getAmountBuilt());
        }


    }


    /**
     * Verify they can build a road (resources and remaining settlement amount)
     * if so: call the 	executeElementAction(ResourceBarElement.ROAD);
     * else
     * display message why they can't
     *
     */
    @Override
    public void buildRoad() {
        executeElementAction(ResourceBarElement.ROAD);
    }


    /**
     * same as for build road
     */
    @Override
    public void buildSettlement() {
        executeElementAction(ResourceBarElement.SETTLEMENT);
    }

    /**
     * same as for build road
     */
    @Override
    public void buildCity() {
        executeElementAction(ResourceBarElement.CITY);
    }



    @Override
    public void buyCard() {
        executeElementAction(ResourceBarElement.BUY_CARD);
    }


    /**
     * same as for build road
     */
    @Override
    public void playCard() {
        executeElementAction(ResourceBarElement.PLAY_CARD);
    }

    private void executeElementAction(ResourceBarElement element) {

        if (elementActions.containsKey(element)) {

            IAction action = elementActions.get(element);
            action.execute();
        }
    }

    @Override public void update(Observable o, Object arg)
    {

        setResources();
    }
}

