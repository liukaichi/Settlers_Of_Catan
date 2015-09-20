package server.proxy;

import java.util.List;

import client.data.GameInfo;
import server.communication.*;
import shared.model.ClientModel;

public interface IProxyGameCommands
{
    // Game Methods
    /**
     * Returns information about all of the current games on the server. <br>
     * 
     * @post If the operation succeeds,
     *       <ol>
     *       <li>The server returns an HTTP 200 success response.
     *       <li>The body contains a JSON array containing a list of objects
     *       that contain information about the server’s games.
     *       </ol>
     *       If the operation fails,
     *       <ul>
     *       <li>The server returns an HTTP 400 error response, and the body
     *       contains an error message.
     *       </ul>
     * @return Information about all of the current games on the server.
     */
    List<GameInfo> listGames();

    /**
     * Creates a new game.
     * 
     * @pre Inside of the createGameRequest:
     *      <ul>
     *      <li>Name is not null.
     *      <li>randomTiles, randomNumbers, and randomPorts contain valid
     *      boolean values.
     *      </ul>
     * @post If the operation succeeds,
     *       <ol>
     *       <li>A new game with the specified properties has been created.
     *       <li>The server returns an HTTP 200 success response.
     *       <li>The body contains a JSON object describing the newly created
     *       game.
     *       </ol>
     *       If the operation fails,
     *       <ul>
     *       <li>The server returns an HTTP 400 error response, and the body
     *       contains an error message.
     *       </ul>
     * @param createGameRequest
     *        The name and settings to use for the new game. Can be sent as form
     *        encoded key-value pairs as well. The new game's ID can be read
     *        from the response.
     * @return The game info with an empty list of players
     */
    CreateGameResponse createGame(CreateGameRequest createGameRequest);

    /**
     * 
     * @param joinGameRequest
     *        The ID of the game the player wants to join, and the color they
     *        want.
     */
    void joinGame(JoinGameRequest joinGameRequest);

    /**
     * 
     * @param saveGameRequest
     *        The id of the game to save and the save file name (no extensions
     *        please).
     */
    void saveGame(SaveGameRequest saveGameRequest);

    /**
     * Only for debugging purposes. Used for saving the game when bugs occur so
     * you can load directly back to where the bug happens. <br>
     * On success:
     * <ul>
     * <li>The server returns an HTTP 200 success response with “Success” in the
     * body.</li>
     * <li>The game in the specified file has been loaded into the server and
     * its state restored(including its ID).</li>
     * </ul>
     * On failure:
     * <ul>
     * <li>The server returns an HTTP 400 error response, and the body contains
     * an error message</li>
     * </ul>
     * 
     * @param loadGameRequest
     *        The game file to load that is saved on the server.
     */
    void loadGame(LoadGameRequest loadGameRequest);

    // Game operations for games you are already in (requires cookie)
    /**
     * Converts the received JSON client model if the given version number
     * doesn't match the server's current version.
     *
     * @param versionNumber
     *        The version number of the client.
     * @return returns the client model of the server's game state. If it
     *         already matches, it returns null.
     */
    ClientModel getGameState(int versionNumber);

    /**
     * Converts the received JSON client model of a restarted game into a Java
     * client. For the default games created by the server, this method reverts
     * the game to the state immediately after the initial placement round. For
     * user­created games, this method reverts the game to the very beginning
     * (i.e., before the initial placement round).
     *
     * @return Returns the client model of the reset game.
     */
    ClientModel resetGame();

    /**
     * 
     * @return List of Commands used in the game so far.
     */
    List<Command> getCommands();

    /**
     * 
     * @param commands
     *        The list of commands to be executed.
     * @return updated Game model after the command is executed.
     */
    ClientModel postCommands(List<Command> commands);

    List<AIType> listAI();

    /**
     * Adds an AI player to the current game.<br>
     * 
     * @pre
     *      <ol>
     *      <li>The caller has previously logged in to the server and joined a
     *      game (i.e., they have valid catan.user and catan.game HTTP cookies).
     *      <li>There is space in the game for another player (i.e., the game is
     *      not “full”).
     *      <li>The specified “AIType” is valid (i.e., one of the values
     *      returned by the /game/listAI method).
     *      </ol>
     * @post On success:
     *       <ol>
     *       <li>The server returns an HTTP 200 success response with “Success”
     *       in the body.
     *       <li>A new AI player of the specified type has been added to the
     *       current game. The server selected a name and color for the player.
     *       </ol>
     *       On failure:
     *       <ul>
     *       <li>The server returns an HTTP 400 error response, and the body
     *       contains an error message.
     *       </ul>
     * 
     * @param aiType
     *        The type of AI player to add (currently, LARGEST_ARMY is the only
     *        supported type)
     */
    void addAI(AIType aiType);
}
