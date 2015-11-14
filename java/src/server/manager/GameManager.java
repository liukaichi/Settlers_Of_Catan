package server.manager;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.ServerModel;
import server.facade.IGameFacade;
import server.facade.IGamesFacade;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.GameQueryException;
import shared.model.player.Player;

import java.util.*;

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
    private List<PlayerInfo> aiPlayers;

    private GameManager()
    {
        games = new HashMap<>();
        models = new HashMap<>();
        aiManager = new AIManager();
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
        /*
        for (int i = 1; i < 3; ++i)
        {
            GameInfo game = new GameInfo(i, "Game_" + i);
            game.addPlayer(new PlayerInfo(0, "Cache", CatanColor.YELLOW));
            game.addPlayer(new PlayerInfo(1, "Amanda", CatanColor.BLUE));
            game.addPlayer(new PlayerInfo(2, "Justin", CatanColor.ORANGE));
            game.addPlayer(new PlayerInfo(3, "David", CatanColor.BROWN));
            games.put(i, game);
        }
        */
        //GameInfo game1 = ;
    }

    /**
     * Gets the Game with the given gameID.
     *
     * @param gameID the id of the game to get.
     * @return the Model of the game that matches the games requested.
     */
    public ServerModel getGame(int gameID)
    {
        games.get(gameID); //TODO this should be used somehow.
        return new ServerModel();
    }

    /**
     * Lists the games that are currently in the server.
     *
     * @return a list of games that are currently in the server.
     */
    public List<GameInfo> listGames()
    {
        return (List<GameInfo>) games.values();
    }

    /**
     * Places the player with the given id in the game specified with the given color.
     *
     * @param player the id of the player joining the game.
     * @param gameID the id of the game to join.
     * @param color  the color that the player is joining the game with.
     */
    public void joinGame(PlayerInfo player, int gameID, CatanColor color) throws GameQueryException
    {
        for (GameInfo game : games.values())
        {
            if (game.getId() == gameID)
            {
                if (game.getPlayers().size() < 4)
                {
                    game.addPlayer(player);
                } else
                {
                    throw new GameQueryException("Four players in game. Unable to join.");
                }
            }
        }
        throw new GameQueryException("Game id not found.");
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
            //game.addPlayer(aiPlayers.get(joinedGameSize - 1));
            try
            {
                game.addPlayer(aiManager.createAIPlayer(game));
            }
            catch (CatanException e)
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
        int newGameID = games.size() + 1;
        GameInfo game = new GameInfo(newGameID, name);
        games.put(newGameID, game);
        return game;
    }
    private class AIManager
    {
        ArrayList<String> aiNames;
        private int id = 0;

        public AIManager()
        {
            aiNames = new ArrayList<>();
            aiNames.add("Miguel");
            aiNames.add("Hannah");
            aiNames.add("Quinn");
            aiNames.add("Ken");
            aiNames.add("Squall");
        }

        private Player createAIPlayer(GameInfo gameInfo) throws CatanException
        {
            ArrayList<CatanColor> usedColors = new ArrayList<>();
            ArrayList<String> usedNames = new ArrayList<>();
            for(PlayerInfo info : gameInfo.getPlayerInfos())
            {
                usedColors.add(info.getColor());
                usedNames.add(info.getName());
            }
            PlayerInfo aiPlayerInfo = new PlayerInfo(--id,randomName(usedNames),randomColor(usedColors));
            Player player = new Player(aiPlayerInfo);
            return player;
        }

        private CatanColor randomColor(ArrayList<CatanColor> usedColors)
        {
            Random rand = new Random();
            CatanColor color = CatanColor.values()[rand.nextInt(8)];
            while(usedColors.contains(color))
            {
                color = CatanColor.values()[rand.nextInt(8)];
            }
            return color;
        }

        private String randomName(ArrayList<String> usedNames)
        {
            Random rand = new Random();
            String name = aiNames.get(rand.nextInt(4));
            while(usedNames.contains(name))
            {
                name = aiNames.get(rand.nextInt(4));
            }
            return name;
        }
    }
}
