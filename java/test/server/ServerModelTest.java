package server;

import client.base.IAction;
import client.data.PlayerInfo;
import org.junit.Before;
import org.junit.Test;
import server.util.FileUtils;
import shared.definitions.CatanColor;
import shared.definitions.exceptions.CatanException;
import shared.locations.*;
import shared.model.bank.resource.Resources;
import shared.model.player.Player;

import static org.junit.Assert.*;

/**
 * Created by dtaylor on 11/16/2015.
 */
public class ServerModelTest
{
    ServerModel model;
    Player player1;
    Player player2;
    @Before public void setUp()
    {
        model = new ServerModel();
        model.updateModel(FileUtils.getModelFromFile("sample/serverDefaults/", "game-2"));
        player1 = new Player(new PlayerInfo(0,"player1", CatanColor.BLUE));
        player2 = new Player(new PlayerInfo(1,"player2", CatanColor.BLUE));
    }

    @Test public void testBuildRoad()
    {
        assertTrue(buildRoadOnLand());
        assertFalse(buildRoadOnWater());
        assertFalse(buildRoadOnRoad());
        assertFalse(buildRoadWithoutSettlement());
    }

    private boolean buildRoadWithoutSettlement()
    {
        return false;
    }

    private boolean buildRoadOnRoad()
    {
        return false;
    }

    private boolean buildRoadOnWater()
    {
        EdgeLocation waterOnly = new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North);
        try
        {
            model.buildRoad(player1.getPlayerIndex(), waterOnly, false);
            assertNotNull(model.getMap().getRoads().get(waterOnly));
            model.getMap().getRoads().put(waterOnly,null);
            return true;
        }
        catch ( CatanException e )
        {
            try
            {
                player1.getBank().setPlayerResources(new Resources(2,2,2,2,2));
                model.buildRoad(player1.getPlayerIndex(), waterOnly, true);
                assertNotNull(model.getMap().getRoads().get(waterOnly));
                model.getMap().getRoads().put(waterOnly,null);
                return true;
            }
            catch ( CatanException e2 )
            {
                return false;
            }
        }
    }

    private boolean buildRoadOnLand()
    {
        try
        {
            //without money
            VertexLocation settlement = new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast);
            //model.buildSettlement(player1, settlement, )
            EdgeLocation landOnly = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North);
            model.buildRoad(player1.getPlayerIndex(), landOnly, false);
            assertNotNull(model.getMap().getRoads().get(landOnly));
            model.getMap().getRoads().put(landOnly,null);
            //with money
            player1.getBank().setPlayerResources(new Resources(2,2,2,2,2));
            model.buildRoad(player1.getPlayerIndex(), landOnly, true);
            assertNotNull(model.getMap().getRoads().get(landOnly));
            model.getMap().getRoads().put(landOnly,null);
            return true;
        }
        catch ( CatanException e )
        {
            return false;
        }
    }

    @Test public void testBuildSettlement()
    {

    }

    @Test public void testBuildCity()
    {

    }

    @Test public void testSendChat()
    {

    }

    @Test public void testRollNumber()
    {

    }

    @Test public void testRobPlayer()
    {

    }

    @Test public void testFinishTurn()
    {

    }

    @Test public void testBuyDevCard()
    {

    }

    @Test public void testYearOfPlenty()
    {

    }

    @Test public void testRoadBuilding()
    {

    }

    @Test public void testSoldier()
    {

    }

    @Test public void testMonopoly()
    {

    }

    @Test public void testMonument()
    {

    }

    @Test public void testOfferTrade()
    {

    }

    @Test public void testAcceptTrade()
    {

    }

    @Test public void testMaritimeTrade()
    {

    }

    @Test public void testDiscardCards()
    {

    }

    @Test public void testUpdateCurrentTurn()
    {

    }

    @Test public void testUpdateLongestRoad()
    {

    }

    @Test public void testUpdateLargestArmy()
    {

    }

    @Test public void testUpdateStatus()
    {

    }
}