import server.plugin.IGamePersistenceEngine;
import server.plugin.IPersistenceFactory;
import server.plugin.IUserPersistenceEngine;

/**
 * Created by liukaichi on 12/2/2015.
 */
public class SQLiteFactory implements IPersistenceFactory
{

    @Override public IUserPersistenceEngine createUserPersistenceEngine()
    {
        return null;
    }

    @Override public IGamePersistenceEngine createGamePersistenceEngine(int commandsBetweenSaves)
    {
        return null;
    }
}
