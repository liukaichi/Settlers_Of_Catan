package client.proxy;

import shared.communication.*;
import shared.definitions.AIType;
import shared.definitions.exceptions.*;
import shared.model.ClientModel;

/**
 * This contains all of the queries and actions that can be taken with the game
 * before joining the game.<br>
 * <br>
 * This interface is extended by {@link IProxyNonMoveAPI}.
 * 
 * @author Cache Staheli
 *
 */
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
     *       that contain information about the server's games.
     *       </ol>
     *       If the operation fails,
     *       <ul>
     *       <li>The server returns an HTTP 400 error response, and the body
     *       contains an error message.
     *       </ul>
     * @return Information about all of the current games on the server.
     */
    ListGamesResponse listGames();

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
     * Adds the player to the specified game and sets their catan.game cookie.
     * 
     * @pre
     *      <ol>
     *      <li>The user has previously logged in to the server (i.e., they have
     *      a valid catan.user HTTP cookie).
     *      <li>The player may join the game because:
     *      <ul>
     *      <li>They are already in the game, OR
     *      <li>There is space in the game to add a new player.
     *      </ul>
     *      <li>The specified game ID is valid
     *      <li>The specified color is valid (red, green, blue, yellow, puce,
     *      brown, white, purple, orange)
     *      </ol>
     * @post If the operation succeeds,
     *       <ol>
     *       <li>The server returns an HTTP 200 success response with "Success"
     *       in the body.
     *       <li>The player is in the game with the specified color (i.e. calls
     *       to /games/list method will show the player in the game with the
     *       chosen color).
     *       <li>The server response includes the "Set-cookie" response header
     *       setting the catan.game HTTP cookie
     *       </ol>
     *       If the operation fails,
     *       <ul>
     *       <li>The server returns an HTTP 400 error response, and the body
     *       contains an error message.
     *       </ul>
     * @param joinGameRequest
     *        The ID of the game the player wants to join, and the color they
     *        want.
     * @see <a href=
     *      "https://students.cs.byu.edu/~cs340ta/fall2015/group_project/Cookies.pdf">
     *      How the Catan Server Uses HTTP Cookies</a>
     * @throws GameQueryException
     *         if game is not successfully joined.
     */
    void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException;

    // Game operations for games you are already in (requires cookie)
    /**
     * Returns the current state of the game in JSON format.<br>
     * <br>
     * In addition to the current game state, the returned JSON also includes a
     * "version" number for the client model. The next time /game/model is
     * called, the version number from the previously retrieved model may
     * optionally be included as a query parameter in the request
     * (/game/model?version=N). The server will only return the full JSON game
     * state if its version number is not equal to N. If it is equal to N, the
     * server returns "true" to indicate that the caller already has the latest
     * game state. This is merely an optimization. If the version number is not
     * included in the request URL, the server will return the full game state.
     * <br>
     * <br>
     * The format of the returned JSON can be found on the server"s Swagger
     * page, or in the document titled "Client Model JSON Documentation".
     * 
     * 
     * @pre
     *      <ol>
     *      <li>The caller has previously logged in to the server and joined a
     *      game (i.e., they have valid catan.user and catan.game HTTP cookies).
     *      <li>If specified, the version number is included as the "version"
     *      query parameter in the request URL, and its value is a valid
     *      integer.
     *      </ol>
     * @post If the operation succeeds,
     *       <ol>
     *       <li>The server returns an HTTP 200 success response.
     *       <li>The response body contains JSON data.
     *       <ol>
     *       <li>The full client model JSON is returned if the caller does not
     *       provide a version number, or the provide version number does not
     *       match the version on the server
     *       <li>"true" (true in double quotes) is returned if the caller
     *       provided a version number, and the version number matched the
     *       version number on the server.
     *       </ol>
     *       </ol>
     *       If the operation fails,
     *       <ul>
     *       <li>The server returns an HTTP 400 error response, and the body
     *       contains an error message.
     *       </ul>
     * 
     * @param versionNumber
     *        The version number of the client. Input "-1" if you want to grab
     *        the game state regardless of version.
     * @return returns the client model of the server's game state. If it
     *         already matches, it returns null.
     */
    ClientModel getGameState(int versionNumber);

    /**
     * Returns a list of supported AI player types. <br>
     * <br>
     * Currently, LARGEST_ARMY is the only supported type.
     * 
     * @post If the operation succeeds,
     *       <ol>
     *       <li>The server returns an HTTP 200 success response.
     *       <li>The body contains a JSON string array enumerating the different
     *       types of AI players. These are the values that may be passed to the
     *       /game/addAI method.
     *       </ol>
     * 
     * @return A list of supported AI player types.
     */
    ListAIResponse listAI();

    /**
     * Adds an AI player to the current game.<br>
     * 
     * @pre
     *      <ol>
     *      <li>The caller has previously logged in to the server and joined a
     *      game (i.e., they have valid catan.user and catan.game HTTP cookies).
     *      <li>There is space in the game for another player (i.e., the game is
     *      not "full").
     *      <li>The specified "AIType" is valid (i.e., one of the values
     *      returned by the /game/listAI method).
     *      </ol>
     * @post On success:
     *       <ol>
     *       <li>The server returns an HTTP 200 success response with "Success"
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
     * @throws IllegalArgumentException
     *         if the aiType is invalid
     * @throws GameQueryException
     *         if the aiType cannot be added (if the user has not joined a game,
     *         or if the game is full).
     * @throws AddAIException
     *         if the aiType cannot be added because the game is full.
     */
    void addAI(AIType aiType) throws IllegalArgumentException, GameQueryException, AddAIException;
}
