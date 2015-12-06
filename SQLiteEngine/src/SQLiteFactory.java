import server.plugin.IPersistenceEngine;
import server.plugin.IPersistenceFactory;
import shared.definitions.exceptions.CatanException;

/**
 * Created by liukaichi on 12/2/2015.
 */
public class SQLiteFactory implements IPersistenceFactory
{
    @Override public IPersistenceEngine createPersistenceEngine(int commandsBetweenSaves)
    {
        try
        {
            SQLiteEngine.initialize();
            return new SQLiteEngine(commandsBetweenSaves);
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

