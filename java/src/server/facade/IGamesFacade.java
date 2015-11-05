package server.facade;

import client.data.GameInfo;

import java.util.List;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IGamesFacade
{
    List<GameInfo> listGames();
    void joinGame();
    void createGame();
}
