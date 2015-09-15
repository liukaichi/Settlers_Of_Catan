package client.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.model.Player;

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
    // private List<PlayerInfo> players; Use list of Player.
    private List<Player> players;

    public GameInfo()
    {
        setId(-1);
        setTitle("");
        // Use list of Player.
        // players = new ArrayList<PlayerInfo>();
        players = new ArrayList<Player>();
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

    // Use list of Player.
    public void addPlayer(Player newPlayer)
    {
        players.add(newPlayer);
    }

    // Use list of Player.
    public List<Player> getPlayers()
    {
        return Collections.unmodifiableList(players);
    }
}
