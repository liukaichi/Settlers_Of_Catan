import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;

import java.rmi.ServerException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * SQL Database Access Object for Commands.
 */
public class CommandAccess implements ICommandAccess, IAccess
{
    private final static Logger LOGGER = Logger.getLogger(CommandAccess.class.getName());

    private SQLiteEngine engine;

    public CommandAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    @Override public void saveCommand(int gameID, MoveCommand command) throws Exception
    {
        PreparedStatement stmt = null;
        try
        {
            String query = "INSERT INTO Command (Command, GameID) VALUES (?,?)";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setBlob(1, (Blob) command);
            stmt.setInt(2, gameID);
            if (stmt.executeUpdate() != 1)
            {
                throw new ServerException("Query wasn't executed properly to add a game");
            }
        } finally
        {
            SQLiteEngine.safeClose(stmt);
        }
    }

    @Override public int getNumberOfCommandsInGame(int gameID) throws Exception
    {
        int count = -1;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "SELECT Count(\'x\') FROM Command WHERE GameID = ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setInt(1, gameID);

            rs = stmt.executeQuery();
            while (rs.next())
            {
                count = rs.getInt(1);
            }
            if (count == -1)
                throw new Exception("unable to count number of commands");
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(rs);
        }
        return count;
    }

    @Override public List<MoveCommand> getAllCommands(int gameID) throws Exception
    {
        return getAllCommandsAfter(gameID, 0);
    }

    @Override public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception
    {
        List<MoveCommand> result = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "SELECT Command FROM Command WHERE GameID = ? AND SequenceNo > ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setInt(1, gameID);
            stmt.setInt(2, sequenceNumber);

            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst())
            {
                return null;
            }
            while (rs.next())
            {
                MoveCommand command = (MoveCommand) rs.getBlob(1);
                result.add(command);
            }
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(rs);
        }
        return result;
    }

    @Override public MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "SELECT Command FROM Command WHERE GameID = ? AND SequenceNo = ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setInt(1, gameID);
            stmt.setInt(2, sequenceNumber);

            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst())
            {
                return null;
            }
            MoveCommand command = null;
            while (rs.next())
            {
                command = (MoveCommand) rs.getBlob(1);
            }
            if (command == null)
                throw new Exception("Command does not exist");
            else
            {
                return command;
            }
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(rs);
        }
    }

    @Override public void initialize()
    {
        LOGGER.entering(getClass().getName(), "initialize");
        Statement stat = null;
        try
        {
            // @formatter:off
            stat = engine.getConnection().createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS Command;");
            stat.executeUpdate("CREATE TABLE Command (" +
                    "SequenceNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Command BLOB NOT NULL, " +
                    "GameID INTEGER NOT NULL, "
                    + "FOREIGN KEY(GameID) REFERENCES Game(GameID)" +
                    ")");
            // @formatter:on
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }
}
