package database;

import shared.communication.moveCommands.MoveCommand;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 12/8/2015.
 */
public class Commands
{
    private static final Logger LOGGER = Logger.getLogger(Commands.class.getName());
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
                File file = Paths.get("..","plugins").toFile();
                if(!file.exists())
                {
                    Files.createDirectory(file.toPath());
                }
                fout = new FileOutputStream(Paths.get("..","plugins","JavaSerializationEngine",String.valueOf(gameID)+"Commands").toFile(), true);
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
            File file = null;
            try
            {
                file = Paths.get("..","plugins","JavaSerializationEngine",String.valueOf(gameID)+"Commands").toFile();
                streamIn = new FileInputStream(file);
                objectinputstream = new ObjectInputStream(streamIn);
                commands = (Commands) objectinputstream.readObject();
            } catch (Exception e)
            {
                LOGGER.log(Level.WARNING,"File not found", e);
                return new Commands(gameID);
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

