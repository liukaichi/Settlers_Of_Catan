import server.ServerModel;
import server.plugin.IGameAccess;

import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameAccess implements IGameAccess, IAccess
{
    @Override public void initializeTable()
    {

    }

    @Override public void updateModel(int gameID, ServerModel game) throws Exception
    {

    }

    @Override public void addCommand(int gameID) throws Exception
    {

    }

    @Override public void addGame(ServerModel game, String gameName) throws Exception
    {

    }

    @Override public ServerModel getGame(int gameID) throws Exception
    {
        return null;
    }

    @Override public List<ServerModel> getAllGames() throws Exception
    {
        return null;
    }

    @Override public int getNumberOfCommands(int gameID) throws Exception
    {
        return 0;
    }
}
