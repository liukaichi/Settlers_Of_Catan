import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

import java.rmi.ServerException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
        User result = null;
        PreparedStatement stmt;
        ResultSet rs;
        String query =
                "SELECT UserID FROM User WHERE Name = " + credentials.getUsername() + " AND UserID = " + credentials
                        .getPassword();
        stmt = engine.getConnection().prepareStatement(query);

        rs = stmt.executeQuery();
        if (!rs.isBeforeFirst())
        {
            return null;
        }
        if (rs.next())
        {
            int id = rs.getInt(1);

            result = new User(credentials, id);
        }

        return result;
    }

    public User getUser(int id) throws Exception
    {
        User result = null;
        PreparedStatement stmt;
        ResultSet rs;
        String query = "SELECT * FROM User WHERE UserID = " + id;
        stmt = engine.getConnection().prepareStatement(query);

        rs = stmt.executeQuery();
        if (!rs.isBeforeFirst())
        {
            return null;
        }
        if (rs.next())
        {
            String name = rs.getString(2);
            String password = rs.getString(3);
            result = new User(name, password, id);
        }

        return result;
    }

    public int registerUser(Credentials credentials) throws Exception
    {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String query = "INSERT into User (Name, Password) VALUES " + "(?,?)";
        stmt = engine.getConnection().prepareStatement(query);

        stmt.setString(1, credentials.getUsername());
        stmt.setString(2, credentials.getPassword().toString());
        if (stmt.executeUpdate() == 1)
        {
            Statement keyStmt = engine.getConnection().createStatement();
            keyRS = keyStmt.executeQuery("select last_insert_rowid()");
            keyRS.next();
            int id = keyRS.getInt(1);
            return id;
        } else
        {
            return -1;
        }

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
