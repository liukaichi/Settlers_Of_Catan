package server.plugin;

import shared.communication.moveCommands.MoveCommand;

import java.util.List;

/**
 * Specifies the actions that can be taken by plugins accessing commands.
 */
public interface ICommandAccess extends IAccess
{
    /**
     * Saves a command to the given game.
     *
     * @param gameID  the id of the game.
     * @param command the command to save.
     * @throws Exception if anything goes wrong.
     */
    void saveCommand(int gameID, MoveCommand command) throws Exception;

    /**
     * Gets the number of commands in a given game. This is analogous to the version number stored in a Server Model.
     * @param gameID the id of the game.
     * @return the number of commands in a given game.
     * @throws Exception if anything goes wrong.
     */
    int getNumberOfCommandsInGame(int gameID) throws Exception;

    /**
     * Gets all of the commands for a given game.
     * @param gameID the id of the game.
     * @return all of the commands for a given game.
     * @throws Exception if anything goes wrong.
     */
    List<MoveCommand> getAllCommands(int gameID) throws Exception;

    /**
     * Gets all of the commands for a given game after a given number.
     * @param gameID the id of the game.
     * @param sequenceNumber the sequence number after which to grab the commands.
     * @return all of the commands for a given game after a given number.
     * @throws Exception if anything goes wrong.
     */
    List<MoveCommand> getAllCommandsAfter(int gameID, int sequenceNumber) throws Exception;

    /**
     * Gets the command from the given game and sequence number.
     * @param gameID the id of the game.
     * @param sequenceNumber the sequence of the command.
     * @return the command from the given game and sequence number.
     * @throws Exception if anything goes wrong.
     */
    MoveCommand getCommand(int gameID, int sequenceNumber) throws Exception;
}
