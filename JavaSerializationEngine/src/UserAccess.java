import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class UserAccess implements IUserAccess
{
    Map<Integer, List<User>> users;

    @Override public void initialize()
    {
        users = new HashMap<>();
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
