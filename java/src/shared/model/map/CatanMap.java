package shared.model.map;

import java.util.*;

import shared.definitions.*;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.map.structure.*;

public class CatanMap
{
    private List<Hex> hexes;
    private List<Port> ports;
    private Map<EdgeLocation, Road> roads;
    private Map<VertexLocation, Structure> structures;
    private int radius;
    private HexLocation robberLocation;

    public void canPlaceSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {

    }

    public void canPlaceCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {

    }

    public void canPlaceRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {

    }
}
