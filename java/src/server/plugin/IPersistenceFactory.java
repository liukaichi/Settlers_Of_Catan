package server.plugin;

/**
 * Specifies the pattern which plugins must follow for their factory. All Factories inside of plugins must implement the static methods in this class.
 */
public interface IPersistenceFactory
{
    /**
     * Creates a PersistenceEngine. This method must be overridden to produce anything.
     *
     * @param commandsBetweenSaves the number of commands in between full saves of the model.
     * @return a PersistenceEngine.
     */
    IPersistenceEngine createPersistenceEngine(int commandsBetweenSaves);
}
