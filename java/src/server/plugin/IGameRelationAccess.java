package server.plugin;

import java.util.List;

/**
 * Specifies the actions that specifies the actions that plugins should (but don't necessarily have to) implement for relations between the Catan Game and players.
 */
public interface IGameRelationAccess extends IAccess
{
    /**
     * Adds a user to the given game.
     *
     * @param userID the id of the user.
     * @param gameID the id of the game.
     * @throws Exception if anything goes wrong.
     */
    void addUserToGame(int userID, int gameID) throws Exception;

    /**
     * Lists the players in a game.
     *
     * @param gameID the id of the game.
     * @return the players in a game.
     * @throws Exception if anything goes wrong.
     */
    List<Integer> listPlayersInGame(int gameID) throws Exception;
}
