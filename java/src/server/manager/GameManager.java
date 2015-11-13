package server.manager;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.ServerModel;
import server.facade.IGameFacade;
import server.facade.IGamesFacade;
import shared.definitions.AIType;
import shared.definitions.CatanColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<Integer, GameInfo> gameInfoMap;

    private List<PlayerInfo> aiPlayers;

    private GameManager()
    {
        gameInfoMap = new HashMap<>();
        this.addDefaultAIs();
        this.addDefaultGames();
    }

    /**
     * Singleton pattern for GameManager.
     *
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

    private void addDefaultAIs()
    {
        aiPlayers = new ArrayList<>();
        aiPlayers.add(new PlayerInfo(-2, "Miguel", CatanColor.PUCE));
        aiPlayers.add(new PlayerInfo(-3, "Quinn", CatanColor.PURPLE));
        aiPlayers.add(new PlayerInfo(-4, "Hannah", CatanColor.RED));
    }

    private void addDefaultGames()
    {
        for (int i = 1; i < 3; ++i)
        {
            GameInfo game = new GameInfo(i, "Game_" + i);
            game.addPlayer(new PlayerInfo(0, "Cache", CatanColor.YELLOW));
            game.addPlayer(new PlayerInfo(1, "Amanda", CatanColor.BLUE));
            game.addPlayer(new PlayerInfo(2, "Justin", CatanColor.ORANGE));
            game.addPlayer(new PlayerInfo(3, "David", CatanColor.BROWN));
            gameInfoMap.put(i, game);
        }
    }

    /**
     * Gets the Game with the given gameID.
     * @param gameID the id of the game to get.
     * @return the Model of the game that matches the games requested.
     */
    public ServerModel getGame(int gameID)
    {
        gameInfoMap.get(gameID); //TODO this should be used somehow.
        return new ServerModel();
    }

    /**
     * Lists the games that are currently in the server.
     * @return a list of games that are currently in the server.
     */
    public List<GameInfo> listGames()
    {
        return (List<GameInfo>) gameInfoMap.values();
    }

    /**
     * Places the player with the given id in the game specified with the given color.
     * @param player the id of the player joining the game.
     * @param gameID the id of the game to join.
     * @param color the color that the player is joining the game with.
     */
    public void joinGame(PlayerInfo player, int gameID, CatanColor color)
    {
        for (GameInfo game: gameInfoMap.values())
        {
            if (game.getId() == gameID)
            {
                if (game.getPlayers().size() < 4)
                {
                    game.addPlayer(player);
                }
            }
        }
    }

    /**
     * Adds an AI player to the game with the given id.
     * @param gameID the id of the game to add the AI to.
     * @param type the type of AI to add to the game.
     */
    public void addAI(AIType type, int gameID)
    {
        GameInfo game = gameInfoMap.get(gameID);
        int joinedGameSize = game.getPlayers().size();
        if (joinedGameSize < 4)
        {
            game.addPlayer(aiPlayers.get(joinedGameSize - 1));
        }
    }

    /**
     * Lists the AI's available to add to a game.
     * @return a list of AI's available in the server.
     */
    public List<AIType> listAI()
    {
        List<AIType> types = new ArrayList<>();
        types.add(AIType.LARGEST_ARMY);
        return types;
    }

    public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        int version = gameInfoMap.size() + 1;
        GameInfo createdGame = new GameInfo(version, name);
        gameInfoMap.put(version, createdGame);
        return createdGame;
    }
}
