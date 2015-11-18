package server;

import client.base.IAction;
import client.data.PlayerInfo;
import org.junit.Before;
import org.junit.Test;
import server.util.FileUtils;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.bank.resource.Resources;
import shared.model.map.CatanMap;
import shared.model.map.structure.MapStructure;
import shared.model.map.structure.Road;
import shared.model.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

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
        player1 = new Player(new PlayerInfo(0, "player1", CatanColor.BLUE));
        player2 = new Player(new PlayerInfo(1, "player2", CatanColor.BLUE));
    }

    @Test public void testBuildRoad()
    {
        try
        {
            CatanMap catanMap = model.getMap();
            //vacant no settlement
            catanMap.setStructures(new HashMap<>());
            catanMap.setRoads(new HashMap<>());
            try
            {
                model.buildRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast),
                        false);
            } catch (CatanException e)
            {
                fail(e.getMessage());
            }
            //vacant and has settlement
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
            try
            {
                model.buildRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast),
                        false);
            } catch (CatanException e)
            {
                fail(e.getMessage());
            }
            //vacant and not his settlement
            catanMap.placeSettlement(PlayerIndex.PLAYER_0,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
            try
            {
                model.buildRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast),
                        false);
            } catch (CatanException e)
            {
                fail(e.getMessage());
            }

            //existing road
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
            catanMap.placeRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast));
            try
            {
                model.buildRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast),
                        false);
            } catch (CatanException e)
            {
                e.printStackTrace();
            }

        } catch (PlacementException e)
        {
            fail(e.getMessage());
        }
    }

    @Test public void testBuildSettlement()
    {
        try
        {
            CatanMap catanMap = model.getMap();
            //vacant
            catanMap.setStructures(new HashMap<VertexLocation, MapStructure>());
            try
            {
                model.buildRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.North),
                        true);
                model.getPlayerByIndex(PlayerIndex.PLAYER_1).getBank().setPlayerResources(new Resources(3, 3, 3, 3, 3));
                model.buildSettlement(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast), false);
            } catch (CatanException e)
            {
                fail(e.getMessage());
            }
            //Nearby settlement
            catanMap.placeSettlement(PlayerIndex.PLAYER_2,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthWest));
            try
            {
                model.buildSettlement(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast),false);
                fail("should fail with nearby settlement");
            } catch (CatanException e)
            {
                //should fail with nearby settlement
            }
            //existing settlement
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
            try
            {
                model.buildSettlement(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast),false);
                fail("should fail with nearby settlement");
            } catch (CatanException e)
            {
                //should fail
            }

        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
    }

    @Test public void testBuildCity()
    {
        try
        {
            CatanMap catanMap = model.getMap();
            //vacant
            catanMap.setStructures(new HashMap<>());
            catanMap.setRoads(new HashMap<>());

            try
            {
                model.buildCity(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
                fail("shouldn't be able to build city without settlement");
            } catch (CatanException e)
            {
                //good
            }

            //existing settlement
            try
            {
                model.buildRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast),
                        true);
                model.buildSettlement(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast), true);
                model.getPlayerByIndex(PlayerIndex.PLAYER_1).getBank().setPlayerResources(new Resources(3, 3, 3, 3, 3));
                model.buildCity(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
            } catch (CatanException e)
            {
                fail(e.getMessage());
            }
            //existing city
            catanMap.placeCity(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
            try
            {
                player1.getBank().setPlayerResources(new Resources(3, 3, 3, 3, 3));
                model.buildCity(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
                fail("can't place a city on a city");
            } catch (CatanException e)
            {
                //good
            }
        } catch (CatanException e)
        {
            fail(e.getMessage());
        }
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