package client.data;

/**
 * Used to pass player information into the rob view<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: player's order in the game [0-3]</li>
 * <li>Name: player's name (non-empty string)</li>
 * <li>Color: player's color (cannot be null)</li>
 * <li>NumCards: Number of development cards the player has (less than 0)</li>
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

    public int getNumCards()
    {
        return numCards;
    }

    public void setNumCards(int numCards)
    {
        this.numCards = numCards;
    }

}
