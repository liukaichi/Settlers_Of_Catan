package client.data;

import shared.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private List<Player> players; // Use list of player.
    // private List<player> players;

    public GameInfo()
    {
        setId(-1);
        setTitle("");
        players = new ArrayList<Player>();
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

    public void addPlayer(Player newPlayer)
    {
        if (players.size() < 4)
        {
            players.add(newPlayer);
        }
    }
    public void addPlayer(PlayerInfo newPlayer)
    {
        if (players.size() < 4)
        {
            players.add(new Player(newPlayer));
        }
    }

    public List<Player> getPlayers()
    {
        return Collections.unmodifiableList(players);
    }

    public void setPlayers(String json){
    }
}
