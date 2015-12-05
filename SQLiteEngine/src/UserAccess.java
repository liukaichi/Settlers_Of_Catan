import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * SQL Database Access Object for Users.
 */
public class UserAccess implements IUserAccess
{
    private SQLiteEngine engine;
    public UserAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    public User getUser(Credentials credentials) throws Exception
    {
        User result = null;
        PreparedStatement stmt;
        ResultSet rs;
        String query = "SELECT UserID FROM User WHERE Name = "
                + credentials.getUsername()
                + " AND UserID = "
                + credentials.getPassword();
        stmt = engine.getConnection().prepareStatement(query);

        rs = stmt.executeQuery();
        if (!rs.isBeforeFirst()){
            return null;
        }
        if (rs.next()) {
            int id = rs.getInt(1);

            result = new User(credentials, id);
        }

        return result;
    }

    public User getUser(int id) throws Exception
    {
        return null;
    }

    public int registerUser(Credentials credentials) throws Exception
    {
        return -1;
    }
}
