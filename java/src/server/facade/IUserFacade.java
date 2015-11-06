package server.facade;

import shared.communication.Credentials;
import shared.definitions.exceptions.SignInException;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IUserFacade
{
    void signInUser(Credentials credentials) throws SignInException;

    void registerUser(Credentials credentials) throws SignInException;
}
