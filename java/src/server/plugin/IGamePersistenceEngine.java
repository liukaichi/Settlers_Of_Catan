package server.plugin;

import server.ServerModel;
import shared.communication.CatanCommand;

/**
 * Created by cstaheli on 12/2/2015.
 */
public interface IGamePersistenceEngine
{
    boolean saveCommand(int gameID, CatanCommand catanCommand, ServerModel game);
    ServerModel loadGame(int gameID);

    void addPlayerToGame(int playerID, int gameID);
}
