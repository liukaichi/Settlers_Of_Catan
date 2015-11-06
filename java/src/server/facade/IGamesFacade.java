package server.facade;

import client.data.GameInfo;
import shared.communication.CreateGameResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;

import java.util.List;

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
    CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
}
