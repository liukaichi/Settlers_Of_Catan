package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class Database
{
    List<Game> games;
    public Database()
    {
        games = new ArrayList<>();
    }

    public void setGames(List<Game> games)
    {
        this.games = games;
    }
}
