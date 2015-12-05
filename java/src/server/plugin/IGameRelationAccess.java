package server.plugin;

import java.util.List;

/**
 * Specifies the actions that specifies the actions that plugins should (but don't necessarily have to) implement for relations between the Catan Game and players.
 */
public interface IGameRelationAccess
{
    void addUserToGame(int userID, int gameID) throws Exception;

    List<Integer> listPlayersInGame(int gameID) throws Exception;
}
