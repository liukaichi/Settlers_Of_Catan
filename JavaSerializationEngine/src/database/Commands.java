package database;

import shared.communication.moveCommands.MoveCommand;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtaylor on 12/8/2015.
 */
public class Commands
{
    private int gameID;
    private List<MoveCommand> commands;

    public Commands(int gameID)
    {
        this.gameID = gameID;
        this.commands = new ArrayList<>();
    }

    public void serialize()
    {
        try
        {
            ObjectOutputStream oos = null;
            FileOutputStream fout = null;
            try
            {
                fout = new FileOutputStream(Paths.get("..","plugins","SQLiteEngine",String.valueOf(gameID),"Commands").toFile(), true);
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

    public static Commands deserialize(int gameID)
    {
        Commands commands = null;
        try
        {
            ObjectInputStream objectinputstream = null;
            FileInputStream streamIn;
            try
            {
                streamIn = new FileInputStream(Paths.get("..","plugins","SQLiteEngine",String.valueOf(gameID),"Commands").toFile());
                objectinputstream = new ObjectInputStream(streamIn);
                commands = (Commands) objectinputstream.readObject();
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
        return commands;
    }

    public void add(MoveCommand command)
    {
        commands.add(command);
    }

    public int getNumberOfCommandsInGame()
    {
        return commands.size();
    }

    public List<MoveCommand> toList()
    {
        return commands;
    }

    public MoveCommand get(int sequenceNumber)
    {
        return commands.get(sequenceNumber);
    }
}

