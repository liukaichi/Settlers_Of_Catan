package server.plugin;

import server.ServerModel;

import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by cstaheli on 12/4/2015.
 */
public interface IGameAccess
{
    void updateModel(int gameID, ServerModel game) throws Exception;

    void addCommand(int gameID) throws Exception;

    void addGame(ServerModel game, String gameName) throws Exception;

    ServerModel getGame(int gameID) throws Exception;

    List<ServerModel> getAllGames() throws Exception;

    int getNumberOfCommands(int gameID) throws Exception;
}
