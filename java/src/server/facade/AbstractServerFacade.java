package server.facade;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.ServerModel;
import server.manager.*;
import shared.communication.CreateGameResponse;
import shared.communication.Credentials;
import shared.communication.ListAIResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.TurnStatus;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.GameQueryException;
import shared.definitions.exceptions.InvalidCredentialsException;
import shared.model.ClientModel;

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

    @Override public String addAI(int gameID, AIType type) throws GameQueryException
    {
        GameManager.getInstance().addAI(gameID, type);
        return "Success";
    }

    @Override public ListGamesResponse listGames()
    {
        List<GameInfo> games = GameManager.getInstance().listGames();
        return new ListGamesResponse(games);
    }

    @Override public String joinGame(int player, int gameID, CatanColor color) throws GameQueryException
    {

        GameManager.getInstance().joinGame(player, gameID, color);
        return "Success";

    }

    @Override public CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts,
            String name)
    {
        GameInfo game = GameManager.getInstance().createGame(randomTiles, randomNumbers, randomPorts, name);
        return new CreateGameResponse(game);
    }

    public ClientModel playAITurns(int gameID)
    {
        GameManager gameManager = GameManager.getInstance();
        ServerModel currentGame = gameManager.getGame(gameID);
        int currentTurnID = currentGame.getCurrentTurnID();
        AIManager aiManager = GameManager.getAIManager();
        AIUser aiUser = aiManager.getAIUser(currentTurnID);
        if (currentGame.getTradeOffer() != null)
        {
            aiUser = aiManager.getAIUser(currentGame.getPlayerID((currentGame.getTradeOffer().getReceiver())));
        }
        if (aiUser != null)
            aiUser.playTurn(gameID);

        return gameManager.getGame(gameID);
    }

    public void checkAIDiscards(int gameID)
    {
        GameManager gameManager = GameManager.getInstance();
        ServerModel currentGame = gameManager.getGame(gameID);
        int currentTurnID = currentGame.getCurrentTurnID();
        AIManager aiManager = GameManager.getAIManager();
        AIUser aiUser = aiManager.getAIUser(currentTurnID);


        if (currentGame.getTurnTracker().getStatus() == TurnStatus.Discarding)
        {
            for (PlayerInfo player : currentGame.getPlayerInfos())
            {
                if (player.getId() < 0)
                {
                    aiUser = aiManager.getAIUser(player.getId());
                    aiUser.discard(gameID);
                }
            }
        }
    }
}
