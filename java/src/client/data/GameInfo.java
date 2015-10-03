package client.data;

import java.util.*;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo
{
    private int id;
    private String title;
    private List<PlayerInfo> players; // Use list of player.
    // private List<player> players;

    public GameInfo()
    {
        setId(-1);
        setTitle("");
        players = new ArrayList<PlayerInfo>();
    }

    public GameInfo(int id, String title)
    {
        this();
        setId(id);
        setTitle(title);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void addPlayer(PlayerInfo newPlayer)
    {
        if (players.size() < 4)
        {
            players.add(newPlayer);
        }
    }

    public List<PlayerInfo> getPlayers()
    {
        return Collections.unmodifiableList(players);
    }

}
