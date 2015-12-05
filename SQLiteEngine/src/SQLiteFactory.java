import server.plugin.IPersistenceEngine;
import server.plugin.IPersistenceFactory;

/**
 * Created by liukaichi on 12/2/2015.
 */
public class SQLiteFactory implements IPersistenceFactory
{
    @Override public IPersistenceEngine createPersistenceEngine(int commandsBetweenSaves)
    {
        return new SQLiteEngine(commandsBetweenSaves);
    }
}

