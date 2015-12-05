import server.plugin.IGameRelationAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

/**
 * SQL Database Access Object for the Join table between Users and Games.
 */
public class GameRelationAccess implements IGameRelationAccess, IAccess
{
    private Logger LOGGER = Logger.getLogger(GameRelationAccess.class.getName());
    private SQLiteEngine engine;
    public GameRelationAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    @Override public void addUserToGame(int userID, int gameID) throws Exception
    {
        LOGGER.entering(getClass().getName(), "addUserToGame");
        PreparedStatement statement = null;
        ResultSet keyRS = null;
        try {
            String query = "INSERT into GameRelation (GameID, UserID) VALUES " +
                    "(?,?)";
            statement = engine.getConnection().prepareStatement(query);
            statement.setInt(1, gameID);
            statement.setInt(2, userID);
            if (statement.executeUpdate() == 1) {
                Statement keyStmt = engine.getConnection().createStatement();
                keyRS = keyStmt.executeQuery("select last_insert_rowid()");
            }
        } catch (SQLException e) {

        } finally {
            SQLiteEngine.safeClose(statement);
            SQLiteEngine.safeClose(keyRS);
        }
    }

    @Override public List<Integer> listPlayersInGame(int gameID) throws Exception
    {
        LOGGER.entering(getClass().getName(), "listPlayersInGame");
        PreparedStatement statement = null;
        ResultSet keyRS = null;
        List<Integer> playerIDs = null;
        try {
            String query = "SELECT Player FROM Game WHERE GameID = ?";
            statement = engine.getConnection().prepareStatement(query);
            statement.setInt(1, gameID);
            keyRS = statement.executeQuery();
            while (keyRS.next()) {
                int playerID = keyRS.getInt(1);
                playerIDs.add(playerID);
            }
        } catch (SQLException e) {

        } finally {
            SQLiteEngine.safeClose(statement);
            SQLiteEngine.safeClose(keyRS);
            return playerIDs;
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
            stat.executeUpdate("DROP TABLE IF EXISTS GameRelation;");
            stat.executeUpdate(
                    "CREATE TABLE GameRelation ("
                            + "GameID INTEGER REFERENCES  Game(GameID), "
                            + "UserID INTEGER REFERENCES User(UserID)"
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
