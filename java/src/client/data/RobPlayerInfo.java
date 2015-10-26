package client.data;

import client.facade.ClientFacade;
import shared.definitions.PlayerIndex;
import shared.model.player.Player;

/**
 * Used to pass player information into the rob view<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>PlayerIndex: player's order in the game [0-3]</li>
 * <li>NumCards: Number of resources cards the player has (less than 0)</li>
 * </ul>
 * 
 */
public class RobPlayerInfo extends PlayerInfo
{

    private int numCards;

    public RobPlayerInfo()
    {
        super();
    }

    public RobPlayerInfo(PlayerIndex victim, int numCards)
    {
        this.setPlayerIndex(victim);
        Player player = ClientFacade.getInstance().getPlayerByIndex(victim);
        super.setName(player.getName());
        super.setColor(player.getPlayerColor());
        super.setId(player.getPlayerInfo().getId());
        this.numCards = numCards;

    }

    public int getNumCards()
    {
        return numCards;
    }

    public void setNumCards(int numCards)
    {
        this.numCards = numCards;
    }

}
