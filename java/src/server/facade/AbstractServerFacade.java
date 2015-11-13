package server.facade;

import client.data.GameInfo;
import server.manager.GameManager;
import server.manager.User;
import server.manager.UserManager;
import shared.communication.Credentials;
import shared.communication.ListAIResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.AIType;
import shared.definitions.exceptions.SignInException;

import java.util.ArrayList;
import java.util.List;

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

    @Override public User signInUser(Credentials credentials) throws SignInException
    {
        User user = UserManager.getInstance().userLogin(credentials);
        if (user == null)
        {
            throw new SignInException("Failed to login - bad username or password.");
        } else
        {
            return user;
        }
    }

    @Override public User registerUser(Credentials credentials) throws SignInException
    {
        User user = UserManager.getInstance().userRegister(credentials);
        if (user == null)
        {
            throw new SignInException("Failed to register - someone already has that username.");
        } else
        {
            return user;
        }
    }

    @Override public ListAIResponse listAI()
    {
        List<AIType> types = GameManager.getInstance().listAI();
        return new ListAIResponse(types);
    }


    @Override public ListGamesResponse listGames()
    {
        List<GameInfo> games = GameManager.getInstance().listGames();
        return new ListGamesResponse(games);
    }
}
