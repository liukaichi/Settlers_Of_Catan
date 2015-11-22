package client.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.CatanColor;

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
 */
public class GameInfo
{
    private int id;
    private String title;
    private List<PlayerInfo> players;

    /**
     * Creates an empty GameInfo with nothing in it.
     *
     * @see shared.model.ClientModel#ClientModel(String) Used by ClientModel(Json) to hold a list of players.
     */
    public GameInfo()
    {
        setId(-1);
        setTitle("");
        players = new ArrayList<>();
    }

    /**
     * Instantiates a GameInfo with the given id, title, and an empty list of players.
     *
     * @param id    the id of the game.
     * @param title the title of the game.
     */
    public GameInfo(int id, String title)
    {
        this();
        setId(id);
        setTitle(title);
    }

    public GameInfo(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(json);
        this.title = obj.get("title").getAsString();
        this.id = obj.get("id").getAsInt();
        JsonArray players = obj.getAsJsonArray("players");
        for (JsonElement element : players)
        {
            JsonObject jsonPlayer = (JsonObject) element;
            if (jsonPlayer.has("color") && jsonPlayer.has("name") && jsonPlayer.has("id"))
            {
                CatanColor color = CatanColor.valueOf(jsonPlayer.get("color").getAsString().toUpperCase());
                String name = jsonPlayer.get("name").getAsString();
                int playerID = jsonPlayer.get("id").getAsInt();
                PlayerInfo player = new PlayerInfo(playerID, name, color);
                this.addPlayer(player);
            }
        }
    }

    public boolean hasPlayer(int playerID)
    {
        for (PlayerInfo player : players)
        {
            if (player.getId() == playerID)
            {
                return true;
            }
        }
        return false;
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
     *
     * @param newPlayer the player to add.
     */
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

    public boolean playerAlreadyJoined(int playerId)
    {
        for (PlayerInfo player : players)
        {
            if (player.getId() == playerId)
            {
                return true;
            }
        }
        return false;
    }

    @Override public boolean equals(Object o)
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

    @Override public int hashCode()
    {
        return 0;
    }

    @Override public String toString()
    {

        JsonParser parser = new JsonParser();
        JsonObject gameInfo = new JsonObject();

        //create list of players
        JsonArray players = new JsonArray();
        for (PlayerInfo player : this.players)
        {
            players.add(parser.parse(player.toString()));
        }

        //add all gameInfo properties
        gameInfo.addProperty("title", title);
        gameInfo.addProperty("id", id);
        gameInfo.add("players", players);

        return gameInfo.toString();

    }
    public void setPlayerColor(CatanColor color, int playerID)
    {
        for (PlayerInfo player : players)
        {
            if (player.getId() == playerID)
            {
                player.setColor(color);
            }
        }
    }
}
