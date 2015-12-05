import server.plugin.IGameRelationAccess;

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

    }

    @Override public List<Integer> listPlayersInGame(int gameID) throws Exception
    {
        return null;
    }

    @Override public void initializeTable()
    {
        LOGGER.entering(getClass().getName(), "initializeTable");
        Statement stat = null;
        try
        {
            stat = engine.getConnection().createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS GameRelation;");
            stat.executeUpdate(
                    "CREATE TABLE GameRelation (" + "GameID INTEGER REFERENCES  Game(GameID), " + "UserID INTEGER REFERENCES User(UserID)" + ")");
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            SQLiteEngine.safeClose(stat);
        }
    }
}
