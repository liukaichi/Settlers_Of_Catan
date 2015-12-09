import database.GameRegistry;
import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

import java.util.Map;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class UserAccess implements IUserAccess
{

    /**
     * Load serialized users into memory
     */
    @Override public void initialize()
    {
        try
        {
            GameRegistry.deserialize();
        }
        catch(Exception e)
        {
            //it's ok if the file doesn't exist here.
        }
    }

    @Override public User getUser(Credentials credentials) throws Exception
    {
        GameRegistry gameRegistry = GameRegistry.getInstance();
        return gameRegistry.getUser(credentials);
    }

    @Override public User getUser(int id) throws Exception
    {
        GameRegistry gameRegistry = GameRegistry.getInstance();
        return gameRegistry.getUser(id);
    }

    @Override public int registerUser(Credentials credentials) throws Exception
    {
        GameRegistry gameRegistry = GameRegistry.getInstance();
        int userID = gameRegistry.registerUser(credentials);
        gameRegistry.serialize();
        return userID;
    }

    public Map<Integer, Credentials> getAllUsers()
    {
        GameRegistry gameRegistry = GameRegistry.getInstance();
        return gameRegistry.getAllUsers();
    }
}
