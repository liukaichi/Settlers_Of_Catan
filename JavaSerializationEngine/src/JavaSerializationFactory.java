import server.plugin.IPersistenceEngine;
import server.plugin.IPersistenceFactory;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class JavaSerializationFactory implements IPersistenceFactory
{
    @Override public IPersistenceEngine createPersistenceEngine(int commandsBetweenSaves)
    {
        return null;
    }
}
