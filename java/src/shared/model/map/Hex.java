package shared.model.map;

import client.data.PlayerInfo;
import shared.definitions.*;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;

/**
 * Represents a Hex tile on the map.
 */
public class Hex
{
    /**
     * @see shared.definitions.ResourceType
     */
    private HexLocation location;

    /**
     * @see shared.definitions.HexType
     */
    private HexType hexType;

    /**
     * @see shared.definitions.ResourceType
     */
    private ResourceType resourceType;

    /**
     * The number assigned to this tile. Range of number is [2-12],
     * representative of the possible rolls of 2 dices
     */
    private int numberTile; // Do we want this to be a separate class?

    /**
     * This is stored as true if the robber is on this hex.
     */
    private boolean robberPresent;
    
    /**
     * Method that indicates whether a player has the ability to place a settlement in a certain location on the map
     * @param player -- this will be the player placing the settlement
     * @param location -- this will be the location of the settlement; must ensure that this space on the map is empty
     * @throws PlacementException -- exception thrown if either of the two above statements cannot be true
     */
    public void canPlaceSettlement(PlayerInfo player, VertexLocation location) throws PlacementException
    {

    }

    /**
    Method that indicates whether a player has the ability to place a city in a certain location on the map
    * @param player -- this will be the player placing the city
    * @param location -- this will be the location of the city; must ensure that this space already has a settlement located their owned by this player
    * @throws PlacementException -- exception thrown if either of the two above statements cannot be true
    */
    public void canPlaceCity(PlayerInfo player, VertexLocation location) throws PlacementException
    {

    }

    /**
     * Method that indicates whether a player has the ability to place a city on a certain edge on the map
     * @param player -- this will be the player placing the road
     * @param location -- this will be the edge location where the road will be placed; must ensure this space is empty on the map
     * @throws PlacementException -- exception thrown if either of the two above statements cannot be true
     */
    public void canPlaceRoad(PlayerInfo player, EdgeLocation location) throws PlacementException
    {

    }

    public void setHasRobber(boolean newHasRobber){
        robberPresent = newHasRobber;
    }

    public boolean hasRobber()
    {
        return robberPresent;
    }
}
