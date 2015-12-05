import server.ServerModel;
import server.plugin.IGameAccess;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameAccess implements IGameAccess, IAccess
{
    List<Map.Entry<String, ServerModel>> games;
    @Override public void initialize()
    {
        games = new ArrayList<>();
    }

    @Override public void updateModel(int gameID, ServerModel game) throws Exception
    {
        Map.Entry<String, ServerModel> model = games.get(gameID);
        if(model != null)
        {
            games.set(gameID, new AbstractMap.SimpleEntry<>(model.getKey(),game));
            //games.get(gameID).updateModel(game);
        }
        throw new Exception("No model to update");
    }

    @Override public void addCommand(int gameID) throws Exception
    {

    }

    @Override public void addGame(ServerModel game, String gameName) throws Exception
    {
        games.add(new AbstractMap.SimpleEntry<>(gameName, game));
    }

    @Override public ServerModel getGame(int gameID) throws Exception
    {
        Map.Entry<String, ServerModel> game = games.get(gameID);
        if(game == null)
            throw new Exception("No game has the ID: "+ gameID);
        return game.getValue();
    }

    @Override public List<ServerModel> getAllGames() throws Exception
    {
        return games.stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override public int getNumberOfCommands(int gameID) throws Exception
    {
        if(games.get(gameID) != null)
            return games.size();
        throw new Exception("No games in database");
    }
}
