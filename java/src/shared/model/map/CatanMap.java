package shared.model.map;

import com.google.gson.*;
import shared.definitions.HexType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.map.structure.*;

import java.util.*;

/**
 * Represents the board game map of the Catan game
 *
 * @author davidtaylor
 */
public class CatanMap
{
    // populated on map initialization
    private List<Port> ports = new ArrayList<Port>();
    private Map<HexLocation, Hex> hexes = new HashMap<HexLocation, Hex>();
    // populated on buy
    private Map<EdgeLocation, Road> roads = new HashMap<EdgeLocation, Road>();
    private Map<VertexLocation, MapStructure> structures = new HashMap<VertexLocation, MapStructure>();

    public List<Port> getPorts()
    {
        return ports;
    }

    public Map<VertexLocation, MapStructure> getStructures()
    {
        return structures;
    }

    public int getRadius()
    {
        return radius;
    }

    public HexLocation getRobberLocation()
    {
        return robberLocation;
    }

    private int radius;
    private HexLocation robberLocation;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public CatanMap(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject map = (JsonObject) parser.parse(json);
        JsonArray hexes = map.getAsJsonArray("hexes");
        populateWaterTiles();
        for (JsonElement elem : hexes)
        {
            JsonObject hexObj = (JsonObject) elem;
            Hex hex = new Hex(hexObj.toString());
            this.hexes.put(hex.getLocation(), hex);
        }
        JsonArray roads = map.getAsJsonArray("roads");
        for (JsonElement elem : roads)
        {
            JsonObject roadObj = (JsonObject) elem;
            Road road = new Road(roadObj.toString());
            this.roads.put(road.getLocation().getNormalizedLocation(), road);
        }
        JsonArray cities = map.getAsJsonArray("cities");
        for (JsonElement elem : cities)
        {
            JsonObject roadObj = (JsonObject) elem;
            City city = new City(roadObj.toString());
            this.structures.put(city.getLocation().getNormalizedLocation(), city);
        }
        JsonArray settlements = map.getAsJsonArray("settlements");
        for (JsonElement elem : settlements)
        {
            JsonObject settlementObj = (JsonObject) elem;
            Settlement settlement = new Settlement(settlementObj.toString());
            this.structures.put(settlement.getLocation().getNormalizedLocation(), settlement);
        }
        this.radius = map.get("radius").getAsInt();
        JsonArray portsArray = map.getAsJsonArray("ports");
        this.ports = new ArrayList<Port>();
        for (JsonElement elem : portsArray)
        {
            JsonObject port = (JsonObject) elem;
            this.ports.add(new Port(port.toString()));
        }
        JsonObject robber = map.getAsJsonObject("robber");
        this.hexes.get(new HexLocation(robber.get("x").getAsInt(), robber.get("y").getAsInt())).setHasRobber(true);
        this.robberLocation = new HexLocation(robber.get("x").getAsInt(), robber.get("y").getAsInt());
    }

    /**
     * Initializes all of the Hexes to be water. The land tiles will replace the water tiles as necessary.
     */
    private void populateWaterTiles()
    {

        for (int x = 0; x <= 3; ++x)
        {

            int maxY = 3 - x;
            for (int y = -3; y <= maxY; ++y)
            {
                HexType hexType = HexType.WATER;
                HexLocation hexLoc = new HexLocation(x, y);
                Hex hex = new Hex(hexLoc, hexType);
                this.hexes.put(hexLoc, hex);
            }

            if (x != 0)
            {
                int minY = x - 3;
                for (int y = minY; y <= 3; ++y)
                {
                    HexType hexType = HexType.WATER;
                    HexLocation hexLoc = new HexLocation(-x, y);
                    Hex hex = new Hex(hexLoc, hexType);
                    this.hexes.put(hexLoc, hex);
                }
            }
        }
    }

    @Override public String toString()
    {
        JsonParser parser = new JsonParser();
        // map
        JsonObject map = new JsonObject();
        {
            // hexes
            JsonArray hexes = new JsonArray();
            {
                for (Hex hex : this.hexes.values())
                {
                    hexes.add(parser.parse(hex.toString()));
                }
            }
            map.add("hexes", hexes);
            // roads
            JsonArray roads = new JsonArray();
            {
                for (Road road : this.roads.values())
                {
                    roads.add(parser.parse(road.toString()));
                }
            }
            map.add("roads", roads);
            // cities
            JsonArray cities = new JsonArray();
            {
                for (MapStructure city : this.structures.values())
                {
                    if (city.getClass().equals(City.class))
                        cities.add(parser.parse(city.toString()));
                }
            }
            map.add("cities", cities);
            // settlements
            JsonArray settlements = new JsonArray();
            {
                for (MapStructure settlement : this.structures.values())
                {
                    if (settlement.getClass().equals(Settlement.class))
                        settlements.add(parser.parse(settlement.toString()));
                }
            }
            map.add("settlements", settlements);
            // radius
            JsonPrimitive radius = new JsonPrimitive(this.radius);
            map.add("radius", radius);
            // ports
            JsonArray ports = new JsonArray();
            {
                for (Port port : this.ports)
                {
                    ports.add(parser.parse(port.toString()));
                }
            }
            map.add("ports", ports);
            // robber
            JsonObject robber = new JsonObject();
            robber.addProperty("x", robberLocation.getX());
            robber.addProperty("y", robberLocation.getY());
            map.add("robber", robber);
        }
        return map.toString();
    }

    public CatanMap()
    {
        ports = new ArrayList<Port>();
        hexes = new HashMap<HexLocation, Hex>();
        roads = new HashMap<EdgeLocation, Road>();
        structures = new HashMap<VertexLocation, MapStructure>();
        radius = -1;
        robberLocation = new HexLocation(0, 0);
    }

    /**
     * @param ports
     * @param hexes
     * @param roads
     * @param structures
     * @param radius
     * @param robberLocation
     */
    public CatanMap(List<Port> ports, Map<HexLocation, Hex> hexes, Map<EdgeLocation, Road> roads,
            Map<VertexLocation, MapStructure> structures, int radius, HexLocation robberLocation)
    {
        super();
        this.ports = ports;
        this.hexes = hexes;
        this.roads = roads;
        this.structures = structures;
        this.radius = radius;
        this.robberLocation = robberLocation;
    }

    /**
     * Method that indicates whether a player has the ability to place a
     * settlement in a certain location on the map
     *
     * @param player            -- this will be the player placing the settlement
     * @param location          -- this will be the location of the settlement; must ensure that
     *                          this space on the map is empty
     * @param allowDisconnected
     * @return boolean -- returns true if the location is vacant and at least
     * two spaces away from another settlement otherwise returns false
     */
    public boolean canPlaceSettlement(PlayerIndex player, VertexLocation location, boolean allowDisconnected)
    {
        HexLocation hexLocation = location.getHexLoc();
        if (Math.abs(hexLocation.getY()) <= radius && Math.abs(hexLocation.getX()) <= radius)
        {
            MapStructure atLocation = structures.get(location);
            // check if location exists, and is empty
            if (atLocation == null)
            {
                //check structures is 2 roads a way
                List<VertexLocation> vertices = getNearbyVertices(location);
                for (VertexLocation vertex : vertices)
                {
                    if (structures.get(vertex) != null)
                    {
                        return false;
                    }
                }
                //check if settlement is connected to player's road.
                List<EdgeLocation> edges = getNearbyEdges(location);
                for (EdgeLocation edge : edges)
                {
                    Road road = roads.get(edge);
                    if (road != null)
                    {
                        if (road.getOwner().equals(player))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    private List<EdgeLocation> getNearbyEdges(VertexLocation normalized)
    {
        ArrayList<EdgeLocation> edges = new ArrayList<>();
        if (normalized.getDir().equals(VertexDirection.NorthEast))
        {
            Hex hex = hexes.get(normalized.getHexLoc());
            if (hex != null)
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.North));
                edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
                hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthEast));
            }
            if (hex != null)
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.NorthWest));
            }
        } else if (normalized.getDir().equals(VertexDirection.NorthWest))
        {
            Hex hex = hexes.get(normalized.getHexLoc());
            if (hex != null)
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.North));
                edges.add(hex.getEdgeLocation(EdgeDirection.NorthWest));

                hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthWest));
            }
            if (hex != null)
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
            }
        }
        return edges;
    }

    /**
     * Method that indicates whether a player has the ability to place a city in
     * a certain location on the map
     *
     * @param player   -- this will be the player placing the city
     * @param location -- this will be the location of the city; must ensure that this
     *                 space already has a settlement located their owned by this player
     * @return boolean -- returns true if there is a settlement at the specified
     * location and it is owned by the player otherwise returns false
     */
    public boolean canPlaceCity(PlayerIndex player, VertexLocation location)
    {
        HexLocation hexLocation = location.getHexLoc();
        if (Math.abs(hexLocation.getY()) <= radius && Math.abs(hexLocation.getX()) <= radius)
        {
            MapStructure mapStructure = structures.get(location);
            if (mapStructure != null && mapStructure.getOwner().getIndex() == player.getIndex())
            {
                if (mapStructure instanceof Settlement)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that indicates whether a player has the ability to place a city on
     * a certain edge on the map
     *
     * @param player            -- this will be the player placing the road
     * @param location          -- this will be the edge location where the road will be placed;
     *                          must ensure this space is empty on the map
     * @param allowDisconnected
     * @return boolean -- returns true if the player owns a settlement or city
     * at the neighboring vertex locations and there is no current road
     * there otherwise returns false
     */
    public boolean canPlaceRoad(PlayerIndex player, EdgeLocation location, boolean allowDisconnected)
    {
        HexLocation hexLocation = location.getHexLoc();
        if (Math.abs(hexLocation.getY()) <= radius && Math.abs(hexLocation.getX()) <= radius)
        {
            EdgeLocation normalizedEdge = location;
            Road atLocation = roads.get(normalizedEdge);
            // check if location exists, and is empty
            if (atLocation == null)
            {
                //check valid hex
                Hex hex = hexes.get(hexLocation);
                if (hex != null && hex.getHexType().equals(HexType.WATER))
                {
                    EdgeDirection dir = location.getDir();
                    Hex opposite = hexes.get(hexLocation.getNeighborLoc(dir));
                    if (opposite != null && opposite.getHexType().equals(HexType.WATER))
                    {
                        return false;
                    }

                }
                //check nearby vertices
                List<VertexLocation> vertices = getNearbyVertices(normalizedEdge);
                for (VertexLocation vertex : vertices)
                {
                    MapStructure mapStructure = structures.get(vertex);
                    if (mapStructure != null)
                    {
                        if (mapStructure.getOwner().getIndex() == player.getIndex())
                        {
                            if (allowDisconnected)
                            {
                                return false;
                            }
                            {
                                return true;
                            }
                        }
                        else if (allowDisconnected)
                        {
                            return false;
                        }
                    }
                }
                if (!allowDisconnected)
                {
                    //check connecting roads
                    List<EdgeLocation> edges = getNearbyEdges(normalizedEdge);
                    for (EdgeLocation edge : edges)
                    {
                        Road road = roads.get(edge);
                        if (road != null && road.getOwner().equals(player))
                        {
                            //get the vertex between the two
                            for (VertexLocation vertex : getNearbyVertices(normalizedEdge))
                            {
                                if (getNearbyVertices(edge).contains(vertex) && structures.get(vertex) != null)
                                {
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                } else
                {
                    return true;
                }

            }
        }
        return false;

    }

    /**
     * Method that indicates whether a player has the ability to move a robber
     * on a certain Hex
     *
     * @param player   -- this will be the player placing the robber
     * @param location -- this will be the hex location where the robber will be placed;
     *                 cannot place on water or where the robber already is
     * @return boolean -- returns true if it is not moving to its current
     * location and it is not a sea piece otherwise returns false
     */
    public boolean canMoveRobber(PlayerIndex player, HexLocation location)
    {
        if (hexes.get(location) != null && !robberLocation.equals(location))
        {
            return true;
        }
        return false;
    }

    public void forcePlaceRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {
        Road road = new Road(player, location);
        roads.put(location, road);

    }

    public void forcePlaceSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {
        Settlement settlement = new Settlement(player, location);
        structures.put(location, settlement);
    }

    public void forcePlaceCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {
        City city = new City(player, location);
        structures.put(location, city);
    }

    public void setHexes(Map<HexLocation, Hex> hexes)
    {
        this.hexes = hexes;
    }

    public Map<HexLocation, Hex> getHexes()
    {
        return hexes;
    }

    public void setPorts(List<Port> ports)
    {
        this.ports = ports;
    }

    public void setRoads(Map<EdgeLocation, Road> roads)
    {
        this.roads = roads;
    }

    public void setStructures(Map<VertexLocation, MapStructure> structures)
    {
        this.structures = structures;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public void setRobberLocation(HexLocation robberLocation)
    {
        this.robberLocation = robberLocation;
    }

    /**
     * Gets vertices that are up to two vertices away.
     *
     * @param location the current vertex to find neighbors for.
     * @return a list a vertices up to two spaces away.
     */
    private List<VertexLocation> getNearbyVertices(VertexLocation location)
    {
        VertexLocation normalized = location;
        ArrayList<VertexLocation> vertices = new ArrayList<>();
        if (normalized.getDir().equals(VertexDirection.NorthEast))
        {
            Hex hex = hexes.get(normalized.getHexLoc());
            if (hex != null)
            {
                vertices.add(hex.getVertexLocation(VertexDirection.East));
                vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
                hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthEast));
            }
            if (hex != null)
            {
                vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
            }
        } else if (normalized.getDir().equals(VertexDirection.NorthWest))
        {
            Hex hex = hexes.get(normalized.getHexLoc());
            if (hex != null)
            {
                vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
                vertices.add(hex.getVertexLocation(VertexDirection.West));

                hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthWest));
            }
            if (hex != null)
            {
                vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
            }
        }
        return vertices;
    }

    /**
     * Gets the nearby vertices from an edge one distance away from the current road.
     *
     * @param location the current location of the edge.
     * @return a list of vertices one distance away.
     */
    private List<VertexLocation> getNearbyVertices(EdgeLocation location)
    {
        EdgeLocation normalized = location;
        ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
        Hex hex = hexes.get(normalized.getHexLoc());
        if (hex != null)
        {
            if (normalized.getDir().equals(EdgeDirection.NorthWest))
            {
                vertices.add(hex.getVertexLocation(VertexDirection.West));
                vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
            } else if (normalized.getDir().equals(EdgeDirection.North))
            {
                vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
                vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
            } else if (normalized.getDir().equals(EdgeDirection.NorthEast))
            {
                vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
                vertices.add(hex.getVertexLocation(VertexDirection.East));
            }
        }
        return vertices;
    }

    /**
     * Gets edges that are one edge distance away. Edges are added clockwise
     *
     * @param location the current location of the edge.
     * @return a list of edges one distance away.
     */
    private List<EdgeLocation> getNearbyEdges(EdgeLocation location)
    {
        EdgeLocation normalized = location;
        ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();

        Hex hex = hexes.get(normalized.getHexLoc());
        if (hex != null)
        {
            if (normalized.getDir().equals(EdgeDirection.NorthWest))
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.North));
                edges.add(hex.getEdgeLocation(EdgeDirection.SouthWest));

                hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthWest));
                if (hex != null)
                {
                    edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
                    edges.add(hex.getEdgeLocation(EdgeDirection.South));
                }

            } else if (normalized.getDir().equals(EdgeDirection.North))
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
                edges.add(hex.getEdgeLocation(EdgeDirection.NorthWest));

                hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.North));
                if (hex != null)
                {
                    edges.add(hex.getEdgeLocation(EdgeDirection.SouthEast));
                    edges.add(hex.getEdgeLocation(EdgeDirection.SouthWest));
                }

            } else if (normalized.getDir().equals(EdgeDirection.NorthEast))
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.North));
                edges.add(hex.getEdgeLocation(EdgeDirection.SouthEast));

                hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthEast));
                if (hex != null)
                {
                    edges.add(hex.getEdgeLocation(EdgeDirection.South));
                    edges.add(hex.getEdgeLocation(EdgeDirection.NorthWest));
                }
            }
        }
        return edges;
    }

    public Set<PlayerIndex> getHexPlayers(HexLocation hexLocation)
    {
        Set<PlayerIndex> players = new HashSet<>();
        for (VertexLocation location : hexes.get(hexLocation).getVertices())
        {

            MapStructure structure = structures.get(location);
            if (structure != null)
            {
                players.add(structure.getOwner());
            }
        }
        return players;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        CatanMap other = (CatanMap) obj;
        if (this.hexes.keySet().size() != other.hexes.keySet().size())
            return false;
        for (HexLocation key : this.hexes.keySet())
        {
            if (!this.hexes.get(key).equals(other.hexes.get(key)))
                return false;
        }
        if (this.ports.size() != other.ports.size())
            return false;
        for (int i = 0; i < this.ports.size(); i++)
        {
            if (!this.ports.get(i).equals(other.ports.get(i)))
                return false;
        }
        if (this.radius != other.radius)
            return false;
        if (this.roads.keySet().size() != other.roads.keySet().size())
            return false;
        for (EdgeLocation key : this.roads.keySet())
        {
            if (!this.roads.get(key).equals(other.roads.get(key)))
                return false;
        }
        if (!this.robberLocation.equals(other.robberLocation))
            return false;
        if (this.structures.keySet().size() != other.structures.keySet().size())
            return false;
        for (VertexLocation key : this.structures.keySet())
        {
            if (!this.structures.get(key).equals(other.structures.get(key)))
                return false;
        }
        return true;
    }

    public Map<EdgeLocation, Road> getRoads()
    {
        return roads;
    }
}
