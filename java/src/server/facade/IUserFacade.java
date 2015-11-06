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
     * @param credentials
     * @throws SignInException
     */
    void signInUser(Credentials credentials) throws SignInException;

    /**
     * Registers a new player with the given credentials
     * @param credentials
     * @throws SignInException
     */
    void registerUser(Credentials credentials) throws SignInException;
}
