package database;

import server.ServerModel;

/**
 * Created by dtaylor on 12/5/2015.
 */
public class Game
{
    ServerModel model;
    String name;
    int gameID;
    public Game(ServerModel model, String name, int gameID)
    {
        this.model = model;
        this.name = name;
        this.gameID = gameID;
    }

    public void setModel(ServerModel model)
    {
        this.model = model;
    }

    public ServerModel getModel()
    {
        return model;
    }
}
