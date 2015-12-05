package server.plugin;

import server.ServerModel;

import java.util.List;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface IGameAccess
{
    void updateModel(int gameID, ServerModel game);

    void addCommand(int gameID);

    void addGame(ServerModel game, String gameName);

    ServerModel getGame(int gameID);

    List<ServerModel> getAllGames();

    int getNumberOfCommands(int gameID);
}
