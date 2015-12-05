package server.plugin;

import server.manager.User;
import shared.communication.Credentials;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface IUserAccess
{
    User getUser(Credentials credentials) throws Exception;

    User getUser(int id) throws Exception;

    int registerUser(Credentials credentials) throws Exception;
}
