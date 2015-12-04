import shared.communication.moveCommands.MoveCommand;

import java.util.List;

/**
 * SQL Database Access Object for Commands.
 */
public class CommandAccess
{
    private SQLiteEngine engine;
    public CommandAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    public void saveCommand(int gameID, MoveCommand command)
    {

    }

    public int getNumberOfCommandsInGame(int gameID)
    {
        return -1;
    }

    public List<MoveCommand> getAllCommands(int gameID)
    {
        return getAllCommandsAfter(gameID, 0);
    }

    public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber)
    {
        return null;
    }

    public MoveCommand getCommand(int gameID, int sequenceNumber)
    {
        return null;
    }

}
