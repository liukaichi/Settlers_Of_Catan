package shared.model.bank;

import java.util.List;

import shared.definitions.*;
import shared.definitions.exceptions.*;
import shared.model.map.structure.Port;
import shared.model.map.structure.Structure;

public class PlayerBank extends Bank
{
    private Structure structures;
    private int knights, victoryPoints;
    /**
     * List of ports owned by player
     */
    private List<Port> ports;

    public PlayerBank()
    {
        super();
    }

    public Structure getStructures()
    {
    	return structures; 
    }

    /**
     * Determines if the player can purchase a DevCard
     * 
     * @return true if game conditions are met
     * @throws InsufficientFundsException
     *         if there are insufficient resources to buy a DevCard
     */
    public boolean canBuyDevCard()
    {
        return false;
    }
    
    /**
     * Determines if the player can purchase a Road
     * 
     * @return true if game conditions are met
     * @throws InsufficientFundsException
     *         if there are insufficient resources to buy a Road
     */
    public boolean canBuyRoad()
    {
        return false;
    }
    
    /**
     * Determines if the player can purchase a Settlement
     * 
     * @return true if game conditions are met
     * @throws InsufficientFundsException
     *         if there are insufficient resources to buy a Settlement
     */
    public boolean canBuySettlement()
    {
        return false;
    }
    
    
    /**
     * Determines if the player can purchase a City
     * 
     * @return true if game conditions are met
     * @throws InsufficientFundsException
     *         if there are insufficient resources to buy a City
     */
    public boolean canBuyCity()
    {
        return false;
    }

    /**
     * Determines if the player is allowed to play a DevCard
     * 
     * @param type
     *        -- the DevCard being played
     * @return true if the player is allowed to play a DevCard
     * @throws InsufficientFundsException
     *         if the player does not have the specified DevCard
     */
    public boolean canPlayDevCard(DevCardType type)
    {
        return false;
    }
    
    public void playDevCard(DevCardType type)
    {
    	
    }

    /**
     * Determines if the player can trade with a port
     * 
     * @param type
     *        -- the type of resource being traded with a port
     * @return returns true if the player owns a port with that type of resource
     * @throws CatanException
     *         if anything goes wrong.
     */
    public boolean canAccessPort(PortType type)
    {
        return false;
    }

}
