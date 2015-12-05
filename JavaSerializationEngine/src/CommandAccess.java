import server.plugin.ICommandAccess;
import shared.communication.moveCommands.MoveCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class CommandAccess implements ICommandAccess, IAccess
{
    Map<Integer, List<MoveCommand>> commands;

    @Override public void initialize()
    {
        commands = new HashMap<>();
    }

    @Override public void saveCommand(int gameID, MoveCommand command) throws Exception
    {
        List<MoveCommand> moveCommands = commands.get(gameID);
        if(moveCommands != null)
        {
            moveCommands.add(command);
        }
        else
        {
            commands.put(gameID, new ArrayList<MoveCommand>(){{add(command);}});
        }
    }

    @Override public int getNumberOfCommandsInGame(int gameID) throws Exception
    {
        List<MoveCommand> moveCommands = commands.get(gameID);
        if(moveCommands != null)
        {
            return moveCommands.size();
        }
        throw new Exception("no such game command for gameID: "+gameID);
    }

    @Override public List<MoveCommand> getAllCommands(int gameID) throws Exception
    {
        List<MoveCommand> moveCommands = commands.get(gameID);
        if(moveCommands != null)
        {
            return moveCommands;
        }
        throw new Exception("no commands for gameID: "+gameID);
    }

    @Override public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception
    {
        List<MoveCommand> moveCommands = commands.get(gameID);
        if(moveCommands != null)
        {
            return moveCommands.stream().skip(sequenceNumber).collect(Collectors.toList());
        }
        throw new Exception("no such game command for gameID: "+gameID);
    }

    @Override public MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception
    {
        List<MoveCommand> moveCommands = commands.get(gameID);
        if(moveCommands != null)
        {
            return moveCommands.get(sequenceNumber);
        }
        throw new Exception("no such game command for gameID: "+gameID);
    }
}
