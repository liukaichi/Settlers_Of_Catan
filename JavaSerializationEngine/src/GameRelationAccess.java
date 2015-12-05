import server.plugin.IGameRelationAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameRelationAccess implements IGameRelationAccess, IAccess
{
    private Map<Integer, List<Integer>> users;

    @Override public void initialize()
    {
        users = new HashMap<>();
    }

    @Override public void addUserToGame(int gameID, int userID) throws Exception
    {
        List<Integer> gameUsers = users.get(gameID);
        if (gameUsers != null)
        {
            gameUsers.add(userID);
        } else
        {
            users.put(gameID, new ArrayList<Integer>()
            {{
                add(userID);
            }});
        }
    }

    @Override public List<Integer> listPlayersInGame(int gameID) throws Exception
    {
        List<Integer> gameUsers = users.get(gameID);
        if (gameUsers != null)
        {
            return gameUsers;
        } else
        {
            throw new Exception("There are no players in the game to list in game: " + gameID);
        }

    }
}
