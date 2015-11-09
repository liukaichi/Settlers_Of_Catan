package server.facade;

/**
 * The AbstractServerFacade allows singleton access to a facade. This can be the real ServerFacade, or the
 * MockServerFacade for testing purposes.
 * @see ServerFacade
 * @see MockServerFacade
 */
public abstract class AbstractServerFacade implements IGameFacade, IGamesFacade, IMovesFacade, IUserFacade
{
    private static AbstractServerFacade _serverInstance;
    private static AbstractServerFacade _mockInstance;
    private static boolean useRealServerFacade;

    /**
     * Singleton pattern to return either the real ServerFacade, or the MockServerFacade
     * @return the singleton instance.
     */
    public static AbstractServerFacade getInstance()
    {
        if (useRealServerFacade)
        {
            if (_serverInstance == null)
            {
                _serverInstance = new ServerFacade();
            }
            return _serverInstance;
        }
        else
        {
            if (_mockInstance == null)
            {
                _mockInstance = new MockServerFacade();
            }
            return _mockInstance;
        }
    }

    /**
     * Allows a MockFacade to be used in place of a real one. This can be useful for testing purposes.
     * @param useRealServerFacade whether to use the read ServerFacade, or to use the MockServerFacade.
     */
    public static void useRealServerFacade(boolean useRealServerFacade)
    {
        AbstractServerFacade.useRealServerFacade = useRealServerFacade;
    }
}
