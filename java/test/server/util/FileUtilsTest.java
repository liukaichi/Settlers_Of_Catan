package server.util;

import client.data.GameInfo;
import client.data.PlayerInfo;
import org.junit.Test;
import server.ServerModel;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.TurnTracker;
import shared.model.map.CatanMap;
import shared.model.map.Hex;
import shared.model.map.structure.Port;
import shared.model.message.Chat;
import shared.model.message.Log;
import shared.model.player.Player;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test getting models from File.
 */
public class FileUtilsTest
{

    @Test public void testGetModelFromFile()
    {
        ServerModel testModel = FileUtils.getModelFromFile("sample/serverDefaults/", "game-0");
        assertNotNull(testModel);
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
        assertNotNull(testModel);
        TurnTracker turnTracker = testModel.getTurnTracker();
        assertEquals(PlayerIndex.NONE, turnTracker.getLargestArmy());
        assertEquals(PlayerIndex.NONE, turnTracker.getLongestRoad());
        assertEquals(PlayerIndex.PLAYER_0, turnTracker.getCurrentTurn());

        ServerModel secondModel = FileUtils.getModelFromFile("sample/serverDefaults/", "game-2");
        assertEquals(testModel,secondModel);
    }

    @Test public void testGetGameInfoFromFile() throws Exception
    {
        GameInfo testGameInfo = FileUtils.getGameInfoFromFile("sample/serverDefaults/", "gameInfo-0");
        assertNotNull(testGameInfo);
        List<PlayerInfo> players = testGameInfo.getPlayers();
        assertEquals(4, players.size());
        PlayerInfo sam = new PlayerInfo(0,"Sam", CatanColor.ORANGE);
        assertEquals(players.get(0), sam);
        assertEquals("Amanda", players.get(1).getName());
        assertEquals(10, players.get(2).getId());
        assertEquals(CatanColor.GREEN, players.get(3).getColor());
        GameInfo anotherGameInfo = FileUtils.getGameInfoFromFile("sample/serverDefaults/", "gameInfo-0");
        assertEquals(testGameInfo, anotherGameInfo);
    }

    @Test public void testGetCatanMapFromFile()
    {
        CatanMap map = FileUtils.getCatanMapFromFile(null, "defaultMap");
        assertNotNull(map);
        Map<HexLocation, Hex> hexes = map.getHexes();
        assertNotNull(hexes);
        Hex hex = hexes.get(new HexLocation(1,-2));
        assertEquals(HexType.BRICK ,hex.getHexType());
        hex = hexes.get(new HexLocation(-2,2));
        assertEquals(6, hex.getNumberTile());
        hex = hexes.get(new HexLocation(-1,1));
        Hex duplicateHex = new Hex(new HexLocation(-1,1), HexType.SHEEP, ResourceType.SHEEP, 9, false);
        //assertEquals(duplicateHex, hex);
        List<Port> ports = map.getPorts();
        assertEquals(9, ports.size());
    }
}