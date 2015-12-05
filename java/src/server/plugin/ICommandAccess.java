package server.plugin;

import shared.communication.moveCommands.MoveCommand;

import java.util.List;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface ICommandAccess
{
    void saveCommand(int gameID, MoveCommand command) throws Exception;

    int getNumberOfCommandsInGame(int gameID) throws Exception;

    List<MoveCommand> getAllCommands(int gameID) throws Exception;

    List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception;

    MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception;
}
