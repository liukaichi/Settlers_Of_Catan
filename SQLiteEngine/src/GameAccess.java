import server.ServerModel;
import server.plugin.IGameAccess;

import java.rmi.ServerException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * SQL Database Access Object for Games.
 */
public class GameAccess implements IGameAccess, IAccess
{
    private SQLiteEngine engine;
    private final static Logger LOGGER = Logger.getLogger(GameAccess.class.getName());

    public GameAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    @Override public void updateModel(int gameID, ServerModel game) throws Exception
    {
        PreparedStatement stmt = null;
        try
        {
            String query = "UPDATE Game SET Model = ? WHERE GameID = ?";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setBlob(1, (Blob) game);
            stmt.setInt(2, gameID);
            if (stmt.executeUpdate() != 1)
            {
                throw new ServerException("Could not update ServerModel for gameID " + gameID);
            }
        } finally
        {
            SQLiteEngine.safeClose(stmt);
        }
    }

    @Override public void addGame(ServerModel game, String gameName) throws Exception
    {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        try
        {
            String query = "INSERT INTO Game (Model, Name) VALUES (?,?)";
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setBlob(1, (Blob) game);
            stmt.setString(2, gameName);
            if (stmt.executeUpdate() == 1)
            {
                Statement keyStmt = engine.getConnection().createStatement();
                keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
            } else
            {
                throw new ServerException("Query wasn't executed properly to add a game");
            }
        } finally
        {
            SQLiteEngine.safeClose(stmt);
            SQLiteEngine.safeClose(keyRS);
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
                String json = rs.getString(1);

                result = new ServerModel(json);
            }
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
                return null;
            }
            while (rs.next())
            {
                String json = rs.getString(1);
                result.add(new ServerModel(json));
            }
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
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }
}
