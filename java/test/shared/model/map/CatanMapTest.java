/**
 * 
 */
package shared.model.map;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import shared.definitions.PlayerIndex;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.map.structure.Structure;

/**
 * @author dtaylor
 *
 */
public class CatanMapTest {


	/**
	 * Test method for {@link shared.model.map.CatanMap#CatanMap()}.
	 */
	@Test
	public void testCatanMap() {
	    String json = null;
        try
        {
            json = new String(Files.readAllBytes(Paths.get("sample/complexMapModel.json")));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		CatanMap map = new CatanMap(json);
		JsonParser parser = new JsonParser();
		JsonObject obj1 = (JsonObject)parser.parse(map.toString());
        JsonObject obj2 = (JsonObject)parser.parse(json);
		boolean same = obj1.equals(obj2);
		assertTrue(same);
		
	}

	/**
	 * Test method for {@link shared.model.map.CatanMap#canPlaceSettlement(shared.definitions.PlayerIndex, shared.locations.VertexLocation)}.
	 */
	@Test
	public void testCanPlaceSettlement() {
		testCanPlaceSettlementEmptySet();
		testCanPlaceSettlementVacantVertexStructureNearBy();
	}
	
	@Test
	public void testSerializeClassToJson()
	{
		CatanMap map = new CatanMap();
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
	}
	
	/**
	 * 
	 */
	private void testCanPlaceSettlementVacantVertexStructureNearBy() {
		CatanMap map = new CatanMap();
		HashMap<HexLocation, Hex> hexMap = new HashMap<HexLocation, Hex>();
		hexMap.put(new HexLocation(0,0), new Hex());
		
		HashMap<VertexLocation, Structure> structureMap = new HashMap<VertexLocation, Structure>();
		map.setHexes(hexMap);
		map.setStructures(structureMap);
		assertFalse(map.canPlaceSettlement(PlayerIndex.PLAYER_0, new VertexLocation(new HexLocation(0,0),VertexDirection.West)));
	}

	
	public void testCanPlaceSettlementEmptySet() {
		CatanMap map = new CatanMap();
		HashMap<HexLocation, Hex> hexMap = new HashMap<HexLocation, Hex>();
		HashMap<VertexLocation, Structure> structureMap = new HashMap<VertexLocation, Structure>();
		map.setHexes(hexMap);
		map.setStructures(structureMap);
		//testCanPlaceSettlementEmptySets(map, hexMap, structureMap);
		assertFalse(map.canPlaceSettlement(PlayerIndex.PLAYER_0, new VertexLocation(new HexLocation(0,0),VertexDirection.West)));
	}


	/**
	 * Test method for {@link shared.model.map.CatanMap#canPlaceCity(shared.definitions.PlayerIndex, shared.locations.VertexLocation)}.
	 */
	@Test
	public void testCanPlaceCity() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link shared.model.map.CatanMap#canPlaceRoad(shared.definitions.PlayerIndex, shared.locations.EdgeLocation)}.
	 */
	@Test
	public void testCanPlaceRoad() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link shared.model.map.CatanMap#canMoveRobber(shared.definitions.PlayerIndex, shared.locations.HexLocation)}.
	 */
	@Test
	public void testCanMoveRobber() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link shared.model.map.CatanMap#placeRoad(shared.definitions.PlayerIndex, shared.locations.EdgeLocation)}.
	 */
	@Test
	public void testPlaceRoad() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link shared.model.map.CatanMap#placeSettlement(shared.definitions.PlayerIndex, shared.locations.VertexLocation)}.
	 */
	@Test
	public void testPlaceSettlement() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link shared.model.map.CatanMap#placeCity(shared.definitions.PlayerIndex, shared.locations.VertexLocation)}.
	 */
	@Test
	public void testPlaceCity() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link shared.model.map.CatanMap#moveRobber(shared.definitions.PlayerIndex, shared.locations.HexLocation)}.
	 */
	@Test
	public void testMoveRobber() {
		fail("Not yet implemented"); // TODO
	}

}
