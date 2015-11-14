package server.facade;

import server.manager.User;
import shared.communication.Credentials;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.InvalidCredentialsException;

/**
 * The options available to the server before a person has logged in.
 */
public interface IUserFacade
{
    /**
     * Signs in the player with the given credentials
     *
     * @param credentials the credentials that the person is using to sign in.
     * @return the user that results from signing in. This is used to create the client's catan.user cookie.
     * @throws InvalidCredentialsException if the credentials do not match anyone in the database.
     */
    User signInUser(Credentials credentials) throws InvalidCredentialsException;

    /**
     * Registers a new player with the given credentials
     *
     * @param credentials the credentials that the person is using to register.
     * @return the user that results from signing in. This is used to create the client's catan.user cookie.
     * @throws InvalidCredentialsException   if the credentials are invalid.
     * @throws ExistingRegistrationException if the credentials supplied already belong to another person.
     */
    User registerUser(Credentials credentials) throws InvalidCredentialsException, ExistingRegistrationException;
}
