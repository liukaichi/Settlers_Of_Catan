package server.manager;

import client.data.GameInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.ServerModel;
import shared.model.message.Log;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the GameManager
 */
public class GameManagerTest
{
    private GameManager gameManager = GameManager.getInstance();

    @Before public void setUp() throws Exception
    {

    }

    @After public void tearDown() throws Exception
    {

    }

    @Test public void testListGames() throws Exception
    {
        List<GameInfo> games = gameManager.listGames();
        assertTrue(games.size() >= 4);
    }

    @Test public void testJoinGame() throws Exception
    {
        //        gameManager.joinGame(new PlayerInfo());
    }

    @Test public void testCreateGame() throws Exception
    {

    }

    @Test public void testGetGame() throws Exception
    {
        GameManager gameManager = GameManager.getInstance();
        ServerModel model = gameManager.getGame(-1);
        assertNull(model);

        model = gameManager.getGame(3);
        assertNotNull(model);
        Log log = model.getLog();
        assertEquals(0, log.size());
        assertEquals(4, model.getGameInfo().getPlayers().size());
    }
}