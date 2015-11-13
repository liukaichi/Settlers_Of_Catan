package client.proxy;

import client.data.PlayerInfo;
import shared.communication.Credentials;
import shared.definitions.exceptions.InvalidCredentialsException;

/**
 * All operations that have to do with users as well as server utility methods
 * are contained in this interface.<br>
 * <br>
 * This interface is used by {@link IProxy}.
 * 
 * @author Cache Staheli
 *
 */
public interface IProxyNonMoveAPI extends IProxyGameCommands
{
    // User Methods
    /**
     * Logs the caller in to the server, and sets their catan.user HTTP cookie.
     * <br>
     * The passed"in username and password may correspond to the credentials of
     * any registered user. The server starts with four users: Sam, Brooke, Pete
     * and Mark. Their passwords are sam, brooke, pete and mark respectively.
     * Any additional registered users should also work with this call.
     * 
     * 
     * @param credentials
     *        The player's username and password.
     * @pre username and password in userParameter are not null.
     * @post If the passed"in (username, password) pair is valid,
     *       <ul>
     *       <li>1. The server returns an HTTP 200 success response with
     *       "Success" in the body.
     *       <li>2. The HTTP response headers set the catan.user cookie to
     *       contain the identity of the logged"in player. The cookie uses
     *       "Path=/", and its value contains a url"encoded JSON object of the
     *       following form: { "name": STRING, "password": STRING, "playerID":
     *       INTEGER }. For example, { "name": "Rick", "password": "secret",
     *       "playerID": 14 }.
     *       </ul>
     *       If the passed"in (username, password) pair is not valid, or the
     *       operation fails for any other reason, The server returns an HTTP
     *       400 error response, and the body contains an error message.
     * @throws InvalidCredentialsException
     *         if the login fails.
     * @see <a href=
     *      "https://students.cs.byu.edu/~cs340ta/fall2015/group_project/Cookies.pdf">
     *      How the Catan Server Uses HTTP Cookies</a>
     *
     * @return the info the player set in the cookie.
     */
    // Or just Username/Password. We should discuss this, probably.
    PlayerInfo userLogin(Credentials credentials) throws InvalidCredentialsException;

    /**
     * Logs into the server and sets the user's HTTP cookie. <br>
     * <br>
     * This method does two things:
     * <ul>
     * <li>1. Creates a new user account.
     * <li>2. Logs the caller in to the server as the new user, and sets their
     * catan.user HTTP cookie.
     * </ul>
     * 
     * @param credentials
     *        The player's username and password.
     * @pre username and password in userParameter are not null. The username is
     *      not already in use.
     * @post If there is no existing user with the specified username,
     *       <ul>
     *       <li>1. A new user account has been created with the specified
     *       username and password.
     *       <li>2. The server returns an HTTP 200 success response with
     *       "Success" in the body.
     *       <li>3. The HTTP response headers set the <b>catan.user</b> cookie
     *       to contain the identity of the logged"in player. The cookie uses
     *       "Path=/", and its value contains a url"encoded JSON object of the
     *       following form: { "name": STRING, "password": STRING, "playerID":
     *       INTEGER }. For example, { "name": "Rick", "password": "secret",
     *       "playerID": 14 }.
     *       </ul>
     *       If there is already an existing user with the specified name, or
     *       the operation fails for any other reason, the server returns an
     *       HTTP 400 error response, and the body contains an error message.
     * @throws InvalidCredentialsException
     *         if the login/registration fails.
     * @see <a href=
     *      "https://students.cs.byu.edu/~cs340ta/fall2015/group_project/Cookies.pdf">
     *      How the Catan Server Uses HTTP Cookies</a>
     * @return the info the player set in the cookie.
     */
    PlayerInfo userRegister(Credentials credentials) throws InvalidCredentialsException;
}
