import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class UserAccess implements IUserAccess, IAccess
{
    @Override public void initialize()
    {

    }

    @Override public User getUser(Credentials credentials) throws Exception
    {
        return null;
    }

    @Override public User getUser(int id) throws Exception
    {
        return null;
    }

    @Override public int registerUser(Credentials credentials) throws Exception
    {
        return 0;
    }
}
