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

/**
 * Represents the board game map of the Catan game
 * 
 * @author davidtaylor
 *
 */
public class CatanMap
{
    private Map<HexLocation, Hex> hexes;
    private List<Port> ports;
    private Map<EdgeLocation, Road> roads;
    private Map<VertexLocation, Structure> structures;
    private int radius;
    private HexLocation robberLocation;

    public CatanMap()
    {
    	
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
    	Structure atLocation = structures.get(location);
    	//check if location exists, and is empty
        if(structures.containsKey(location) && atLocation == null)
        {
        	for(VertexLocation vertex : location.getNeighboringVertices())
        	{
        		if(structures.get(vertex) != null)
    			{
    				return false;
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
    	if(roads.get(location) == null)
    	{
    		for(VertexLocation vertex : location.getVertices())
            {
    	    	Structure structure = structures.get(vertex);
    			if(structure != null && structure.getOwner().equals(player))
		        {
		        	return true;
		        }
            }
    		return false;
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
}
