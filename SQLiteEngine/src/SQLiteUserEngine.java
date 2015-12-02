import server.manager.User;
import server.plugin.IUserPersistenceEngine;
import shared.communication.Credentials;

/**
 * Created by liukaichi on 12/2/2015.
 */
public class SQLiteUserEngine implements IUserPersistenceEngine
{
    @Override public int registerUser(Credentials credentials)
    {
        return 34567;
    }

    @Override public User getUser(int id)
    {
        return null;
    }

    @Override public User getUser(Credentials credentials)
    {
        return null;
    }
}
