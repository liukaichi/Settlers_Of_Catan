package shared.model.map;

import java.util.*;

import shared.definitions.*;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.map.structure.*;

/**
 * Represents the board game map of the Catan game
 * @author amandafisher
 *
 */
public class CatanMap
{
    private List<Hex> hexes;
    private List<Port> ports;
    private Map<EdgeLocation, Road> roads;
    private Map<VertexLocation, Structure> structures;
    private int radius;
    private HexLocation robberLocation;

    /**
     * Method that indicates whether a player has the ability to place a settlement in a certain location on the map
     * @param player -- this will be the player placing the settlement
     * @param location -- this will be the location of the settlement; must ensure that this space on the map is empty
     * @throws PlacementException -- exception thrown if either of the two above statements cannot be true
     */
    public void canPlaceSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {

    }

    /**
     Method that indicates whether a player has the ability to place a city in a certain location on the map
     * @param player -- this will be the player placing the city
     * @param location -- this will be the location of the city; must ensure that this space already has a settlement located their owned by this player
     * @throws PlacementException -- exception thrown if either of the two above statements cannot be true
     */
    public void canPlaceCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {

    }

    /**
     * Method that indicates whether a player has the ability to place a city on a certain edge on the map
     * @param player -- this will be the player placing the road
     * @param location -- this will be the edge location where the road will be placed; must ensure this space is empty on the map
     * @throws PlacementException -- exception thrown if either of the two above statements cannot be true
     */
    public void canPlaceRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {

    }
}
