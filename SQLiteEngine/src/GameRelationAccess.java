import java.util.List;

/**
 * SQL Database Access Object for the Join table between Users and Games.
 */
public class GameRelationAccess
{
    private SQLiteEngine engine;
    public GameRelationAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    public void addUserToGame(int userID, int gameID)
    {

    }

    public List<Integer> listPlayersInGame(int gameID)
    {
        return null;
    }
}
