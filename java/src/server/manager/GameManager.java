package server.manager;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.ServerModel;
import server.facade.IGameFacade;
import server.facade.IGamesFacade;
import server.plugin.IPersistenceEngine;
import server.util.FileUtils;
import shared.communication.moveCommands.MoveCommand;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.GameQueryException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Manager that holds all of the games. Various options that the facades take use this manager. Uses the singleton
 * pattern so that there will only be one instance of the games available on the server.
 *
 * @see IGameFacade
 * @see IGamesFacade
 * @see UserManager
 */
public class GameManager
{
    private static GameManager _instance = null;
    private static AIManager aiManager;

    private Map<Integer, GameInfo> games;
    private Map<Integer, ServerModel> models;
    private IPersistenceEngine persistenceEngine;

    private GameManager()
    {
        games = new HashMap<>();
        models = new HashMap<>();
        aiManager = new AIManager();
        this.addExistingGames();
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

    private void addExistingGames()
    {
        GameInfo game0Info = FileUtils.getGameInfoFromFile("sample/serverDefaults/", "gameInfo-0");
        games.put(game0Info.getId(), game0Info);
        ServerModel game0Model = FileUtils.getModelFromFile("sample/serverDefaults/", "game-0");
        models.put(game0Info.getId(), game0Model);

        GameInfo game1Info = FileUtils.getGameInfoFromFile("sample/serverDefaults/", "gameInfo-1");
        games.put(game1Info.getId(), game1Info);
        ServerModel game1Model = FileUtils.getModelFromFile("sample/serverDefaults/", "game-1");
        models.put(game1Info.getId(), game1Model);

        GameInfo game2Info = FileUtils.getGameInfoFromFile("sample/serverDefaults/", "gameInfo-2");
        games.put(game2Info.getId(), game2Info);
        ServerModel game2Model = FileUtils.getModelFromFile("sample/serverDefaults/", "game-2");
        models.put(game2Info.getId(), game2Model);

        GameInfo customGameInfo = FileUtils.getGameInfoFromFile("sample/serverDefaults/", "gameInfo-CS340");
        games.put(customGameInfo.getId(), customGameInfo);
        ServerModel customGame = FileUtils.getModelFromFile("sample/serverDefaults/", "game-CS340");
        models.put(customGameInfo.getId(), customGame);
    }

    /**
     * Gets the Game with the given gameID.
     *
     * @param gameID the id of the game to get.
     * @return the Model of the game that matches the games requested. Null if the game doesn't exist.
     */
    public ServerModel getGame(int gameID)
    {
        return models.get(gameID);
    }

    /**
     * Lists the games that are currently in the server.
     *
     * @return a list of games that are currently in the server.
     */
    public List<GameInfo> listGames()
    {
        List<GameInfo> result = new ArrayList<>();
        List<ServerModel> list = persistenceEngine.getAllGames();
        for (ServerModel model : list)
        {
            result.add(model.getGameInfo());
        }
        return result;
    }

    /**
     * Places the player with the given id in the game specified with the given color.
     *
     * @param playerID the id of the player joining the game.
     * @param gameID   the id of the game to join.
     * @param color    the color that the player is joining the game with.
     */
    public void joinGame(int playerID, int gameID, CatanColor color) throws GameQueryException
    {
        GameInfo game = persistenceEngine.loadGame(gameID).getGameInfo();
        //GameInfo game = games.get(gameID);
        if(game != null)
        {
            if(game.playerAlreadyJoined(playerID))
            {
                games.get(gameID).setPlayerColor(color, playerID);
                models.get(gameID).setPlayerColor(color, playerID);
                ServerModel updatedModel = persistenceEngine.updateColor(gameID, color, playerID);
                games.replace(gameID, updatedModel.getGameInfo());
                models.replace(gameID, updatedModel);
                return;
            }
            else if (game.getPlayers().size() < 4) {


                //add player to game
                //update local copy
                User user = UserManager.getInstance().getUser(playerID);
                PlayerInfo player = new PlayerInfo(user.getPlayerID(), user.getUserName(), color);
                player.setPlayerIndex(game.getPlayers().size());
                persistenceEngine.addPlayerToGame(player, gameID);
                game.addPlayer(player);
                models.get(gameID).addPlayer(player);
                return;
            } else {

                if (!game.hasPlayer(playerID)) {
                    throw new GameQueryException("Four players in game. Unable to join.");
                } else {
                    return;
                }
            }
        }
        throw new GameQueryException("Game id not found.");
        /*for (GameInfo game : games.values())
        {
            if (game.getId() == gameID)
            {
                if (game.getPlayers().size() < 4)
                {
                    User user = UserManager.getInstance().getUser(playerID);
                    PlayerInfo player = new PlayerInfo(user.getPlayerID(), user.getUserName(), color);
                    game.addPlayerToGame(player);
                    return;
                } else
                {

                    if (!game.hasPlayer(playerID))
                    {
                        throw new GameQueryException("Four players in game. Unable to join.");
                    } else
                    {
                        return;
                    }
                }
            }
        }
        throw new GameQueryException("Game id not found.");*/
    }

    /**
     * Adds an AI player to the game with the given id.
     *
     * @param gameID the id of the game to add the AI to.
     * @param type   the type of AI to add to the game.
     */
    public void addAI(int gameID, AIType type) throws GameQueryException
    {
        GameInfo game = games.get(gameID);
        if (game == null)
        {
            throw new GameQueryException("Game id not found.");
        }
        int joinedGameSize = game.getPlayers().size();
        if (joinedGameSize < 4)
        {
            //game.addPlayerToGame(aiPlayers.get(joinedGameSize - 1));
            try
            {
                game.addPlayer(aiManager.createAIPlayer(game));
            } catch (CatanException e)
            {
                throw new GameQueryException(e.getMessage());
            }
        } else
        {
            throw new GameQueryException("Unable to add AI. Four players in game already.");
        }
    }

    /**
     * Lists the AI's available to add to a game.
     *
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
        int newGameID = persistenceEngine.getNextGameID();
        //int newGameID = games.size();
        GameInfo game = new GameInfo(newGameID, name);
        games.put(newGameID, game);
        ServerModel model = new ServerModel(game, randomTiles, randomNumbers, randomPorts);
        models.put(newGameID, model);
        persistenceEngine.addGame(model, name);

        return game;
    }

    public void setPersistenceEngine(IPersistenceEngine persistenceEngine)
    {
        this.persistenceEngine = persistenceEngine;
        List<ServerModel> serverModelList = persistenceEngine.getAllGames();
        for (ServerModel serverModel : serverModelList)
        {
            games.put(serverModel.getGameInfo().getId(), serverModel.getGameInfo());
            models.put(serverModel.getGameInfo().getId(), serverModel);
        }
    }

    public void saveCommand(int gameID, MoveCommand moveCommand)
    {
        persistenceEngine.saveGame(gameID, moveCommand, getGame(gameID));
    }

/*    public void addPlayerToGame(int playerID, int gameID)
    {
        User user = persistenceEngine.getUser(playerID);
        persistenceEngine.addPlayerToGame(player, gameID);
    }*/
}
