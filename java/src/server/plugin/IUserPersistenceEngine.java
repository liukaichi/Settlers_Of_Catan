package server.plugin;

import server.manager.User;
import shared.communication.Credentials;

/**
 * An interface to specify the pattern that plugins must follow for registering and retrieving users.
 */
public interface IUserPersistenceEngine
{
    /**
     * Registers a user with the given credentials. Returns the unique ID that has been assigned to this player.
     *
     * @param credentials the credentials to create the user from.
     * @return the id that is now associated with this user.
     */
    int registerUser(Credentials credentials);

    /**
     * Gets a user based on their id.
     *
     * @param id the id of the user.
     * @return the user who matches the id.
     */
    User getUser(int id);

    /**
     * Gets a user based on their credentials.
     *
     * @param credentials the credentials of the user.
     * @return the user who matches the credentials.
     */
    User getUser(Credentials credentials);
}
