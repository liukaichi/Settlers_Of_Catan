package client.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;

import java.io.Serializable;

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
 */
public class PlayerInfo implements Serializable
{

    private static final long serialVersionUID = -6664101032500471675L;
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
     * Creates a PlayerInfo with the given information.
     * @param id the id of the player.
     * @param name the name of the player.
     * @param color the color of the player.
     */
    public PlayerInfo(int id, String name, CatanColor color)
    {
        this();
        this.id = id;
        this.name = name;
        this.color = color;
    }

    /**
     * Creates a PlayerInfo given the Json returned from a URLDecoder for the
     * Catan User Cookie. This should only contain the name and ID of the
     * player. The password is also returned, but the password is not currently
     * stored locally.
     *
     * @param json the json representation of this. Invoked from
     *             ServerProxy#buildPlayerInfoFromCookie().
     */
    public PlayerInfo(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonObject user = (JsonObject) parser.parse(json);
        String name = user.get("name").getAsString();
        int id = user.get("playerID").getAsInt();
        // Password not used currently.
        String password = user.get("password").getAsString();

        this.name = name;
        this.id = id;
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

    public void setPlayerIndex(PlayerIndex playerIndex)
    {
        this.playerIndex = playerIndex;
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

    @Override public int hashCode()
    {
        return 31 * this.id;
    }

    @Override public boolean equals(Object obj)
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

    @Override public String toString()
    {
        JsonParser parser = new JsonParser();
        JsonObject gameInfo = new JsonObject();

        //add all gameInfo properties
        gameInfo.addProperty("color", color.toString().toLowerCase());
        gameInfo.addProperty("name", name);
        gameInfo.addProperty("id", id);
        return gameInfo.toString();
    }
}
