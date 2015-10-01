/**
 * 
 */
package shared.communication;

import java.util.*;

import com.google.gson.*;

import client.data.*;
import shared.definitions.CatanColor;

/**
 * @author cstaheli
 *
 */
public class ListGamesResponse
{
    private List<GameInfo> games;

    public ListGamesResponse()
    {
        games = new ArrayList<>();
    }

    public ListGamesResponse(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonArray gameInfoArray = (JsonArray) parser.parse(json);
        for (JsonElement element : gameInfoArray)
        {
            JsonObject game = (JsonObject) element;
            String title = game.get("title").getAsString();
            int id = game.get("id").getAsInt();
            JsonArray playerArray = game.get("players").getAsJsonArray();
            GameInfo gameInfo = new GameInfo(id, title);
            for (JsonElement elem : playerArray)
            {
                JsonObject player = (JsonObject) elem;
                CatanColor color = CatanColor.valueOf(player.get("color").getAsString().toUpperCase());
                String name = player.get("name").getAsString();
                int playerID = player.get("id").getAsInt();
                PlayerInfo playerInfo = new PlayerInfo(playerID, name, color);
                gameInfo.addPlayer(playerInfo);
            }
        }

    }

    public List<GameInfo> getGames()
    {
        return Collections.unmodifiableList(games);
    }
}
