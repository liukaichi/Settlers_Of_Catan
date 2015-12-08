import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import server.ServerModel;
import server.plugin.IGameAccess;

import java.io.ObjectInputStream;
import java.rmi.ServerException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SQL Database Access Object for Games.
 */
public class GameAccess implements IGameAccess
{
    private SQLiteEngine engine;
    private final static Logger LOGGER = Logger.getLogger(GameAccess.class.getName());

    private GameAccess()
    {

    }

    public GameAccess(SQLiteEngine engine)
    {
        this();
        this.engine = engine;
    }

    @Override public void updateModel(int gameID, ServerModel game) throws Exception
    {
        PreparedStatement stmt = null;
        try
        {
            String query = "UPDATE Game SET Model = ? WHERE GameID = ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt = engine.addBlobToStatement(stmt, 1, game);
            stmt.setInt(2, gameID);
            if (stmt.executeUpdate() != 1)
            {
                throw new ServerException(String.format("Could not update ServerModel for gameID(%d)", gameID));
            }
        } catch (Exception e)
        {
            LOGGER.severe(String.format("Failed to update game(%d)", gameID));
            throw e;
        } finally
        {
            SQLiteEngine.safeClose(stmt);
        }

    }

    @Override public int addGame(ServerModel game, String gameName) throws Exception
    {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        Statement keyStmt = null;
        try
        {
            String query = "INSERT INTO Game (Model, Name) VALUES (?,?)";
            stmt = this.engine.getConnection().prepareStatement(query);
            stmt = engine.addBlobToStatement(stmt, 1, game);

            stmt.setString(2, gameName);
            if (stmt.executeUpdate() == 1)
            {
                keyStmt = engine.getConnection().createStatement();
                keyRS = keyStmt.executeQuery("SELECT MAX(GameID) FROM Game");
                keyRS.next();
                return keyRS.getInt(1);
            } else
            {
                throw new ServerException("Query wasn't executed properly to add a game");
            }
        } catch (Exception e)
        {
            LOGGER.severe("Failed to add game");
            throw e;
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(keyRS);
            SQLiteEngine.safeClose(keyStmt);
        }

    }

    @Override public ServerModel getGame(int gameID) throws Exception
    {
        ServerModel result = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            String query = "SELECT Model FROM Game WHERE GameID = ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setInt(1, gameID);

            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst())
            {
                return null;
            }
            while (rs.next())
            {
                byte modelBytes[] = rs.getBytes(1);
                ObjectInputStream stream = new ObjectInputStream(new ByteInputStream(modelBytes, modelBytes.length));
                Object o;
                while ((o = stream.readObject()) != null)
                {

                    result = (ServerModel) o;
                }
            }
        } catch (Exception e)
        {
            LOGGER.severe(String.format("Failed to getGame(%d)", gameID));
            throw e;
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(rs);
        }

        return result;
    }

    @Override public List<ServerModel> getAllGames() throws Exception
    {
        List<ServerModel> result = new ArrayList<>();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try
            {
                String query = "SELECT Model FROM Game";
                stmt = engine.getConnection().prepareStatement(query);

                rs = stmt.executeQuery();
            if (!rs.isBeforeFirst())
            {
                return result;
            }
            while (rs.next())
            {
                byte modelBytes[] = rs.getBytes(1);
                ObjectInputStream stream = new ObjectInputStream(new ByteInputStream(modelBytes, modelBytes.length));
                Object o;
                while ((o = stream.readObject()) != null)
                {
                    ServerModel model = (ServerModel) o;
                    result.add(model);
                }
            }
        } catch (Exception e)
        {
            LOGGER.severe("Failed to get all games");
            throw e;
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(rs);
        }

        return result;
    }

    @Override public void initialize()
    {
        LOGGER.entering(getClass().getName(), "initialize");
        Statement stat = null;
        try
        {
            // @formatter:off
            stat = engine.getConnection().createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS Game;");
            stat.executeUpdate("CREATE TABLE Game ("
                    + "GameID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , "
                    + "Model BLOB NOT NULL , "
                    + "Name VARCHAR "
                    + ")");
            // @formatter:on
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to initialize Game table", e);
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }

    @Override public int getNextGameID() throws Exception
    {
        ResultSet keyRS = null;
        Statement keyStmt = null;
        try
        {
            int nextID = 0;
            keyStmt = engine.getConnection().createStatement();
            keyRS = keyStmt.executeQuery("SELECT IFNULL(seq, -1) FROM sqlite_sequence where name = \"Game\"");
            if (keyRS.next())
            {
                int lastID = keyRS.getInt(1);
                nextID = lastID + 1;
            }
            return nextID;
        } catch (Exception e)
        {
                throw e;
        } finally
        {
            SQLiteEngine.safeClose(keyRS);
            SQLiteEngine.safeClose(keyStmt);
        }
    }
}
