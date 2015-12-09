package database;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.ServerModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class Game implements Serializable
{
    static final long serialVersionUID = 42L;
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
    private ServerModel model;
    private String title;
    private int gameID;

    public Game(ServerModel model, String title, int gameID)
    {
        this(model);
        this.model = model;
        this.title = title;
        this.gameID = gameID;
    }

    public Game(ServerModel game)
    {
        GameInfo gameInfo = game.getGameInfo();
        this.gameID = gameInfo.getId();
        this.title = gameInfo.getTitle();
    }

    public int getGameID()
    {
        return this.gameID;
    }

    public void setModel(ServerModel model)
    {
        this.model = model;
    }

    public ServerModel getModel()
    {
        return model;
    }

    public void serialize()
    {
        try
        {
            ObjectOutputStream oos = null;
            FileOutputStream fout = null;
            try
            {
                File db = Paths.get("plugins", "JavaSerializationEngine", this.title).toFile();
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
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static Game deserialize(String filename)
    {
        Game game = null;
        try
        {
            ObjectInputStream objectinputstream = null;
            FileInputStream streamIn;
            try
            {
                streamIn = new FileInputStream(Paths.get("plugins", "JavaSerializationEngine", filename).toFile());
                objectinputstream = new ObjectInputStream(streamIn);
                game = (Game) objectinputstream.readObject();
            } catch (Exception e)
            {
                //LOGGER.log(Level.WARNING,"File not found", e);
            } finally
            {
                if (objectinputstream != null)
                {
                    objectinputstream.close();
                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return game;
    }
}
