package server.plugin;

import server.manager.User;
import shared.communication.Credentials;

/**
 * Specifies the actions that can be taken by plugins accessing Users.
 */
public interface IUserAccess extends IAccess
{
    /**
     * Gets a user from their credentials.
     *
     * @param credentials the credentials of the user.
     * @return the user associated with the credentials.
     * @throws Exception if anything goes wrong.
     */
    User getUser(Credentials credentials) throws Exception;

    /**
     * Gets a user from their id.
     * @param id the id of the user.
     * @return the user associated with the id.
     * @throws Exception if anything goes wrong.
     */
    User getUser(int id) throws Exception;

    /**
     * Registers a new user with the given credentials.
     * @param credentials the credentials of the new user.
     * @return the newly created user with a new id.
     * @throws Exception
     */
    int registerUser(Credentials credentials) throws Exception;
}
