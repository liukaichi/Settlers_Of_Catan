package client.data;

import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    private List<Player> players;

    /**
     * Creates an empty GameInfo with nothing in it.
     * @see shared.model.ClientModel#ClientModel(String) Used by ClientModel(Json) to hold a list of players.
     */
    public GameInfo()
    {
        setId(-1);
        setTitle("");
        players = new ArrayList<Player>();
    }

    /**
     * Instantiates a GameInfo with the given id, title, and an empty list of players.
     * @param id the id of the game.
     * @param title the title of the game.
     */
    public GameInfo(int id, String title)
    {
        this();
        setId(id);
        setTitle(title);
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

    /**
     * Adds a player to the list of players in the game.
     * @param newPlayer the player to add.
     */
    public void addPlayer(Player newPlayer)
    {
        if (players.size() < 4)
        {
            players.add(newPlayer);
        }
    }

    /**
     * Adds a player to the list of players in the game.
     * @param newPlayer the player to add.
     */
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

    /**
     * Gets the color of the player with the given index.
     * @param index the index of the player.
     * @return the color of the player with the given index.
     */
    public CatanColor getPlayerColor(PlayerIndex index)
    {
        return players.get(index.getIndex()).getPlayerColor();
    }


    @Override
    public String toString() {

        JsonParser parser = new JsonParser();
        JsonObject gameInfo = new JsonObject();

        //create list of players
        JsonArray playerList = new JsonArray();
        for (Player player : players)
        {
            playerList.add(parser.parse(player.getPlayerInfo().toString()));
        }

        //add all gameInfo properties
        gameInfo.addProperty("title", title);
        gameInfo.addProperty("id", id);
        gameInfo.add("players", playerList);


        return gameInfo.toString();

    }
}
