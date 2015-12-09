package database;

import shared.communication.Credentials;
import shared.communication.moveCommands.MoveCommand;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class Database
{
    private static Database _instance;
    private List<Game> games;
    private Map<Integer, Credentials> credentials;

    public static Database getInstance()
    {
        if(_instance == null)
            _instance = new Database();
        return _instance;
    }

    public Database()
    {
        initialize();
    }

    public void initialize()
    {
        games = new ArrayList<>();
        credentials = new HashMap<>();
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
        return games.get(gameID-1);
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

    public Map<Integer, Credentials> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<Integer, Credentials> credentials) {
        this.credentials = credentials;
    }

    public void serialize()
    {
        try
        {
            ObjectOutputStream oos = null;
            FileOutputStream fout = null;
            try
            {
                fout = new FileOutputStream("..\\plugins\\SQLiteEngine\\catan.db", true);
                oos = new ObjectOutputStream(fout);
                oos.writeObject(this);
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                if (oos != null)
                {
                    oos.close();
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }
    public static Database deserialize()
    {
        Database database = null;
        try
        {
            ObjectInputStream objectinputstream = null;
            FileInputStream streamIn;
            try
            {
                streamIn = new FileInputStream("..\\plugins\\SQLiteEngine\\catan.db");
                objectinputstream = new ObjectInputStream(streamIn);
                database = (Database) objectinputstream.readObject();
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                if (objectinputstream != null)
                {
                    objectinputstream.close();
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return database;
    }
}
