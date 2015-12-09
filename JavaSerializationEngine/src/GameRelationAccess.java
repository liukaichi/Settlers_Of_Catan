import database.Game;
import database.GameRegistry;
import server.plugin.IGameRelationAccess;

import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameRelationAccess implements IGameRelationAccess
{
    @Override public void initialize()
    {
        //not needed in java serialization
    }

    @Override public void addUserToGame(int userID, int gameID) throws Exception
    {
        //not needed in java serialization
    }

    @Override public List<Integer> listPlayersInGame(int gameID) throws Exception
    {
        //not needed in java serialization
        return null;
    }
}
