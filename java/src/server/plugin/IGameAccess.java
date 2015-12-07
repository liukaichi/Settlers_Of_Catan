package server.plugin;

import server.ServerModel;

import java.util.List;

/**
 * Specifies the actions that can be taken by plugins accessing Games.
 */
public interface IGameAccess extends IAccess
{
    /**
     * Replaces the currently stored game with a new one.
     *
     * @param gameID the id of the game.
     * @param game   the game to store.
     * @throws Exception if anything goes wrong.
     */
    void updateModel(int gameID, ServerModel game) throws Exception;

    /**
     * Adds a game.
     * @param game the game to add.
     * @param gameName the name of the game.
     * @return the id of the game created.
     * @throws Exception  if anything goes wrong.
     */
    int addGame(ServerModel game, String gameName) throws Exception;

    /**
     * Gets the game associated with the given id.
     * @param gameID the id of the game.
     * @return the game associated with the given id.
     * @throws Exception if anything goes wrong.
     */
    ServerModel getGame(int gameID) throws Exception;

    /**
     * Gets all games.
     * @return all games.
     * @throws Exception if anything goes wrong.
     */
    List<ServerModel> getAllGames() throws Exception;

    /**
     * Gets the next gameID available.
     *
     * @return the next gameID available.
     * @throws Exception if anything goes wrong.
     */
    int getNextGameID() throws Exception;
}
