import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import server.ServerModel;
import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;

import java.io.ObjectInputStream;
import java.rmi.ServerException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SQL Database Access Object for Commands.
 */
public class CommandAccess implements ICommandAccess
{
    private final static Logger LOGGER = Logger.getLogger(CommandAccess.class.getName());

    private SQLiteEngine engine;

    private CommandAccess()
    {

    }

    public CommandAccess(SQLiteEngine engine)
    {
        this();
        this.engine = engine;
    }

    @Override public void saveCommand(int gameID, MoveCommand command) throws Exception
    {
        PreparedStatement stmt = null;
        try
        {
            String query = "INSERT INTO Command (SequenceNo, Command, GameID) " +
                    "VALUES ((SELECT IFNULL(MAX(SequenceNo), 0) + 1 FROM Command WHERE GameID = ?),?,?)";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setInt(1, gameID);
            stmt = engine.addBlobToStatement(stmt, 2, command);
            stmt.setInt(3, gameID);
            if (stmt.executeUpdate() != 1)
            {
                throw new ServerException("Query wasn't executed properly to add a game");
            }
        } catch (SQLException e)
        {
            LOGGER.severe("Failed to save command");
            throw e;
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
        } catch (SQLException e)
        {
            LOGGER.severe("Failed to get number of commands in game");
            throw e;
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
                return result;
            }
            while (rs.next())
            {
                MoveCommand command;

                byte modelBytes[] = rs.getBytes(1);
                ObjectInputStream stream = new ObjectInputStream(new ByteInputStream(modelBytes, modelBytes.length));
                Object o;
                while ((o = stream.readObject()) != null)
                {
                    command = (MoveCommand) o;
                    result.add(command);
                }
            }
        } catch (SQLException e)
        {
            LOGGER.severe("Failed to get all commands after " + sequenceNumber);
            throw e;
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
//                command = (MoveCommand) rs.getBlob(1);
                byte modelBytes[] = rs.getBytes(1);
                ObjectInputStream stream = new ObjectInputStream(new ByteInputStream(modelBytes, modelBytes.length));
                Object o;
                while ((o = stream.readObject()) != null)
                {

                    command = (MoveCommand) o;
                }
            }
            if (command == null)
                throw new Exception("Command does not exist");
            else
            {
                return command;
            }

        } catch (SQLException e)
        {
            LOGGER.severe("Failed to get command");
            throw e;
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
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS Command (" +
                    "SequenceNo INTEGER NOT NULL, " +
                    "Command BLOB NOT NULL, " +
                    "GameID INTEGER NOT NULL, "
                    + "FOREIGN KEY(GameID) REFERENCES Game(GameID)" +
                    ")");
            // @formatter:on
        } catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to create Command table", e);
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }
}
