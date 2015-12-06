import server.manager.User;
import server.plugin.IUserAccess;
import shared.communication.Credentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "SELECT UserID FROM User WHERE Name = ? AND Password = ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setString(1, credentials.getUsername());
            stmt.setString(2, credentials.getPassword().getPasswordPlainText());

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
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(rs);
        }

        return result;
    }

    public User getUser(int id) throws Exception
    {
        User result = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "SELECT * FROM User WHERE UserID = ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setInt(1, id);

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
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(rs);
        }

        return result;
    }

    public int registerUser(Credentials credentials) throws Exception
    {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        try
        {
            String query = "INSERT INTO User (Name, Password) VALUES (?,?)";
            stmt = engine.getConnection().prepareStatement(query);

            stmt.setString(1, credentials.getUsername());
            stmt.setString(2, credentials.getPassword().toString());
            if (stmt.executeUpdate() == 1)
            {
                Statement keyStmt = engine.getConnection().createStatement();
                keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
                return id;
            } else
            {
                return -1;
            }
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(keyRS);
        }

    }

    @Override public void initialize()
    {
        LOGGER.entering(getClass().getName(), "initialize");
        Statement stat = null;
        try
        {
            // @formatter:off
            stat = engine.getConnection().createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS User;");
            stat.executeUpdate("CREATE TABLE User ("
                    + "UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "Username TEXT NOT NULL  UNIQUE, "
                    + "Password TEXT NOT NULL, "
                    + ");");
            // @formatter:on
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }
}
