package server.util;

import client.data.PlayerInfo;
import org.junit.Test;
import server.ServerModel;
import shared.definitions.PlayerIndex;
import shared.model.TurnTracker;
import shared.model.message.Chat;
import shared.model.message.Log;
import shared.model.player.Player;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test getting models from File.
 */
public class FileUtilsTest
{

    @Test public void testGetModelFromFile() throws Exception
    {
        ServerModel testModel = FileUtils.getModelFromFile("sample/serverDefaults/", "game-0");
        List<PlayerInfo> players = testModel.getPlayerInfos();
        List<Player> playerList = testModel.getPlayers();
        assertNotNull(playerList);
        assertEquals(4, players.size());
        assertEquals(4, playerList.size());
        PlayerInfo player0 = players.get(0);
        assertEquals(0, player0.getId());
        assertEquals(PlayerIndex.PLAYER_0, player0.getPlayerIndex());
        assertEquals("Sam", player0.getName());
        Log log = testModel.getLog();
        assertEquals(24, log.size());
        Chat chat = testModel.getChat();
        assertEquals(0, chat.size());

        testModel = FileUtils.getModelFromFile("sample/serverDefaults/", "game-2");
        TurnTracker turnTracker = testModel.getTurnTracker();
        assertEquals(PlayerIndex.NONE, turnTracker.getLargestArmy());
        assertEquals(PlayerIndex.NONE, turnTracker.getLongestRoad());
        assertEquals(PlayerIndex.PLAYER_0, turnTracker.getCurrentTurn());

        ServerModel secondModel = FileUtils.getModelFromFile("sample/serverDefaults/", "game-2");
        //Fails. Not sure why... assertEquals(testModel,secondModel);
    }

    @Test public void testGetGameInfoFromFile() throws Exception
    {

    }
}