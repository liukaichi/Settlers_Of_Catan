import server.plugin.IGameRelationAccess;

import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameRelationAccess implements IGameRelationAccess, IAccess
{
    @Override public void initializeTable()
    {

    }

    @Override public void addUserToGame(int userID, int gameID) throws Exception
    {

    }

    @Override public List<Integer> listPlayersInGame(int gameID) throws Exception
    {
        return null;
    }
}
