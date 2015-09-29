package shared.model.player;

import client.data.PlayerInfo;
import shared.definitions.DevCardType;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.*;

/**
 * Represents a player playing the game. There can be up to 4 players in a
 * single game.
 */
public class Player
{
    /**
     * @see Bank
     */
    private PlayerBank bank;

    /**
     * @see PlayerInfo
     */
    private PlayerInfo info;

    /**
     * A player bank is instantiated with the creation of each new player
     */
    public Player()
    {
        bank = new PlayerBank();
    }

    public PlayerInfo getPlayerInfo()
    {
        return info;
    }
    
    boolean canBuyRoad()
    {
    	return bank.canBuyRoad();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment road count
     */
    public void buyRoad() throws InsufficientResourcesException {
    	bank.buyRoad();
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     * @return true if both conditions are met
     */
    public boolean canBuySettlement()
    {
    	return bank.canBuySettlement();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment settlement count
     */
    public void buySettlement()
    {
        try {
            bank.buySettlement();
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the PlayerBank has Cities left to purchase AND if the
     * resources required are available
     * @return true if both conditions are met
     */
    public  boolean canBuyCity()
    {
    	return bank.canBuyCity();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment city count
     * @throws InsufficientResourcesException
     */
    public void buyCity() throws InsufficientResourcesException {
    	bank.buyCity();
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     * @return true if both conditions are met
     */
    public boolean canBuyDevCard()
    {
        return bank.canBuyDevCard();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment the appropriate DevCard count
     * @throws InsufficientResourcesException
     */
    public void buyDevCard() throws InsufficientResourcesException {
        bank.buyDevCard();
    }

    /**
     * Determines if the PlayerBank has the specified DevCard to play AND if the DevCard is playable
     * during the turn
     * @param type the type of DevCard being checked
     * @throws InsufficientResourcesException
     * @return true if both conditions are met
     */
    public boolean canPlayDevCard(DevCardType type)
    {
        return bank.canPlayDevCard(type);
    }

    /**
     * Plays the action of the specified DevCard
     * @param type -- the type of DevCard to play
     * @throws InsufficientResourcesException
     */
    public void playDevCard(DevCardType type) throws InsufficientResourcesException {
        bank.playDevCard(type);
    }
    
    /**
     * Gets the players name from info
     * @return string -- the name of the player
     */
    public String getName()
    {
    	return info.getName();
    }
    
    
}
