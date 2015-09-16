package shared.model;

import client.data.PlayerInfo;

/**
 * Represents a player playing the game.
 * There can be up to 4 players in a single game.
 */
public class Player
{
    /**
     * @see Bank
     */
    private Bank bank;
    
    /**
     * 
     */
    private PlayerInfo playerInfo;
    // probably should create a Name class?
    private String name;

    public Player()
    {
        bank = new PlayerBank();
    }

    public String getName()
    {
        return playerInfo.getName();
    }
}
