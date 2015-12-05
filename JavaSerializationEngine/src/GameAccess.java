import server.ServerModel;
import server.plugin.IGameAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameAccess implements IGameAccess, IAccess
{
    Map<Integer, ServerModel> models;
    @Override public void initialize()
    {
        models = new HashMap<>();
    }

    @Override public void updateModel(int gameID, ServerModel game) throws Exception
    {
        ServerModel model = models.get(gameID);
        if(model != null)
        {
            models.put(gameID, game);
        }
        else
        {
            models.put(gameID, game);
        }
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
