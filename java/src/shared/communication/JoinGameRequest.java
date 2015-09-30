package shared.communication;

import shared.definitions.CatanColor;

/**
 * The ID of the game the player wants to join, and the color they want.
 * 
 * @author Cache Staheli
 *
 */
public class JoinGameRequest
{
    private int id;
    private CatanColor color;

    public JoinGameRequest(int id, CatanColor color)
    {
        this.id = id;
        this.color = color;
    }
}
