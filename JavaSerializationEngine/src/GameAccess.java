import database.Game;
import database.GameRegistry;
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
        GameRegistry gameRegistry = GameRegistry.getInstance();
        Game game = gameRegistry.loadGame(gameID);
        if(game != null)
        {
            game.setModel(model);
            game.serialize();
            //games.get(gameID).updateModel(game);
        }
        else
        {
            throw new Exception("No model to update");
        }
    }

    @Override public int addGame(ServerModel model, String gameName) throws Exception
    {
        GameRegistry gameRegistry = GameRegistry.getInstance();
        return gameRegistry.addGame(model, gameName);
    }

    @Override public ServerModel getGame(int gameID) throws Exception
    {
        GameRegistry gameRegistry = GameRegistry.getInstance();
        Game game = gameRegistry.loadGame(gameID);
        if(game == null)
            throw new Exception("No game has the ID: "+ gameID);
        return game.getModel();
    }

    @Override public List<ServerModel> getAllGames() throws Exception
    {
        GameRegistry gameRegistry = GameRegistry.getInstance();
        List<Game> games = gameRegistry.loadAllGames();
        return games.stream().map(Game::getModel).collect(Collectors.toList());
    }

    @Override public int getNextGameID() throws Exception
    {
        return 0;
    }

}
