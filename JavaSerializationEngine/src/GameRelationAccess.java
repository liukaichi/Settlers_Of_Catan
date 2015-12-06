import database.Database;
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
    @Override public void initialize()
    {
    }

    @Override public void addUserToGame(int gameID, int userID) throws Exception
    {
        List<Integer> gameUsers = Database.getInstance().getUsers(gameID);
        if (gameUsers != null)
        {
            gameUsers.add(userID);
        } else
        {
            throw new Exception("No such gameID or Users");
        }
    }

    @Override public List<Integer> listPlayersInGame(int gameID) throws Exception
    {
        List<Integer> gameUsers = Database.getInstance().getUsers(gameID);
        if (gameUsers != null)
        {
            return gameUsers;
        } else
        {
            throw new Exception("There are no players in the game to list in game: " + gameID);
        }

    }
}
