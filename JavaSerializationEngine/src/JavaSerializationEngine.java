import database.Database;
import server.ServerModel;
import server.manager.User;
import server.plugin.IPersistenceEngine;
import shared.communication.CatanCommand;
import shared.communication.Credentials;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class JavaSerializationEngine extends IPersistenceEngine
{
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
        return 0;
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

    @Override public boolean endTransaction(boolean commit)
    {
        return false;
    }

    @Override public boolean addGame(ServerModel model, String name)
    {
        return false;
    }
}
