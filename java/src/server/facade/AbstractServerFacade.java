package server.facade;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.manager.GameManager;
import server.manager.User;
import server.manager.UserManager;
import shared.communication.CreateGameResponse;
import shared.communication.Credentials;
import shared.communication.ListAIResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.InvalidCredentialsException;

import java.util.List;

/**
 * The AbstractServerFacade allows singleton access to a facade. This can be the real ServerFacade, or the
 * MockServerFacade for testing purposes. This allows for Dependency injection.
 *
 * @see ServerFacade
 * @see MockServerFacade
 */
public abstract class AbstractServerFacade implements IGameFacade, IGamesFacade, IMovesFacade, IUserFacade
{
    private static AbstractServerFacade _instance;

    /**
     * Singleton pattern to return either the real ServerFacade, or the MockServerFacade
     *
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

    @Override public User signInUser(Credentials credentials) throws InvalidCredentialsException
    {
        return UserManager.getInstance().userLogin(credentials);
    }

    @Override public User registerUser(Credentials credentials)
            throws InvalidCredentialsException, ExistingRegistrationException
    {
        return UserManager.getInstance().userRegister(credentials);
    }

    @Override public ListAIResponse listAI()
    {
        List<AIType> types = GameManager.getInstance().listAI();
        return new ListAIResponse(types);
    }

    @Override public void addAI(AIType aiType, int gameID)
    {
        GameManager.getInstance().addAI(aiType, gameID);
    }

    @Override public ListGamesResponse listGames()
    {
        List<GameInfo> games = GameManager.getInstance().listGames();
        return new ListGamesResponse(games);
    }

    @Override public CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts,
            String name)
    {
        return new CreateGameResponse(
                GameManager.getInstance().createGame(randomTiles, randomNumbers, randomPorts, name));
    }

    @Override public void joinGame(PlayerInfo player, int gameID, CatanColor color)
    {
        GameManager.getInstance().joinGame(player, gameID, color);
    }
}
