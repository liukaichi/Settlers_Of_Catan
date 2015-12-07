import database.Database;
import database.Game;
import server.ServerModel;
import server.plugin.IGameAccess;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class GameAccess implements IGameAccess
{
    @Override public void initialize()
    {

    }

    @Override public void updateModel(int gameID, ServerModel model) throws Exception
    {
        Game game = Database.getInstance().getGame(gameID);
        if(game != null)
        {
            game.setModel(model);
            //games.get(gameID).updateModel(game);
        }
        throw new Exception("No model to update");
    }

    @Override public int addGame(ServerModel game, String gameName) throws Exception
    {
        List<Game> games = Database.getInstance().getGames();
        games.add(new Game(game, gameName, games.size()));
        return games.size();
    }

    @Override public ServerModel getGame(int gameID) throws Exception
    {
        Game game = Database.getInstance().getGame(gameID);
        if(game == null)
            throw new Exception("No game has the ID: "+ gameID);
        return game.getModel();
    }

    @Override public List<ServerModel> getAllGames() throws Exception
    {
        List<Game> games = Database.getInstance().getGames();
        return games.stream().map(Game::getModel).collect(Collectors.toList());
    }

    @Override public int getNextGameID() throws Exception
    {
        return 0; //TODO Implement this.
    }

}
