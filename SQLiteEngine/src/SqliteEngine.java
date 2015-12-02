import server.ServerModel;
import server.manager.User;
import server.plugin.IPersistenceEngine;
import shared.communication.CatanCommand;
import shared.communication.Credentials;

/**
 * Created by liukaichi on 12/2/2015.
 */
public class SQLiteEngine implements IPersistenceEngine
{
    final int DEFAULT_COMMAND_BETWEEN_SAVES = 10;
    int commandsBetweenSaves = DEFAULT_COMMAND_BETWEEN_SAVES;


    public SQLiteEngine(int commandsBetweenSaves)
    {
        this.commandsBetweenSaves = commandsBetweenSaves;
    }
    @Override public boolean saveGame(int gameID, CatanCommand catanCommand, ServerModel game)
    {
        return false;
    }

    @Override public ServerModel loadGame(int gameID)
    {
        return null;
    }

    @Override public void addPlayerToGame(int playerID, int gameID)
    {

    }

    @Override public int registerUser(Credentials credentials)
    {
        return 23456;
    }

    @Override public User getUser(int id)
    {
        return null;
    }

    @Override public User getUser(Credentials credentials)
    {
        return null;
    }

    @Override public boolean startTransaction()
    {
        return false;
    }

    @Override public boolean endTransaction()
    {
        return false;
    }
}
