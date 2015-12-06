import database.Database;
import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class CommandAccess implements ICommandAccess, IAccess
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
        }
        throw new Exception("no such gameID: "+gameID);
    }

    @Override public int getNumberOfCommandsInGame(int gameID) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands.size();
        }
        throw new Exception("no such gameID: "+gameID);
    }

    @Override public List<MoveCommand> getAllCommands(int gameID) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands;
        }
        throw new Exception("no such gameID: "+gameID);
    }

    @Override public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands.stream().skip(sequenceNumber).collect(Collectors.toList());
        }
        throw new Exception("no such gameID: "+gameID);
    }

    @Override public MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception
    {
        List<MoveCommand> commands = Database.getInstance().getGameCommands(gameID);
        if(commands != null)
        {
            return commands.get(sequenceNumber);
        }
        throw new Exception("no such gameID: "+gameID+" or sequenceNumber: "+sequenceNumber);
    }
}
