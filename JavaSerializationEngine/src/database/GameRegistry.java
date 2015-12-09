package database;

import server.ServerModel;
import server.manager.User;
import shared.communication.Credentials;
import shared.communication.moveCommands.MoveCommand;
import shared.definitions.exceptions.CatanException;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 12/8/2015.
 */
public class GameRegistry implements Serializable
{
    static final long serialVersionUID = 42L;
    private static final Logger LOGGER = Logger.getLogger(GameRegistry.class.getName());
    private static GameRegistry _instance;
    public static GameRegistry getInstance()
    {
        if(_instance == null)
        {
            try
            {
                _instance = deserialize();
            }
            catch(Exception e)
            {
                _instance = new GameRegistry();
                LOGGER.log(Level.WARNING,"File not found", e);
            }
        }
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
        if(getUser(credentials) == null)
        {
            int nextID = users.size() + 1;
            users.put(nextID, credentials);
            return nextID;
        }
        return -1;
    }

    public User getUser(Credentials credentials)
    {
        User user = null;
        for (Map.Entry<Integer, Credentials> entry : users.entrySet())
        {
            if (Objects.equals(credentials, entry.getValue()))
            {
                user = new User(credentials, entry.getKey());
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
        serialize();
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
                File db = Paths.get("plugins","JavaSerializationEngine","GameRegistry.db").toFile();
                if(!db.exists())
                {
                    db.createNewFile();
                }
                fout = new FileOutputStream(db, true);
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
            File file = Paths.get("plugins","JavaSerializationEngine","GameRegistry.db").toFile();
            if(file.exists())
            {
                streamIn = new FileInputStream(file);
                objectinputstream = new ObjectInputStream(streamIn);
                gameRegistry = (GameRegistry) objectinputstream.readObject();
            }
            else
            {
                throw new FileNotFoundException("File not found");
            }
        } catch (FileNotFoundException e)
        {
            //LOGGER.log(Level.WARNING,"File not found", e);
            return new GameRegistry();
        } finally
        {
            if (objectinputstream != null)
            {
                objectinputstream.close();
            }
        }
        return gameRegistry;
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

    public int getNextGameID()
    {
        return games.size();
    }

    public Map<Integer, Credentials> getAllUsers()
    {
        return users;
    }
}
