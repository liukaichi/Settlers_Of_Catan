package shared.model.bank;

import java.util.List;

import client.main.Catan;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.Structures;
import shared.model.map.structure.Port;

/**
 * This class extends Bank with properties and methods specific to the Player
 */
public class PlayerBank extends Bank
{
    private Structures structures;
    private int knights, victoryPoints, monuments;
    private DevCards devCards;
    private Resources resources;
    private int sheep, wheat, wood, ore, brick;
    private DevCard monopoly, monument, roadBuilding, soldier, yearOfPlenty;
    /**
     * List of ports owned by player
     */
    private List<Port> ports;

    public PlayerBank() throws CatanException
    {
        super();
        knights = 0;
        victoryPoints = 0;
        monuments = 0;
        ports = null;
        devCards = super.getDevCards();
        resources = super.getResources();
        structures = new Structures();
        initResources();
    }

    public Structures getStructures()
    {
        return structures;
    }

    private void initDevCards() throws CatanException{
        monument = devCards.getCard(DevCardType.MONUMENT);
        monopoly = devCards.getCard(DevCardType.MONOPOLY);

    }

    private void initResources() throws CatanException{
        sheep = resources.getResource(ResourceType.SHEEP).getAmount();
        wood = resources.getResource(ResourceType.WOOD).getAmount();
        wheat = resources.getResource(ResourceType.WHEAT).getAmount();
        ore = resources.getResource(ResourceType.ORE).getAmount();
        brick = resources.getResource(ResourceType.BRICK).getAmount();
    }


    /**
     * Determines if PlayerBank has the resources to purchase a DevCard
     * 
     * @return true if the condition is met
     */
    public boolean canBuyDevCard() {
        if ((sheep > 0) && (wood > 0) && (wheat > 0)){
            return true;
        }
        return false;
    }

    /**
     * Increments the appropriate DevCard count in PlayerBank and decrements
     * appropriate resources
     * 
     * @throws InsufficientResourcesException
     */
    public void buyDevCard() throws InsufficientResourcesException
    {

    }

    /**
     * Determines if PlayerBank has the resources to purchase a Road
     * 
     * @return true if the condition is met
     */
    public boolean canBuyRoad()
    {
        if ((brick > 0) && (wood > 0)){
            return true;
        }
        return false;
    }

    /**
     * Increments the road count in PlayerBank and decrements appropriate
     * resources
     * 
     * @throws InsufficientResourcesException
     */
    public void buyRoad() throws InsufficientResourcesException
    {

    }

    /**
     * Determines if PlayerBank has the resources to purchase a Settlement
     * 
     * @return true if the condition is met
     */
    public boolean canBuySettlement()
    {
        if((sheep > 0) && (wood > 0) && (wheat > 0) && (brick > 0)){
            return true;
        }
        return false;
    }

    /**
     * Increments the settlement count in PlayerBank and decrements appropriate
     * resources
     * 
     * @throws InsufficientResourcesException
     */
    public void buySettlement() throws InsufficientResourcesException
    {

    }

    /**
     * Determines if PlayerBank has the resources to purchase a City
     * 
     * @return true if the condition is met
     */
    public boolean canBuyCity()
    {
        if ((ore > 3) && (wheat > 2)){
            return true;
        }
        return false;
    }

    /**
     * Increments the city count in PlayerBank and decrements appropriate
     * resources
     * 
     * @throws InsufficientResourcesException
     */
    public void buyCity() throws InsufficientResourcesException
    {

    }

    /**
     * Determines if the specified DevCard is playable AND is available in
     * PlayerBank
     * 
     * @param type
     *        the type of DevCard to check
     * @return true if both conditions are met
     */
    public boolean canPlayDevCard(DevCardType type)
    {
//        if ()
        return false;
    }

    /**
     * Calls playAction() on the DevCard
     * 
     * @param type
     *        the type of DevCard to play
     * @throws InsufficientResourcesException
     */
    public void playDevCard(DevCardType type) throws InsufficientResourcesException
    {

    }

    /**
     * Determines if a Player has control over a Port
     * 
     * @param type
     *        the type of Port to check
     * @return true if the condition is met
     */
    public boolean canAccessPort(PortType type)
    {
        return false;
    }

}
