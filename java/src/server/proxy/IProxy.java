package server.proxy;

import java.util.List;
import java.util.logging.Level;

import client.data.GameInfo;
import shared.model.ClientModel;
import shared.model.player.UserParam;

public interface IProxy
{
    // User Methods
    /**
     * Logs the caller in to the server, and sets their catan.user HTTP cookie.
     * <p>
     * </p>
     * The passed�in username and password may correspond to the credentials of
     * any registered user. The server starts with four users: Sam, Brooke, Pete
     * and Mark. Their passwords are sam, brooke, pete and mark respectively.
     * Any additional registered users should also work with this call.
     * 
     * 
     * @param userParameter
     *        the username and password of the individual.
     * @custom.pre username and password in userParameter are not null.
     * @custom.post If the passed�in (username, password) pair is valid,
     *              <ul>
     *              <li>1. The server returns an HTTP 200 success response with
     *              �Success� in the body.
     *              <li>2. The HTTP response headers set the catan.user cookie
     *              to contain the identity of the logged�in player. The cookie
     *              uses �Path=/�, and its value contains a url�encoded JSON
     *              object of the following form: { �name�: STRING, �password�:
     *              STRING, �playerID�: INTEGER }. For example, { �name�:
     *              �Rick�, �password�: �secret�, �playerID�: 14 }.
     *              </ul>
     *              If the passed�in (username, password) pair is not valid, or
     *              the operation fails for any other reason, The server returns
     *              an HTTP 400 error response, and the body contains an error
     *              message.
     * @see https://students.cs.byu.edu/~cs340ta/fall2015/group_project/Cookies.
     *      pdf
     * 
     */
    // Or just Username/Password. We should discuss this, probably.
    void userLogin(UserParam userParameter);

    /**
     * Logs into the server and sets the user's HTTP cookie.
     * <p>
     * </p>
     * This method does two things:
     * <ul>
     * <li>1. Creates a new user account.
     * <li>2. Logs the caller in to the server as the new user, and sets their
     * catan.user HTTP cookie.
     * </ul>
     * 
     * @param userParameter
     *        the username and password of the individual.
     * @pre username and password in userParameter are not null. The username is
     *      not already in use.
     * @post If there is no existing user with the specified username,
     *       <ul>
     *       <li>1. A new user account has been created with the specified
     *       username and password.
     *       <li>2. The server returns an HTTP 200 success response with
     *       �Success� in the body.
     *       <li>3. The HTTP response headers set the <b>catan.user</b> cookie
     *       to contain the identity of the logged�in player. The cookie uses
     *       �Path=/�, and its value contains a url�encoded JSON object of the
     *       following form: { �name�: STRING, �password�: STRING, �playerID�:
     *       INTEGER }. For example, { �name�: �Rick�, �password�: �secret�,
     *       �playerID�: 14 }.
     *       </ul>
     *       If there is already an existing user with the specified name, or
     *       the operation fails for any other reason, the server returns an
     *       HTTP 400 error response, and the body contains an error message.
     * @see https://students.cs.byu.edu/~cs340ta/fall2015/group_project/Cookies.
     *      pdf
     */
    void userRegister(UserParam userParameter);

    // Game Methods
    /**
     * Returns information about all of the current games on the server.
     * <p>
     * </p>
     * 
     * @post If the operation succeeds,
     *       <ul>
     *       <li>1. The server returns an HTTP 200 success response.
     *       <li>2. The body contains a JSON array containing a list of objects
     *       that contain information about the server�s games.
     *       </ul>
     *       If the operation fails, The server returns an HTTP 400 error
     *       response, and the body contains an error message.
     * @return Information about all of the current games on the server.
     */
    List<GameInfo> listGames();

    /**
     * Adds an AI player to the current game.
     * <p>
     * </p>
     * On success:
     * <ul>
     * <li>1. The server returns an HTTP 200 success response with �Success� in
     * the body.
     * <li>2. A new AI player of the specified type has been added to the
     * current game. The server selected a name and color for the player.
     * </ul>
     * On failure:
     * <ul>
     * <li>1. The server returns an HTTP 400 error response, and the body
     * contains an error message.
     * </ul>
     * 
     * @param aiType
     *        Values returned by listAI, currently only LARGEST_ARMY is
     *        supported
     */
    void addAI(AIType aiType);

    // Util Method
    /**
     * Sets the server�s logging level.
     * <p>
     * </p>
     * On success:
     * <ul>
     * <li>1. The server returns an HTTP 200 success response with �Success� in
     * the body.
     * <li>2. The Server is using the specified logging level
     * </ul>
     * On failure:
     * <ul>
     * <li>1. The server returns an HTTP 400 error response, and the body
     * contains an error message
     * </ul>
     * 
     * @param level
     *        Valid values include: SEVERE, WARNING, INFO, CONFIG, FINE, FINER,
     *        FINEST
     */
    void changeLogLevel(Level level);

    // Move Methods

    /**
     * Only for debugging purposes. Used for saving the game when bugs occur so
     * you can load directly back to where the bug happens.
     * <p>
     * </p>
     * On success:
     * <ul>
     * <li>The server returns an HTTP 200 success response with �Success� in the
     * body.</li>
     * <li>The game in the specified file has been loaded into the server and
     * its state restored(including its ID).</li>
     * </ul>
     * On failure:
     * <ul>
     * <li>1. The server returns an HTTP 400 error response, and the body
     * contains an error message</li>
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
     * user�created games, this method reverts the game to the very beginning
     * (i.e., before the initial placement round).
     *
     * @return Returns the client model of the reset game.
     */
    ClientModel resetGame();

}
