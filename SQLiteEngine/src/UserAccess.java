import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * SQL Database Access Object for Users.
 */
public class UserAccess implements IUserAccess, IAccess
{
    private SQLiteEngine engine;
    private final static Logger LOGGER = Logger.getLogger(UserAccess.class.getName());
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
        LOGGER.entering(getClass().getName(), "initializeTable");
        Statement stat = null;
        try
        {
            stat = engine.getConnection().createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS User;");
            stat.executeUpdate("CREATE TABLE User (" + "UserID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," + "Username TEXT NOT NULL  UNIQUE , "
                    + "Password TEXT NOT NULL ," + ");");

        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }
}
