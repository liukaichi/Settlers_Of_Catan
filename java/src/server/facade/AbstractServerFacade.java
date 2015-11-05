package server.facade;

import shared.facade.IGameFacade;
import shared.facade.IGamesFacade;
import shared.facade.IMovesFacade;
import shared.facade.IUserFacade;

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
