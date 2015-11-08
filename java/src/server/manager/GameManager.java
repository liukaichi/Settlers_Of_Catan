package server.manager;

import client.data.GameInfo;
import server.ServerModel;
import server.facade.*;
import shared.definitions.AIType;
import shared.definitions.CatanColor;

import java.util.List;

/**
 * The Manager that holds all of the games. Various options that the facades take use this manager. Uses the singleton
 * pattern so that there will only be one instance of the games available on the server.
 * @see IGameFacade
 * @see IGamesFacade
 * @see UserManager
 */
public class GameManager
{
    private static GameManager _instance = null;

    private GameManager()
    {

    }

    /**
     * Singleton pattern for GameManager.
     * @return a singleton instance for GameManager.
     */
    public static GameManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new GameManager();
        }
        return _instance;
    }

    /**
     * Gets the Game with the given gameID.
     * @param gameID the id of the game to get.
     * @return the Model of the game that matches the games requested.
     */
    public ServerModel getGame(int gameID)
    {
        return null;
    }

    /**
     * Lists the games that are currently in the server.
     * @return a list of games that are currently in the server.
     */
    public List<GameInfo> listGames()
    {
        return null;
    }

    /**
     * Places the player with the given id in the game specified with the given color.
     * @param playerID the id of the player joining the game.
     * @param gameID the id of the game to join.
     * @param color the color that the player is joining the game with.
     */
    public void joinGame(int playerID, int gameID, CatanColor color)
    {

    }

    /**
     * Adds an AI player to the game with the given id.
     * @param gameID the id of the game to add the AI to.
     * @param type the type of AI to add to the game.
     */
    public void addAI(int gameID, AIType type)
    {

    }

    /**
     * Lists the AI's available to add to a game.
     * @return a list of AI's available in the server.
     */
    public List<AIType> listAI()
    {
        return null;
    }
}
