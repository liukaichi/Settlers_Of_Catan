import server.ServerModel;
import server.manager.User;
import server.plugin.IPersistenceEngine;
import shared.communication.Credentials;
import shared.communication.moveCommands.MoveCommand;

import java.util.List;
import java.util.Map;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class JavaSerializationEngine extends IPersistenceEngine
{
    @Override public boolean saveGame(int gameID, MoveCommand moveCommand, ServerModel game)
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

    @Override public int getNextGameID()
    {
        return 0;
    }

    @Override public List<ServerModel> getAllGames()
    {
        return null;
    }

    @Override public Map<Integer, Credentials> getAllUsers()
    {
        return null;
    }

}
