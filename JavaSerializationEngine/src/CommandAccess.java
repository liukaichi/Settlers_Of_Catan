import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;

import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class CommandAccess implements ICommandAccess, IAccess
{
    @Override public void initializeTable()
    {

    }

    @Override public void saveCommand(int gameID, MoveCommand command) throws Exception
    {

    }

    @Override public int getNumberOfCommandsInGame(int gameID) throws Exception
    {
        return 0;
    }

    @Override public List<MoveCommand> getAllCommands(int gameID) throws Exception
    {
        return null;
    }

    @Override public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception
    {
        return null;
    }

    @Override public MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception
    {
        return null;
    }
}
