import server.plugin.IPersistenceEngine;
import server.plugin.IPersistenceFactory;
import shared.definitions.exceptions.CatanException;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class JavaSerializationFactory implements IPersistenceFactory
{
    @Override public IPersistenceEngine createPersistenceEngine(int commandsBetweenSaves)
    {
        try
        {
            JavaSerializationEngine.initialize();
            return new JavaSerializationEngine(commandsBetweenSaves);
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
