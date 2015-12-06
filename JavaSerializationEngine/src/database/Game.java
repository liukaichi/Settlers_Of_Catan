package database;

import server.ServerModel;
import shared.communication.moveCommands.MoveCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class Game
{
    ServerModel model;
    String name;
    int gameID;
    List<MoveCommand> commands;
    public Game(ServerModel model, String name, int gameID)
    {
        this.model = model;
        this.name = name;
        this.gameID = gameID;
        this.commands = new ArrayList<>();
    }

    public void setModel(ServerModel model)
    {
        this.model = model;
    }

    public ServerModel getModel()
    {
        return model;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getGameID()
    {
        return gameID;
    }

    public void setGameID(int gameID)
    {
        this.gameID = gameID;
    }

    public List<MoveCommand> getCommands()
    {
        return commands;
    }

    public void setCommands(List<MoveCommand> commands)
    {
        this.commands = commands;
    }
}
