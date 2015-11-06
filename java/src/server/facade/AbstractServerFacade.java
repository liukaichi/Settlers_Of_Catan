package server.facade;

/**
 * Created by cstaheli on 11/4/2015.
 */
public abstract class AbstractServerFacade implements IGameFacade, IGamesFacade, IMovesFacade, IUserFacade
{
    private static AbstractServerFacade _instance;
    public static AbstractServerFacade getInstance()
    {
        if(_instance == null)
        {
            _instance = new ServerFacade();
        }
        return _instance;
    }
}
