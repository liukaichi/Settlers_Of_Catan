package database;

import shared.communication.moveCommands.MoveCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class Database
{
    private static Database _instance;
    List<Game> games;

    public static Database getInstance()
    {
        if(_instance == null)
            _instance = new Database();
        return _instance;
    }

    public Database()
    {
        games = new ArrayList<>();
    }

    public void setGames(List<Game> games)
    {
        this.games = games;
    }

    public List<Game> getGames()
    {
        return games;
    }

    public Game getGame(int gameID)
    {
        return games.get(gameID);
    }

    public List<MoveCommand> getGameCommands(int gameID)
    {
        Game game = getGame(gameID);
        if(game != null)
        {
            return game.getCommands();
        }
        return null;
    }

    public List<Integer> getUsers(int gameID)
    {
        Game game = getGame(gameID);
        if(game != null)
        {
            return game.getUsers();
        }
        return null;
    }
}
