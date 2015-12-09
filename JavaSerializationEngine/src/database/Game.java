package database;

import server.ServerModel;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class Game implements Serializable
{
    private ServerModel model;
    private String name;
    private int gameID;

    public Game(ServerModel model, String name, int gameID)
    {
        this.model = model;
        this.name = name;
        this.gameID = gameID;
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
                fout = new FileOutputStream(Paths.get("..", "plugins", "SQLiteEngine", this.name).toFile(), true);
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
                streamIn = new FileInputStream(Paths.get("..", "plugins", "SQLiteEngine", filename).toFile());
                objectinputstream = new ObjectInputStream(streamIn);
                game = (Game) objectinputstream.readObject();
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
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return game;
    }
}
