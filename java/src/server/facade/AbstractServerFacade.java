package server.facade;

/**
 * The AbstractServerFacade allows singleton access to a facade. This can be the real ServerFacade, or the
 * MockServerFacade for testing purposes. This allows for Dependency injection.
 * @see ServerFacade
 * @see MockServerFacade
 */
public abstract class AbstractServerFacade implements IGameFacade, IGamesFacade, IMovesFacade, IUserFacade
{
    private static AbstractServerFacade _instance;

    /**
     * Singleton pattern to return either the real ServerFacade, or the MockServerFacade
     * @return the singleton instance.
     */
    public static AbstractServerFacade getInstance()
    {
        if (_instance == null)
        {
            _instance = new ServerFacade();
        }
        return _instance;

    }

    /**
     * Allows a MockFacade to be used in place of a real one. This allows for Dependency injection.
     * This can be useful for testing purposes.
     */

    public static void setFacade(AbstractServerFacade facade)
    {
        _instance = facade;
    }
}
