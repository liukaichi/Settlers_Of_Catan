import client.data.PlayerInfo;
import server.ServerModel;
import server.manager.User;
import server.plugin.IPersistenceEngine;
import shared.communication.Credentials;
import shared.communication.moveCommands.MoveCommand;
import shared.definitions.exceptions.CatanException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Persistence Engine that Uses SQLite as it's database.
 */
public class SQLiteEngine extends IPersistenceEngine
{
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE_DIRECTORY = "plugins" + File.separator + "SQLiteEngine";
    private static final String DATABASE_FILE = "CatanDatabase.sqlite";
    private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY + File.separator + DATABASE_FILE;

    private static final int DEFAULT_COMMAND_BETWEEN_SAVES = 10;
    private static final Logger LOGGER = Logger.getLogger(SQLiteEngine.class.getName());
    private int commandsBetweenSaves = DEFAULT_COMMAND_BETWEEN_SAVES;
    private Connection connection;
    private UserAccess userAccess;
    private GameAccess gameAccess;
    private CommandAccess commandAccess;
    private GameRelationAccess gameRelationAccess;

    private SQLiteEngine()
    {
        connection = null;

        userAccess = new UserAccess(this);
        gameAccess = new GameAccess(this);
        commandAccess = new CommandAccess(this);
        gameRelationAccess = new GameRelationAccess(this);
    }

    /**
     * Creates a SQLiteEngine with the specified number of saves between commands.
     *
     * @param commandsBetweenSaves the number of commands to save before saving the entire model.
     */
    public SQLiteEngine(int commandsBetweenSaves)
    {
        this();
        this.commandsBetweenSaves = commandsBetweenSaves;

    }

    /**
     * A method called once to load the driver for the database.
     */
    public static void initialize() throws CatanException
    {
        LOGGER.info("Initializing Database Connection");
        try
        {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e)
        {
            LOGGER.severe("Could not load database driver");
            throw new CatanException("Could not load database driver", e);
        }
    }

    /**
     * Safely closes a connection.
     *
     * @param connection the connection to close.
     */
    public static void safeClose(Connection connection)
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            } catch (SQLException e)
            {
                LOGGER.severe("Connection not safely closed");
            }
        }
    }

    /**
     * Safely closes a statement.
     *
     * @param statement the statement to close.
     */
    public static void safeClose(Statement statement)
    {
        if (statement != null)
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                LOGGER.severe("Statement not safely closed");
            }
        }
    }

    /**
     * Safely closes a prepared statement
     *
     * @param statement the PreparedStatement to close
     */
    public static void safeClose(PreparedStatement statement)
    {
        if (statement != null)
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                LOGGER.severe("PreparedStatement not safely closed");
            }
        }
    }

    /**
     * Safely closes a result set.
     *
     * @param resultSet the ResultSet to close.
     */
    public static void safeClose(ResultSet resultSet)
    {
        if (resultSet != null)
        {
            try
            {
                resultSet.close();
            } catch (SQLException e)
            {
                LOGGER.severe("ResultSet not safely closed");
            }
        }
    }

    @Override public boolean saveGame(int gameID, MoveCommand moveCommand, ServerModel game)
    {
        int currentNumberOfCommands = game.getVersion(); //hopefully the version number is the same as commandCount
        if (currentNumberOfCommands % commandsBetweenSaves == 0) //it's time to save the model
        {
            try
            {
                startTransaction();
                gameAccess.updateModel(gameID, game);
                endTransaction(true);
            } catch (Exception e)
            {
                endTransaction(false);
                LOGGER.log(Level.SEVERE, "Model didn't update correctly", e);
            }
        }
        try
        {
            startTransaction();
            commandAccess.saveCommand(gameID, moveCommand);
            endTransaction(true);
            return true;
        } catch (Exception e)
        {
            endTransaction(false);
            LOGGER.log(Level.SEVERE, "Command didn't update correctly", e);
        }
        return false;
    }

    @Override public int registerUser(Credentials credentials)
    {
        try
        {
            startTransaction();
            int userID = userAccess.registerUser(credentials);
            endTransaction(true);
            return userID;
        } catch (Exception e)
        {
            endTransaction(false);
            return -1;
        }
    }

    @Override public ServerModel loadGame(int gameID)
    {
        try
        {
            startTransaction();
            ServerModel model = gameAccess.getGame(gameID);
            endTransaction(true);
            return model;
        } catch (Exception e)
        {
            endTransaction(false);
            return null;
        }
    }

    /**
     * @param player the playerID
     * @param gameID the gameID
     * @return the Server Model of the game after the player is added.
     */
    @Override public ServerModel addPlayerToGame(PlayerInfo player, int gameID)
    {
        try
        {
            startTransaction();
            gameRelationAccess.addUserToGame(player.getId(), gameID);
            ServerModel model = gameAccess.getGame(gameID);
            model.addPlayer(player);
            gameAccess.updateModel(gameID, model);
            endTransaction(true);
            return model;

        } catch (Exception e)
        {
            endTransaction(false);
        }

        return null;
    }

    @Override public User getUser(int id)
    {
        try
        {
            startTransaction();
            User user = userAccess.getUser(id);
            endTransaction(true);
            return user;
        } catch (Exception e)
        {
            endTransaction(false);
            return null;
        }
    }

    @Override public User getUser(Credentials credentials)
    {
        try
        {
            startTransaction();
            User user = userAccess.getUser(credentials);
            endTransaction(true);
            return user;
        } catch (Exception e)
        {
            endTransaction(false);
            return null;
        }
    }

    @Override public boolean startTransaction()
    {
        LOGGER.log(Level.INFO, "Starting Transaction");
        assert connection == null;
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL);
            connection.setAutoCommit(false);
        } catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Could not connect to database. Make sure " + DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
            return false;
        }
        return true;
    }

    @Override public boolean endTransaction(boolean commit)
    {
        LOGGER.entering(getClass().getName(), "endTransaction");
        try
        {
            if (commit)
            {
                connection.commit();
                LOGGER.log(Level.INFO, "Ending Transaction: Commit");
            } else
            {
                connection.rollback();
                LOGGER.log(Level.INFO, "Ending Transaction: Roll Back");
            }
        } catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Could not end transaction", e);
            return false;
        } finally
        {
            safeClose(connection);
            connection = null;
        }
        return true;
    }

    @Override public boolean addGame(ServerModel model, String name)
    {
        LOGGER.log(Level.FINE, "Adding Game...");
        startTransaction();
        try
        {

            gameAccess.addGame(model, name);
            endTransaction(true);
        } catch (Exception e)
        {
            endTransaction(false);
            LOGGER.log(Level.SEVERE, "", e);
            return false;
        }
        return true;
    }

    @Override public int getNextGameID()
    {
        try
        {
            startTransaction();
            int nextID = gameAccess.getNextGameID();
            endTransaction(true);
            return nextID;
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Couldn't get next game ID", e);
            endTransaction(false);
            return -1;
        }
    }

    @Override public List<ServerModel> getAllGames()
    {
        try
        {
            startTransaction();
            List<ServerModel> result = gameAccess.getAllGames();
            endTransaction(true);
            return result;
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "", e);
            endTransaction(false);
            return null;
        }
    }

    @Override public Map<Integer, Credentials> getAllUsers()
    {
        try
        {
            startTransaction();
            Map<Integer, Credentials> result = userAccess.getAllUsers();
            endTransaction(true);
            return result;
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "", e);
            endTransaction(false);
            return null;
        }
    }

    @Override public void saveGame(ServerModel serverModel)
    {
        try
        {
            startTransaction();
            gameAccess.updateModel(serverModel.getGameInfo().getId(), serverModel);
            endTransaction(true);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "", e);
            endTransaction(false);
        }
    }

    @Override public List<MoveCommand> getCommandBatch(int gameID, int sequenceNo)
    {
        List<MoveCommand> commands = new ArrayList<>();
        try
        {
            startTransaction();
            commands = commandAccess.getAllCommandsAfter(gameID, sequenceNo);
            endTransaction(true);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "", e);
            endTransaction(false);
        }
        return commands;
    }

    /**
     * Allows a program to determine if a transaction is still happening.
     *
     * @return true if there is a current connection, false if not.
     */
    public boolean inTransaction()
    {
        return (connection != null);
    }

    public Connection getConnection()
    {
        return connection;
    }

    /**
     * Creates all of the tables in the database if they do not currently exist.
     * The tables to be created are the User, Game, Command, and Game Relation Tables.
     */
    @Override
    public void initializeDatabase()
    {
        try
        {
            boolean fileCreated = false;
            Path file = Paths.get("plugins");
            if (!Files.exists(file))
            {
                Files.createDirectory(file);
            }
            Path db = Paths.get("plugins", "SQLiteEngine");
            if (!Files.exists(db))
            {
                Files.createDirectory(db);
            }
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Failed to create folders for data persistence", e);
        }
        LOGGER.info("Initializing Tables");
        try
        {
            startTransaction();
            userAccess.initialize();
            gameAccess.initialize();
            commandAccess.initialize();
            gameRelationAccess.initialize();
            endTransaction(true);
        } catch(Exception e)
        {
            endTransaction(false);
        }

    }

    public PreparedStatement addBlobToStatement(PreparedStatement stmt, int index, Object obj) throws Exception
    {
        try
        {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(byteStream);
            stream.writeObject(obj);
            stream.writeObject(null);
            stream.close();
            byte byteArray[] = byteStream.toByteArray();


/* To test written Object output.
            ByteArrayInputStream input = new ByteArrayInputStream(byteArray);
            ObjectInputStream in = new ObjectInputStream(input);
            ServerModel model = (ServerModel) in.readObject();
*/

            stmt.setBytes(index, byteArray);
            return stmt;
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to Blob the Object.");
            throw e;
        }
    }

}
