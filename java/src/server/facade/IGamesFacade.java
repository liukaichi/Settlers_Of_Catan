package server.facade;

import client.data.PlayerInfo;
import shared.communication.CreateGameResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.CatanColor;

/**
 * An interface detailing all of the actions that can take place on the Server Side for all Games.
 */
public interface IGamesFacade
{
    /**
     * Returns a list of Games on the server.
     * @return a list of games on the server.
     */
    ListGamesResponse listGames();

    /**
     * Places a player in the given game, if it exists.
     * @param player the player joining the game.
     * @param gameID the id of the game to join.
     * @param color the color that the player is joining with.
     */
    String joinGame(PlayerInfo player, int gameID, CatanColor color);

    /**
     * Creates a game with the given properties.
     * @param randomTiles whether or not the created game will have random hexes.
     * @param randomNumbers whether or not the hexes in the created game will have randomly placed numbers.
     * @param randomPorts whether or not the created game's ports will be randomly picked.
     * @param name the name of the game.
     * @return a CreateGameResponse with the created game's info.
     */
    CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);
}
