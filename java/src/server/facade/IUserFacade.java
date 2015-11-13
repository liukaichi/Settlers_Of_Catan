package server.facade;

import server.manager.User;
import shared.communication.Credentials;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.InvalidCredentialsException;
import shared.definitions.exceptions.SignInException;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IUserFacade
{
    /**
     * Signs in the player with the given credentials
     * @param credentials the credentials that the person is using to sign in.
     * @throws SignInException if the login fails
     * @return the user that results from signing in. This is used to create the client's catan.user cookie.
     */
    User signInUser(Credentials credentials) throws SignInException, InvalidCredentialsException;

    /**
     * Registers a new player with the given credentials
     * @param credentials the credentials that the person is using to register.
     * @throws SignInException if registration fails.
     * @return the user that results from signing in. This is used to create the client's catan.user cookie.
     */
    User registerUser(Credentials credentials) throws SignInException, InvalidCredentialsException, ExistingRegistrationException;
}
