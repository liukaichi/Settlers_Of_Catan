import database.Game;
import server.ServerModel;
import server.plugin.IGameAccess;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameAccess implements IGameAccess, IAccess
{
    List<Game> games;

    @Override public void initialize()
    {
        games = new ArrayList<>();
    }

    @Override public void updateModel(int gameID, ServerModel model) throws Exception
    {
        Game game = games.get(gameID);
        if(game != null)
        {
            game.setModel(model);
            //games.get(gameID).updateModel(game);
        }
        throw new Exception("No model to update");
    }

    @Override public void addGame(ServerModel game, String gameName) throws Exception
    {
        games.add(new Game(game, gameName, games.size()));
    }

    @Override public ServerModel getGame(int gameID) throws Exception
    {
        Game game = games.get(gameID);
        if(game == null)
            throw new Exception("No game has the ID: "+ gameID);
        return game.getModel();
    }

    @Override public List<ServerModel> getAllGames() throws Exception
    {
        return games.stream().map(Game::getModel).collect(Collectors.toList());
    }

    @Override public int getNumberOfCommands(int gameID) throws Exception
    {
        if(games.get(gameID) != null)
            return games.size();
        throw new Exception("No games in database");
    }
}
