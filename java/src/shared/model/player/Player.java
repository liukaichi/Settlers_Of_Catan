package shared.model.player;

import client.data.PlayerInfo;
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
    private Bank bank;

    /**
     * @see PlayerInfo
     */
    private PlayerInfo playerInfo;

    /**
     * A player bank is instantiated with the creation of each new player
     */
    public Player()
    {
        bank = new PlayerBank();
    }

    public String getName()
    {
        return playerInfo.getName();
    }
}
