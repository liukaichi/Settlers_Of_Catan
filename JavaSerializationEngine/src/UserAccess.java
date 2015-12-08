import database.Database;
import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

import java.util.Map;
import java.util.Objects;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class UserAccess implements IUserAccess
{
    @Override public void initialize()
    {

    }

    @Override public User getUser(Credentials credentials) throws Exception
    {
        Map<Integer, Credentials> creds = Database.getInstance().getCredentials();
        User user = new User(credentials, -1);
        for (Map.Entry<Integer, Credentials> entry : creds.entrySet())
        {
            if (Objects.equals(credentials, entry.getValue()))
            {
                user.assignUserID(entry.getKey());
                break;
            }
        }
        return user;
    }

    @Override public User getUser(int id) throws Exception
    {
        Map<Integer, Credentials> credentials = Database.getInstance().getCredentials();
        if (credentials.containsKey(id))
        {
            return new User(credentials.get(id), id);
        }
        return null;
    }

    @Override public int registerUser(Credentials credentials) throws Exception
    {
        Map<Integer, Credentials> creds = Database.getInstance().getCredentials();
        int nextID = creds.size();
        creds.put(nextID, credentials);
        return nextID;
    }
}
