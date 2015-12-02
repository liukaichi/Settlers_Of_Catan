import server.ServerModel;
import server.plugin.IGamePersistenceEngine;
import shared.communication.CatanCommand;

/**
 * Created by liukaichi on 12/2/2015.
 */
public class SQLiteGameEngine implements IGamePersistenceEngine
{
    @Override public boolean saveCommand(int gameID, CatanCommand catanCommand, ServerModel game)
    {
        return false;
    }

    @Override public ServerModel loadGame(int gameID)
    {
        return null;
    }

    @Override public void addPlayerToGame(int playerID, int gameID)
    {

    }
}
