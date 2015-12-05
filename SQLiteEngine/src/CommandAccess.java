import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;

import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
    public void saveCommand(int gameID, MoveCommand command) throws Exception
    {

    }
    @Override
    public int getNumberOfCommandsInGame(int gameID) throws Exception
    {
        return -1;
    }

    @Override
    public List<MoveCommand> getAllCommands(int gameID) throws Exception
    {
        return getAllCommandsAfter(gameID, 0);
    }

    @Override
    public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception
    {
        return null;
    }

    @Override
    public MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception
    {
        return null;
    }

    @Override public void initializeTable()
    {
        LOGGER.entering(getClass().getName(), "initializeTable");
        Statement stat = null;
        try
        {
            stat = engine.getConnection().createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS Command;");
            stat.executeUpdate("CREATE TABLE Command (" + "SequenceNo PRIMARY KEY AUTOINCREMENT INTEGER, " + "Command BLOB NOT NULL, "
                    + "GameID INTEGER REFERENCES Game(GameID) NOT NULL" + ")");

        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }
}
