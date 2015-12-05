package server.plugin;

import server.ServerModel;
import server.manager.User;
import shared.communication.CatanCommand;
import shared.communication.Credentials;

/**
 * Persistence Engine to save Settlers Of Catan game.
 */
public abstract class IPersistenceEngine
{
    //*********************GAME PERSISTENCE***********************//

    /** Saves the game after commands have been made
     *
     * @param gameID gameID to save to
     * @param catanCommand the command to be saved
     * @param game the model of the game after the command is executed
     * @return if transaction was successful
     */
    public abstract boolean saveGame(int gameID, CatanCommand catanCommand, ServerModel game);

    /** Loads a game from the database given the gameID
     *
     * @param gameID ID of the game to be loaded
     * @return ServerModel of the game specified
     */
    public abstract ServerModel loadGame(int gameID);

    /** Adds a player to the many-to-many table
     *
     * @param playerID the playerID
     * @param gameID the gameID
     */
    public abstract void addPlayerToGame(int playerID, int gameID);


//*********************USER PERSISTENCE***********************//
    /**
     * Registers a user with the given credentials. Returns the unique ID that has been assigned to this player.
     *
     * @param credentials the credentials to create the user from.
     * @return the id that is now associated with this user.
     */
    public abstract int registerUser(Credentials credentials);

    /**
     * Gets a user based on their id.
     *
     * @param id the id of the user.
     * @return the user who matches the id.
     */
    public abstract User getUser(int id);

    /**
     * Gets a user based on their credentials.
     *
     * @param credentials the credentials of the user.
     * @return the user who matches the credentials.
     */
    public abstract User getUser(Credentials credentials);

    public abstract boolean startTransaction();

    public abstract boolean endTransaction(boolean commit);

    public abstract boolean addUser(User user);
    public abstract boolean addGame(ServerModel model, String name);


}