import client.data.PlayerInfo;
import server.ServerModel;
import server.manager.User;
import server.plugin.IPersistenceEngine;
import shared.communication.Credentials;
import shared.communication.moveCommands.MoveCommand;
import shared.definitions.CatanColor;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Persistince engine that users Java Serialization
 */
public class JavaSerializationEngine extends IPersistenceEngine
{
    private static final Logger LOGGER = Logger.getLogger(JavaSerializationEngine.class.getName());

    private CommandAccess commandAccess;
    private GameAccess gameAccess;
    private GameRelationAccess gameRelationAccess;
    private UserAccess userAccess;
    private int commandsBetweenSaves;

    private JavaSerializationEngine()
    {
        commandAccess = new CommandAccess();
        gameAccess = new GameAccess();
        gameRelationAccess = new GameRelationAccess();
        userAccess = new UserAccess();
    }

    public JavaSerializationEngine(int commandsBetweenSaves)
    {
        this();
        this.commandsBetweenSaves = commandsBetweenSaves;
    }

    @Override public boolean saveGame(int gameID, MoveCommand moveCommand, ServerModel game)
    {
        boolean saveGame = false;
        try
        {
            saveGame = (commandAccess.getNumberOfCommandsInGame(gameID) % commandsBetweenSaves) == 0;
            LOGGER.info("Number of commands in game divisible by set amount: " + saveGame);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to get number of commands", e);
            return false;
        }
        if (saveGame)
        {
            try
            {
                gameAccess.updateModel(gameID, game);
                return true;
            } catch (Exception e)
            {
                LOGGER.log(Level.SEVERE, "Failed to update game", e);
            }
        } else
        {
            try
            {
                commandAccess.saveCommand(gameID, moveCommand);
                return true;
            } catch (Exception e)
            {
                LOGGER.log(Level.SEVERE, "Failed to save command", e);
            }
        }
        return false;
    }

    @Override public ServerModel loadGame(int gameID)
    {
        try
        {
            return gameAccess.getGame(gameID);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to load game", e);
            return null;
        }
    }

    @Override public ServerModel addPlayerToGame(PlayerInfo player, int gameID)
    {
        try
        {
            gameRelationAccess.addUserToGame(player.getId(), gameID);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to add player to game", e);
        }
        return null;
    }

    @Override public int registerUser(Credentials credentials)
    {
        try
        {
            return userAccess.registerUser(credentials);
        } catch (Exception e)
        {

            LOGGER.log(Level.SEVERE, "Failed to register user.", e);
            return -1;
        }
    }

    @Override public User getUser(int id)
    {
        try
        {
            return userAccess.getUser(id);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to get user.", e);
            return null;
        }
    }

    @Override public User getUser(Credentials credentials)
    {
        try
        {
            return userAccess.getUser(credentials);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to get user.", e);
            return null;
        }
    }

    @Override public boolean startTransaction()
    {
        return false;
    }

    @Override public boolean endTransaction(boolean commit)
    {
        return false;
    }

    @Override public boolean addGame(ServerModel model, String name)
    {
        try
        {
            gameAccess.addGame(model, name);
            return true;
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to add game", e);
        }
        return false;
    }

    @Override public int getNextGameID()
    {
        try
        {
            return gameAccess.getNextGameID();
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to get next game ID.", e);
            return -1;
        }
    }

    @Override public List<ServerModel> getAllGames()
    {
        try
        {
            return gameAccess.getAllGames();
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to get all games.", e);
            return null;
        }
    }

    @Override public Map<Integer, Credentials> getAllUsers()
    {
        return null;
    }

    @Override public ServerModel updateColor(int gameID, CatanColor color, int playerID)
    {
        return null;
    }

}
