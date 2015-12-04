package server.plugin;

import shared.communication.moveCommands.MoveCommand;

import java.util.List;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface ICommandAccess
{
    public void saveCommand(int gameID, MoveCommand command);
    public int getNumberOfCommandsInGame(int gameID);

    public List<MoveCommand> getAllCommands(int gameID);

    public List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber);

    public MoveCommand getCommand(int gameID, int sequenceNumber);
}
