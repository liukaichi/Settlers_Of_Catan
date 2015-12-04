package server.plugin;

import server.ServerModel;

import java.util.List;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface IGameAccess
{
    public void updateModel(int gameID, ServerModel game);

    public void addCommand(int gameID);

    public void addGame(ServerModel game, String gameName);

    public ServerModel getGame(int gameID);

    public List<ServerModel> getAllGames();

    public int getNumberOfCommands(int gameID);
}
