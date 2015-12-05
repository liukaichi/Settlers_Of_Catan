package server.plugin;

import server.manager.User;
import shared.communication.Credentials;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface IUserAccess
{
    User getUser(Credentials credentials);

    User getUser(int id);

    int registerUser(Credentials credentials);
}
