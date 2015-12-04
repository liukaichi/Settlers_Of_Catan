import server.ServerModel;

import java.util.List;

/**
 * SQL Database Access Object for Games.
 */
public class GameAccess
{
    private SQLiteEngine engine;
    public GameAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    public void saveGame(int gameID, ServerModel game)
    {

    }

    public void addGame(ServerModel game)
    {

    }

    public ServerModel getGame(int gameID)
    {
        return null;
    }

    public List<ServerModel> getAllGames()
    {
        return null;
    }
}
