package shared.model.bank;

import java.util.List;

import shared.definitions.*;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.structure.Structures;
import shared.model.map.structure.Port;

/**
 * This class extends Bank with properties and methods specific to the Player
 */
public class PlayerBank extends Bank
{
    private Structures structures;
    private int knights, victoryPoints, monuments;
    /**
     * List of ports owned by player
     */
    private List<Port> ports;

    public PlayerBank()
    {
        super();
    }

    public Structures getStructures()
    {
        return structures;
    }

    /**
     * Determines if PlayerBank has the resources to purchase a DevCard
     * 
     * @return true if the condition is met
     */
    public boolean canBuyDevCard()
    {
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
