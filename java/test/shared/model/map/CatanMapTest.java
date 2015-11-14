/**
 *
 */
package shared.model.map;

import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.PlacementException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.map.structure.MapStructure;

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
     * Test method for {@link CatanMap#canPlaceSettlement(PlayerIndex, VertexLocation, boolean)}.
     */
    @Test public void testCanPlaceSettlement()
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
        assertTrue(catanMap.canPlaceSettlement(PlayerIndex.PLAYER_1,
                new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast), true));
        //Nearby settlement
        try
        {
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.East));
        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
        assertFalse(catanMap.canPlaceSettlement(PlayerIndex.PLAYER_1,
                new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast), true));
        //existing settlement
        try
        {
            catanMap.placeSettlement(PlayerIndex.PLAYER_1,
                    new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast));
        } catch (PlacementException e)
        {
            // TODO Auto-generated catch block
            fail(e.getMessage());
        }
        assertFalse(catanMap.canPlaceSettlement(PlayerIndex.PLAYER_1,
                new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthEast), true));
    }

    /**
     * Test method for {@link shared.model.map.CatanMap#canPlaceCity(shared.definitions.PlayerIndex, shared.locations.VertexLocation)}.
     */
    /*@Test
    public void testCanPlaceCity() {
		try {
			catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//vacant
		catanMap.setStructures(new HashMap<VertexLocation,MapStructure>());
		assertFalse(catanMap.canPlaceCity(PlayerIndex.PLAYER_1, new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthEast)));
		//existing settlement
		try {
			catanMap.placeSettlement(PlayerIndex.PLAYER_1, new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthEast));
		} catch (PlacementException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());

		assertTrue(catanMap.canPlaceCity(PlayerIndex.PLAYER_1, new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthEast)));
		//existing city
		try {
			catanMap.placeCity(PlayerIndex.PLAYER_1, new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthEast));
		} catch (PlacementException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		assertTrue(catanMap.canPlaceCity(PlayerIndex.PLAYER_1, new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthEast)));
	}*/

    /**
     * Test method for {@link CatanMap#canPlaceRoad(PlayerIndex, EdgeLocation, boolean)}.
     */
    /*@Test
    public void testCanPlaceRoad() {
		try {
			catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//vacant no settlement
		catanMap.setStructures(new HashMap<VertexLocation,MapStructure>());
		catanMap.setRoads(new HashMap<EdgeLocation,Road>());
		assertFalse(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0,-1), EdgeDirection.SouthEast),
				allowDisconnected));
		//vacant and has settlement
		try {
			catanMap.placeSettlement(PlayerIndex.PLAYER_1, new VertexLocation(new HexLocation(0,-1), VertexDirection.East));
		} catch (PlacementException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		assertTrue(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0,-1), EdgeDirection.SouthEast),
				allowDisconnected));
		//vacant and not his settlement
		try {
			catanMap.placeSettlement(PlayerIndex.PLAYER_0, new VertexLocation(new HexLocation(0,-1), VertexDirection.East));
		} catch (PlacementException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		assertFalse(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0,-1), EdgeDirection.SouthEast),
				allowDisconnected));
		//existing road
		try {
			catanMap.placeSettlement(PlayerIndex.PLAYER_1, new VertexLocation(new HexLocation(0,-1), VertexDirection.East));
		} catch (PlacementException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			catanMap.placeRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0,-1), EdgeDirection.SouthEast));
		} catch (PlacementException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		assertFalse(catanMap.canPlaceRoad(PlayerIndex.PLAYER_1, new EdgeLocation(new HexLocation(0,-1), EdgeDirection.SouthEast),
				allowDisconnected));
	}*/

    /**
     * Test method for {@link shared.model.map.CatanMap#canMoveRobber(shared.definitions.PlayerIndex, shared.locations.HexLocation)}.
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
        assertTrue(catanMap.canMoveRobber(PlayerIndex.PLAYER_1, new HexLocation(0, -2)));
        //can move on wood
        assertTrue(catanMap.canMoveRobber(PlayerIndex.PLAYER_1, new HexLocation(-1, -1)));
        //can move on wheat
        assertTrue(catanMap.canMoveRobber(PlayerIndex.PLAYER_1, new HexLocation(2, -1)));
        //can move on ore
        assertTrue(catanMap.canMoveRobber(PlayerIndex.PLAYER_1, new HexLocation(-2, 0)));
        //can move on desert
        assertTrue(catanMap.canMoveRobber(PlayerIndex.PLAYER_1, new HexLocation(-1, 2)));
        //can't move on port
        assertFalse(catanMap.canMoveRobber(PlayerIndex.PLAYER_1, new HexLocation(-2, 3)));
        //can't move on same hex
        assertFalse(catanMap.canMoveRobber(PlayerIndex.PLAYER_1, new HexLocation(-1, 0)));
    }

	/*@Test
	public void testPlaceRoad() {
		try {
			catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			catanMap.placeRoad(PlayerIndex.PLAYER_0, new EdgeLocation(new HexLocation(0,-1), EdgeDirection.SouthEast));
		} catch (PlacementException e) {
			//passed
		}
	}*/

	/*@Test
	public void testPlaceSettlement() {
		try {
			catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			catanMap.placeSettlement(PlayerIndex.PLAYER_0, new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthEast));
		} catch (PlacementException e) {
			//passed
		}
	}*/

	/*@Test
	public void testPlaceCity() {
		try {
			catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			catanMap.placeCity(PlayerIndex.PLAYER_0, new VertexLocation(new HexLocation(0,-1), VertexDirection.SouthEast));
		} catch (PlacementException e) {
			//passed
		}
	}*/

	/*@Test
	public void testMoveRobber() {
		try {
			catanMap = new CatanMap(new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			catanMap.moveRobber(PlayerIndex.PLAYER_0, new HexLocation(0,-1));
		} catch (PlacementException e) {
			//passed
		}
	}*/

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
