import server.ServerModel;
import server.manager.User;
import server.plugin.IPersistenceEngine;
import shared.communication.CatanCommand;
import shared.communication.Credentials;
import shared.definitions.exceptions.CatanException;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Persistence Engine that Uses SQLite as it's database.
 */
public class SQLiteEngine extends IPersistenceEngine
{
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE_DIRECTORY = "database";
    private static final String DATABASE_FILE = "database.sqlite";
    private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY + File.separator + DATABASE_FILE;

    private static final int DEFAULT_COMMAND_BETWEEN_SAVES = 10;
    private int commandsBetweenSaves = DEFAULT_COMMAND_BETWEEN_SAVES;

    private static final Logger LOGGER = Logger.getLogger(SQLiteEngine.class.getName());

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
    @Override public boolean saveGame(int gameID, CatanCommand catanCommand, ServerModel game)
    {
        int currentNumberOfCommands = getCurrentNumberOfCommands(gameID);
        if (commandsBetweenSaves % currentNumberOfCommands == 0) //it's time to save the model
        {
            try
            {
                gameAccess.updateModel(gameID,game);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            gameAccess.addCommand(gameID);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override public ServerModel loadGame(int gameID)
    {
        return null;
    }

    @Override public void addPlayerToGame(int playerID, int gameID)
    {

    }

    @Override public int registerUser(Credentials credentials)
    {
        return 23456;
    }

    @Override public User getUser(int id)
    {
        return null;
    }

    @Override public User getUser(Credentials credentials)
    {
        return null;
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

    @Override public boolean addUser(User user)
    {
        //userAccess.addUser(user);
        return false;
    }

    @Override public boolean addGame(ServerModel model, String name)
    {
        startTransaction();
        try
        {
            gameAccess.addGame(model,name);
            endTransaction(true);
        } catch (Exception e)
        {
            endTransaction(false);
            e.printStackTrace();
        }

        return false;
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

    private int getCurrentNumberOfCommands(int gameID)
    {
        try
        {
            return gameAccess.getNumberOfCommands(gameID);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * A method called once to load the driver for the database.
     */
    public static void initialize() throws CatanException
    {
        LOGGER.info("Initializing Database Connection");
        try
        {
            Class.forName(DRIVER); //TODO I'm not sure this works like I think it does...
        } catch (ClassNotFoundException e)
        {
            LOGGER.severe("Could not load database driver");
            throw new CatanException("Could not load database driver", e);
        }
    }

    /**
     * Creates all of the tables in the database if they do not currently exist.
     * The tables to be created are the User, Game, Command, and Game Relation Tables.
     */
    public void initializeTables()
    {
        LOGGER.info("Initializing Tables");
        initializeUser();
        initializeGame();
        initializeCommand();
        initializeGameRelation();
    }

    private void initializeUser()
    {
        //TODO User SQL Schema goes here.
    }

    private void initializeGame()
    {
        //TODO Game SQL Schema goes here.
    }

    private void initializeCommand()
    {
        //TODO Command SQL Schema goes here.
    }

    private void initializeGameRelation()
    {
        //TODO GameRelation SQL Schema goes here.
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

}
