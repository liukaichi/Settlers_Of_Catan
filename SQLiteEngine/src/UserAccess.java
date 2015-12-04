import server.manager.User;
import shared.communication.Credentials;

/**
 * SQL Database Access Object for Users.
 */
public class UserAccess
{
    private SQLiteEngine engine;
    public UserAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    public User getUser(Credentials credentials)
    {
        return null;
    }

    public User getUser(int id)
    {
        return null;
    }

    public int registerUser(Credentials credentials)
    {
        return -1;
    }
}
