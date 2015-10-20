package client.data;

import java.util.*;

import shared.definitions.*;
import shared.model.player.Player;

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

    public List<PlayerInfo> getPlayerInfos()
    {
        List<PlayerInfo> playerInfos = new ArrayList<>();
        for (Player player : players)
        {
            playerInfos.add(player.getPlayerInfo());
        }
        return Collections.unmodifiableList(playerInfos);
    }

    public void setPlayers(String json)
    {
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GameInfo gameInfo = (GameInfo) o;

        if (id != gameInfo.id)
            return false;
        if (title != null ? !title.equals(gameInfo.title) : gameInfo.title != null)
            return false;
        return !(players != null ? !players.equals(gameInfo.players) : gameInfo.players != null);

    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    public CatanColor getPlayerColor(PlayerIndex index)
    {
        return players.get(index.getIndex()).getPlayerColor();
    }
}
