import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;

import java.util.List;

/**
 * SQL Database Access Object for Commands.
 */
public class CommandAccess implements ICommandAccess, IAccess
{
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

    }
}
