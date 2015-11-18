/**
 *
 */
package shared.model.map;

import org.junit.Test;
import server.util.FileUtils;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.map.structure.MapStructure;
import shared.model.map.structure.Road;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author dtaylor
 */
public class CatanMapTest
{
    private static CatanMap catanMap;

    /**
     * Test method for {@link CatanMap#canPlaceSettlement(PlayerIndex, VertexLocation)}.
     */
    @Test public void testCanPlaceSettlement()
    {
        try
        {
            catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
            //vacant
            catanMap.setStructures(new HashMap<VertexLocation, MapStructure>());
            catanMap.placeRoad(PlayerIndex.PLAYER_1,
                    new EdgeLocation(new HexLocation(0, -1), EdgeDirection.North));
            assertTrue(catanMap.canPlaceSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast)));
            //Nearby settlement
            catanMap.placeSettlement(PlayerIndex.PLAYER_2,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthWest));
            assertFalse(catanMap.canPlaceSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast)));
            //existing settlement
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
            assertFalse(catanMap.canPlaceSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast)));

        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link shared.model.map.CatanMap#canPlaceCity(shared.definitions.PlayerIndex, shared.locations.VertexLocation)}.
     */
    @Test public void testCanPlaceCity()
    {
        try
        {
            catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //vacant
        catanMap.setStructures(new HashMap<VertexLocation, MapStructure>());
        assertFalse(catanMap.canPlaceCity(PlayerIndex.PLAYER_1,
                new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast)));
        //existing settlement
        try
        {
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast));
        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());

            assertTrue(catanMap.canPlaceCity(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast)));
            //existing city
            try
            {
                catanMap.placeCity(PlayerIndex.PLAYER_1,
                        new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast));
            } catch (PlacementException e2)
            {
                // TODO Auto-generated catch block
                fail(e.getMessage());
            }
            assertTrue(catanMap.canPlaceCity(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast)));
        }
    }

    /**
     * Test method for {@link CatanMap#canPlaceRoad(PlayerIndex, EdgeLocation, boolean)}.
     */
    @Test public void testCanPlaceRoad()
    {
        try
        {
            catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //vacant no settlement
        catanMap.setStructures(new HashMap<VertexLocation, MapStructure>());
        catanMap.setRoads(new HashMap<EdgeLocation, Road>());
        assertFalse(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1,
                new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast), false));
        //vacant and has settlement
        try
        {
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
        assertTrue(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1,
                new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast), false));
        //vacant and not his settlement
        try
        {
            catanMap.placeSettlement(PlayerIndex.PLAYER_0,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
        assertFalse(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1,
                new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast), false));
        //existing road
        try
        {
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.NorthEast));
        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
        try
        {
            catanMap.placeRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast));
        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
        assertFalse(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1,
                new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast), false));
    }

    /**
     * Test method for {@link CatanMap#canMoveRobber(HexLocation)}.
     */
    @Test public void testCanMoveRobber()
    {
        try
        {
            catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //can move on sheep
        assertTrue(catanMap.canMoveRobber(new HexLocation(0, -2)));
        //can move on wood
        assertTrue(catanMap.canMoveRobber(new HexLocation(-1, -1)));
        //can move on wheat
        assertTrue(catanMap.canMoveRobber(new HexLocation(2, -1)));
        //can move on ore
        assertTrue(catanMap.canMoveRobber(new HexLocation(-2, 0)));
        //can move on desert
        assertTrue(catanMap.canMoveRobber(new HexLocation(-1, 2)));
        //can't move on port
        assertFalse(catanMap.canMoveRobber(new HexLocation(-2, 3)));
        //can't move on same hex
        assertFalse(catanMap.canMoveRobber(new HexLocation(-1, 0)));
    }

    @Test public void testPlaceRoad()
    {
        try
        {
            catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
            catanMap.placeRoad(PlayerIndex.PLAYER_0, new EdgeLocation(new HexLocation(0, -1), EdgeDirection.SouthEast));
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test public void testPlaceSettlement()
    {
        try
        {
            catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
            catanMap.placeSettlement(PlayerIndex.PLAYER_0,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast));
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test public void testPlaceCity()
    {
        try
        {
            catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
            catanMap.placeCity(PlayerIndex.PLAYER_0,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast));
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test public void testMoveRobber()
    {
        try
        {
            catanMap = new CatanMap(new String(FileUtils.readFile("sample/complexMapModel.json")));
            catanMap.setRobberLocation(new HexLocation(0, -1));
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test public void testSerializeDeserialize()
    {
        String json = null;
        try
        {
            json = new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json")));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CatanMap map1 = new CatanMap(json);
        CatanMap map2 = new CatanMap(map1.toString());
        boolean test1 = map1.equals(map2);
        boolean test2 = map2.equals(map1);
        assertTrue(test1);
        assertTrue(test2);
    }

}
