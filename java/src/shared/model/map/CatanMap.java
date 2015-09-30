package shared.model.map;

import java.util.*;

import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.map.structure.*;
import shared.locations.*;
import shared.model.map.structure.*;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * Represents the board game map of the Catan game
 * 
 * @author davidtaylor
 *
 */
public class CatanMap
{
	//populated on map initialization
    private List<Port> ports;
    private Map<HexLocation, Hex> hexes;
    private Map<EdgeLocation, List<VertexLocation> > edgeVertices;
    private Map<VertexLocation, List<EdgeLocation> > vertexEdges;
    //populated on buy
    private Map<EdgeLocation, Road> roads;
    private Map<VertexLocation, Structure> structures;
    private int radius;
    private HexLocation robberLocation;

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	JsonParser parser = new JsonParser();
    	//map

    	JsonObject obj = new JsonObject();
    	{
	    	JsonObject map = new JsonObject();
	    	{
	    		//hexGrid
	        	JsonObject hexGrid = new JsonObject();
	        	{
	        		//hexes
	        		JsonArray hexes = new JsonArray();
	            	{
	            		for(Hex hex : this.hexes.values())
	            		{
	            			hexes.add(parser.parse(hex.toString()));
	            		}
	            	}
	            	hexGrid.add("hexes", hexes);
	            	//offsets
	            	JsonArray offsets = new JsonArray();
	            	{
	            		offsets.add(new JsonPrimitive(1));
	            		offsets.add(new JsonPrimitive(0));
	            		offsets.add(new JsonPrimitive(0));
	            	}
	            	hexGrid.add("offsets", offsets);
	            	//radius
	            	JsonPrimitive radius = new JsonPrimitive(this.radius);
	            	hexGrid.add("radius", radius);
	            	//x0
	            	JsonPrimitive x0 = new JsonPrimitive(1);
	            	hexGrid.add("x0", x0);
	            	//y0
	            	JsonPrimitive y0 = new JsonPrimitive(1);
	            	hexGrid.add("y0", y0);
	        	}
	        	//radius
	        	JsonPrimitive radius = new JsonPrimitive(this.radius);
	        	map.add("radius", radius);
	        	//numbers
	        	JsonObject numbers = new JsonObject();
	        	{
	            	numbers.add(property, value);
	        	}
	        	map.add("numbers", numbers);
	        	//ports
	        	JsonObject ports = new JsonObject();
	        	{
	        		
	        	}
	        	map.add("ports", ports);
	    	}
	    	obj.add("map", map);
    	}
    	return obj.toString();
    }
    
public CatanMap() {
	ports = new ArrayList<Port>();
	hexes = new HashMap<HexLocation, Hex>();
	edgeVertices = new HashMap<EdgeLocation, List<VertexLocation> >();
	vertexEdges = new HashMap<VertexLocation, List<EdgeLocation> >();
	roads = new HashMap<EdgeLocation, Road>();
	structures = new HashMap<VertexLocation, Structure>();
	radius = 2;
	robberLocation = new HexLocation(0,0);
}
    /**
	 * @param ports
	 * @param hexes
	 * @param edgeVertices
	 * @param vertexEdges
	 * @param roads
	 * @param structures
	 * @param radius
	 * @param robberLocation
	 */
	public CatanMap(List<Port> ports, Map<HexLocation, Hex> hexes, Map<EdgeLocation, List<VertexLocation>> edgeVertices,
			Map<VertexLocation, List<EdgeLocation>> vertexEdges, Map<EdgeLocation, Road> roads,
			Map<VertexLocation, Structure> structures, int radius, HexLocation robberLocation) {
		super();
		this.ports = ports;
		this.hexes = hexes;
		this.edgeVertices = edgeVertices;
		this.vertexEdges = vertexEdges;
		this.roads = roads;
		this.structures = structures;
		this.radius = radius;
		this.robberLocation = robberLocation;
	}

	/**
     * Method that indicates whether a player has the ability to place a
     * settlement in a certain location on the map
     * 
     * @param player
     *        -- this will be the player placing the settlement
     * @param location
     *        -- this will be the location of the settlement; must ensure that
     *        this space on the map is empty
     * @return boolean -- returns true if the location is vacant and at least
     *         two spaces away from another settlement otherwise returns false
     */
    public boolean canPlaceSettlement(PlayerIndex player, VertexLocation location)
    {
    	VertexLocation normalizedVertex = location.getNormalizedLocation();
    	Structure atLocation = structures.get(normalizedVertex);
    	//check if location exists, and is empty
        if(structures.containsKey(normalizedVertex) && atLocation == null)
        {
        	for(EdgeLocation edge : vertexEdges.get(normalizedVertex))
        	{
        		for(VertexLocation vertex : edgeVertices.get(edge))
        		{
        			if(structures.get(vertex) != null)
        			{
        				return false;
        			}
        		}
        		
        	}
        	return true;
        }
        else
        {
        	return false;
        }
        
    }

    /**
     * Method that indicates whether a player has the ability to place a city in
     * a certain location on the map
     * 
     * @param player
     *        -- this will be the player placing the city
     * @param location
     *        -- this will be the location of the city; must ensure that this
     *        space already has a settlement located their owned by this player
     * @return boolean -- returns true if there is a settlement at the specified
     *         location and it is owned by the player otherwise returns false
     */
    public boolean canPlaceCity(PlayerIndex player, VertexLocation location)
    {
    	Structure structure = structures.get(location);
        if(structure != null && structure.getOwner().equals(player))
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }

    /**
     * Method that indicates whether a player has the ability to place a city on
     * a certain edge on the map
     * 
     * @param player
     *        -- this will be the player placing the road
     * @param location
     *        -- this will be the edge location where the road will be placed;
     *        must ensure this space is empty on the map
     * @return boolean -- returns true if the player owns a settlement or city
     *         at the neighboring vertex locations and there is no current road
     *         there otherwise returns false
     */
    public boolean canPlaceRoad(PlayerIndex player, EdgeLocation location)
    {
    	EdgeLocation normalizedEdge = location.getNormalizedLocation();
    	Structure atLocation = structures.get(normalizedEdge);
    	//check if location exists, and is empty
        if(roads.containsKey(normalizedEdge) && atLocation == null)
        {
        	for(VertexLocation vertex : edgeVertices.get(normalizedEdge))
        	{
        		for(EdgeLocation edge : vertexEdges.get(vertex))
        		{
        			if(roads.get(edge) != null)
        			{
        				return false;
        			}
        		}
        		
        	}
        	return true;
        }
        else
        {
        	return false;
        }
        
    }

    /**
     * Method that indicates whether a player has the ability to move a robber
     * on a certain Hex
     * 
     * @param player
     *        -- this will be the player placing the robber
     * @param location
     *        -- this will be the hex location where the robber will be placed;
     *        cannot place on water or where the robber already is
     * @return boolean -- returns true if it is not moving to its current
     *         location and it is not a sea piece otherwise returns false
     */
    public boolean canMoveRobber(PlayerIndex player, HexLocation location)
    {
        if(hexes.get(location) != null && !robberLocation.equals(location))
        {
        	return true;
        }
        return false;
    }

    /**
     * Method that places a road on the map
     * @param player
     *        -- this will be the player placing the road
     * @param location
     * 		  -- this will be the hex location where the road will be placed
     * @throws PlacementException
     */
    public void placeRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {
    	try
    	{
    		Road road = new Road(player, location);
    		roads.put(location, road);
    	} 
    	catch(Exception e)
    	{
    		throw new PlacementException();
    	}
    	
    }

    /**
     * Method that places a settlement on the map
     * @param player
     * 		  -- this will be the player placing the settlement
     * @param location
     * 		  -- this will be the vertex location where the settlement will be placed
     * @throws PlacementException
     */
    public void placeSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {
    	try
    	{
    		Settlement settlement = new Settlement(player, location);
    		structures.put(location, settlement);
    	} 
    	catch(Exception e)
    	{
    		throw new PlacementException();
    	}
    }

    /**
     * Method that places a city on the map
     * @param player
     * 		  -- this will be the player placing the city
     * @param location
     * 		  -- this will be the vertex location where the city will be placed
     * @throws PlacementException
     */
    public void placeCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {
    	try
    	{
    		City city = new City(player, location);
    		structures.put(location, city);
    	} 
    	catch(Exception e)
    	{
    		throw new PlacementException();
    	}
    }

    /**
     * Method that moves the robber on the map
     * @param player
     * 		  -- this will be the player moving the robber
     * @param location
     * 		  -- this will be the hex location where the robber will be placed
     * @throws PlacementException
     */
    public void moveRobber(PlayerIndex player, HexLocation location) throws PlacementException
    {
    	try
    	{
        	this.robberLocation = location;
    	}
    	catch(Exception e)
    	{
    		throw new PlacementException();
    	}
    }
	public void setHexes(Map<HexLocation, Hex> hexes) {
		this.hexes = hexes;
	}
	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}
	public void setRoads(Map<EdgeLocation, Road> roads) {
		this.roads = roads;
	}
	public void setStructures(Map<VertexLocation, Structure> structures) {
		this.structures = structures;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public void setRobberLocation(HexLocation robberLocation) {
		this.robberLocation = robberLocation;
	}
}
