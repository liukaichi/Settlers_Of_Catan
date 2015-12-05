package server.plugin;

import shared.communication.moveCommands.MoveCommand;

import java.util.List;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface ICommandAccess
{
    void saveCommand(int gameID, MoveCommand command);

    int getNumberOfCommandsInGame(int gameID);

    List<MoveCommand> getAllCommands(int gameID);

    List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber);

    MoveCommand getCommand(int gameID, int sequenceNumber);
}
