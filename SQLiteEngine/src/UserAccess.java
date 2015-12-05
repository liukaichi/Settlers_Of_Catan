import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

/**
 * SQL Database Access Object for Users.
 */
public class UserAccess implements IUserAccess, IAccess
{
    private SQLiteEngine engine;
    public UserAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    public User getUser(Credentials credentials) throws Exception
    {
        return null;
    }

    public User getUser(int id) throws Exception
    {
        return null;
    }

    public int registerUser(Credentials credentials) throws Exception
    {
        return -1;
    }

    @Override public void initializeTable()
    {

    }
}
