package server.facade;

import shared.communication.Credentials;
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
     */
    void signInUser(Credentials credentials) throws SignInException;

    /**
     * Registers a new player with the given credentials
     * @param credentials the credentials that the person is using to register.
     * @throws SignInException if registration fails.
     */
    void registerUser(Credentials credentials) throws SignInException;
}
