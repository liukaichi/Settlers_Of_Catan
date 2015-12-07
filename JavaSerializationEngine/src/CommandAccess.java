import database.Database;
import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class CommandAccess implements ICommandAccess
{
    @Override public void initialize()
    {

    }

    @Override public void saveCommand(int gameID, MoveCommand command) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            commands.add(command);
        } else
        {
            throw new Exception("no such gameID: " + gameID);
        }
    }

    @Override public int getNumberOfCommandsInGame(int gameID) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands.size();
        } else
        {
            throw new Exception("no such gameID: " + gameID);
        }
    }

    @Override public List<MoveCommand> getAllCommands(int gameID) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands;
        } else
        {
            throw new Exception("no such gameID: " + gameID);
        }
    }

    @Override public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands.stream().skip(sequenceNumber).collect(Collectors.toList());
        } else
        {
            throw new Exception("no such gameID: " + gameID);
        }
    }

    @Override public MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands.get(sequenceNumber);
        } else
        {
            throw new Exception("no such gameID: " + gameID + " or sequenceNumber: " + sequenceNumber);
        }
    }
}
