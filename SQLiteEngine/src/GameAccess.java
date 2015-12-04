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
public class GameAccess implements IGameAccess
{
    private SQLiteEngine engine;
    private final static Logger LOGGER = Logger.getLogger(GameAccess.class.getName());

    public GameAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    @Override
    public void updateModel(int gameID, ServerModel game)
    {
        PreparedStatement stmt = null;
        try {
            String query = "UPDATE Game SET Model = (" + game.toString() + ") WHERE GameID = " + gameID;
            engine.startTransaction();
            stmt = engine.getConnection().prepareStatement(query);
            if (stmt.executeUpdate() != 1) {
                engine.endTransaction(false);
                throw new ServerException("Could not update ServerModel for gameID " + gameID);
            }
        }
        catch (SQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
        }
        catch (ServerException e)
        {
            LOGGER.warning(e.getLocalizedMessage());
        }
        finally {
            engine.safeClose(stmt);
            engine.endTransaction(true);
        }
    }

    @Override public void addCommand(int gameID)
    {
        PreparedStatement stmt = null;
        try {
            String query = "UPDATE Game SET CurrentCommandNo = (CurrentCommandNo + 1) WHERE GameID = " + gameID;
            engine.startTransaction();
            stmt = engine.getConnection().prepareStatement(query);
            if (stmt.executeUpdate() != 1) {
                engine.endTransaction(false);
                throw new ServerException("Could not update ServerModel for gameID " + gameID);
            }
        }
        catch (SQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
        }
        catch (ServerException e)
        {
            LOGGER.warning(e.getLocalizedMessage());
        }
        finally {
            engine.safeClose(stmt);
            engine.endTransaction(true);
        }
    }

    @Override
    public void addGame(ServerModel game, String gameName)
    {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        try {
            String query = "INSERT into Game (Model, CurrentCommandNo, Name) VALUES " +
                    "(?,?,?)";
            engine.startTransaction();
            stmt = engine.getConnection().prepareStatement(query);
            stmt.setBlob(1, (Blob) game);
            stmt.setInt(2, 0);
            stmt.setString(3, gameName);
            if (stmt.executeUpdate() == 1) {
                Statement keyStmt = engine.getConnection().createStatement();
                keyRS = keyStmt.executeQuery("select last_insert_rowid()");
                keyRS.next();
                int id = keyRS.getInt(1);
            }
            else {
                throw new ServerException("Query wasn't executed properly to add a game");
            }
        }
        catch (SQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
        }
        catch (ServerException e)
        {
            LOGGER.warning(e.getLocalizedMessage());
        }
        finally {
            engine.safeClose(stmt);
            engine.safeClose(keyRS);
            engine.endTransaction(true);
        }
    }

    @Override
    public ServerModel getGame(int gameID)
    {
        ServerModel result = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT Model FROM Game WHERE GameID = " + gameID;
            engine.startTransaction();
            stmt = engine.getConnection().prepareStatement(query);

            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()){
                engine.endTransaction(true);
                return null;
            }
            while (rs.next()) {
                String json = rs.getString(1);

                result = new ServerModel(json);
            }
        }
        catch (SQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
        }
        finally {
            engine.safeClose(rs);
            engine.safeClose(stmt);
            engine.endTransaction(true);
        }

        return result;
    }

    @Override
    public List<ServerModel> getAllGames()
    {
        List<ServerModel> result = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = "SELECT Model FROM Game";
            engine.startTransaction();
            stmt = engine.getConnection().prepareStatement(query);

            rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()){
                engine.endTransaction(true);
                return null;
            }
            while (rs.next()) {
                String json = rs.getString(1);
                result.add(new ServerModel(json));
            }
        }
        catch (SQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
        }
        finally {
            engine.safeClose(rs);
            engine.safeClose(stmt);
            engine.endTransaction(true);
        }

        return result;
    }
}
