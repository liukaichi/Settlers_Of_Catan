package shared.facade;

import shared.communication.ListGamesResponse;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IGamesFacade
{
    /**
     * Returns a list of Games on the server.
     * @return a list of games on the server.
     */
    ListGamesResponse listGames();

    /**
     *
     * @param player
     * @param gameID
     * @param color
     */
    void joinGame(PlayerIndex player, int gameID, CatanColor color);

    /**
     *
     * @param randomTiles
     * @param randomNumbers
     * @param randomPorts
     * @param name
     */
    void createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
}
