package server.plugin;

/**
 * Specifies the pattern which plugins must follow for their factory. All Factories inside of plugins must implement the static methods in this class.
 */
public interface IPersistenceFactory
{
    /**
     * Creates a UserPersistenceEngine. This method must be overridden to produce anything.
     *
     * @return a UserPersistenceEngine.
     */
    public IUserPersistenceEngine createUserPersistenceEngine();

    /**
     * Creates a GamePersistenceEngine. This method must be overridden to produce anything.
     *
     * @param commandsBetweenSaves the number of commands in between full saves of the model.
     * @return a GamePersistenceEngine.
     */
    public IGamePersistenceEngine createGamePersistenceEngine(int commandsBetweenSaves);
}
