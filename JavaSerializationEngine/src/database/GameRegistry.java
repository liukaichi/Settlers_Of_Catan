package database;

import server.ServerModel;
import server.manager.User;
import shared.communication.Credentials;
import shared.communication.moveCommands.MoveCommand;
import shared.definitions.exceptions.CatanException;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by dtaylor on 12/8/2015.
 */
public class GameRegistry implements Serializable
{
    private static GameRegistry _instance;
    public static GameRegistry getInstance()
    {
        if(_instance == null)
            _instance = new GameRegistry();
        return _instance;
    }

    /**
     * Maps a game id to commands list
     */
    private Map<Integer, Commands> commands;
    /**
     * Maps a user id to user credential
     */
    private Map<Integer, Credentials> users;
    /**
     * Maps a game id to filename (game title)
     */
    private Map<Integer, String> games;

    public GameRegistry()
    {
        users = new HashMap<>();
        games = new HashMap<>();
    }

    public int registerUser(Credentials credentials)
    {
        int nextID = users.size()+1;
        users.put(nextID, credentials);
        return nextID;
    }

    public User getUser(Credentials credentials)
    {
        User user = new User(credentials, -1);
        for (Map.Entry<Integer, Credentials> entry : users.entrySet())
        {
            if (Objects.equals(credentials, entry.getValue()))
            {
                user.assignUserID(entry.getKey());
                break;
            }
        }
        return user;
    }

    public User getUser(int id) throws CatanException
    {
        if (users.containsKey(id))
        {
            return new User(users.get(id), id);
        }
        throw new CatanException(String.format("No such user exists with ID %d",id));
    }

    public Game loadGame(int gameID) throws CatanException
    {
        String filename = games.get(gameID);
        if(filename != null)
        {
            return Game.deserialize(filename);
        }
        throw new CatanException(String.format("Cannot load game: %d",gameID));
    }

    public int addGame(ServerModel model, String gameName)
    {
        Game game = new Game(model, gameName, games.size());
        games.put(game.getGameID(), gameName);
        game.serialize();
        return game.getGameID();
    }

    public void serialize()
    {
        try
        {
            ObjectOutputStream oos = null;
            FileOutputStream fout = null;
            try
            {
                fout = new FileOutputStream(Paths.get("..","plugins","SQLiteEngine","GameRegistry.db").toFile(), true);
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

    public static GameRegistry deserialize() throws Exception
    {
        GameRegistry gameRegistry = null;
        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn;
        try
        {
            streamIn = new FileInputStream(Paths.get("..","plugins","SQLiteEngine","GameRegistry.db").toFile());
            objectinputstream = new ObjectInputStream(streamIn);
            gameRegistry = (GameRegistry) objectinputstream.readObject();
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            if (objectinputstream != null)
            {
                objectinputstream.close();
            }
        }
        _instance = gameRegistry;
        return _instance;
    }

    public List<Game> loadAllGames() throws CatanException
    {
        List<Game> loadedGames = new ArrayList<>();
        for(int gameID : games.keySet())
        {
            loadedGames.add(loadGame(gameID));
        }
        return loadedGames;
    }
}
