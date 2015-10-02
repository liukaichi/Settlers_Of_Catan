package client.data;

import shared.definitions.*;

/**
 * Used to pass player information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: player's order in the game [0-3]</li>
 * <li>Name: player's name (non-empty string)</li>
 * <li>Color: player's color (cannot be null)</li>
 * </ul>
 * 
 */
public class PlayerInfo
{

    private int id;
    // private int playerIndex; change to PlayerIndex
    private PlayerIndex playerIndex;
    private String name;
    private CatanColor color;

    public PlayerInfo()
    {
        setId(-1);
        setPlayerIndex(-1);
        setName("");
        setColor(CatanColor.WHITE);
    }

    /**
     * @param id
     * @param name
     * @param color
     */
    public PlayerInfo(int id, String name, CatanColor color)
    {
        this();
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getNormalizedPlayerIndex()
    {
        return playerIndex.getIndex();
    }
    
    public PlayerIndex getPlayerIndex()
    {
    	return playerIndex; 
    }

    public void setPlayerIndex(int playerIndex)
    {
        // this.playerIndex = playerIndex;
        switch (playerIndex)
        {
        case 0:
            this.playerIndex = PlayerIndex.PLAYER_0;
            break;
        case 1:
            this.playerIndex = PlayerIndex.PLAYER_1;
            break;
        case 2:
            this.playerIndex = PlayerIndex.PLAYER_2;
            break;
        case 3:
            this.playerIndex = PlayerIndex.PLAYER_3;
            break;
        default:
            this.playerIndex = PlayerIndex.NONE;
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public CatanColor getColor()
    {
        return color;
    }

    public void setColor(CatanColor color)
    {
        this.color = color;
    }

    @Override
    public int hashCode()
    {
        return 31 * this.id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final PlayerInfo other = (PlayerInfo) obj;

        return this.id == other.id;
    }
}
