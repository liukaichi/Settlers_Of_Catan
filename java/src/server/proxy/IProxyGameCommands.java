package server.proxy;

import java.util.List;

import client.data.GameInfo;
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
     * Adds an AI player to the current game.Currently, LARGEST_ARMY is the only
     * supported type.<br>
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
     *        Values returned by listAI, currently only LARGEST_ARMY is
     *        supported
     */
    void addAI(AIType aiType);

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
     * @param gameName
     *        the file name of the game that is saved on the server
     */
    void loadGame(String gameName);

    /**
     * Converts the received JSON client model of the updated game into an Java
     * client model.
     *
     * @return returns the client model of the server's game state.
     */
    ClientModel getGameState();

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
}
