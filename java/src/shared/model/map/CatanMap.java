package shared.model.map;

import client.map.MapComponent;
import com.google.gson.*;
import server.util.FileUtils;
import shared.definitions.HexType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.map.structure.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the board game map of the Catan game
 *
 * @author davidtaylor
 */
public class CatanMap implements Serializable
{
    private static final long serialVersionUID = -5436502447154889792L;
    // populated on map initialization
    private List<Port> ports = new ArrayList<>();
    private Map<HexLocation, Hex> hexes = new TreeMap<>();
    // populated on buy
    private Map<EdgeLocation, Road> roads = new HashMap<>();
    private Map<VertexLocation, MapStructure> structures = new HashMap<>();
    private int radius;
    private HexLocation robberLocation;

    /**
     * Initializes a CatanMap from Json.
     *
     * @param json the Json to initialize from.
     */
    public CatanMap(String json)
    {
        this();
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
        this.ports = new ArrayList<>();
        for (JsonElement elem : portsArray)
        {
            JsonObject port = (JsonObject) elem;
            this.ports.add(new Port(port.toString()));
        }
        JsonObject robber = map.getAsJsonObject("robber");
        Hex robberHex = this.hexes.get(new HexLocation(robber.get("x").getAsInt(), robber.get("y").getAsInt()));
        robberHex.setHasRobber(true);
        this.robberLocation = robberHex.getLocation();
    }

    /**
     * Initializes an empty map.
     */
    public CatanMap()
    {
        ports = new ArrayList<>();
        hexes = new TreeMap<>();
        roads = new HashMap<>();
        structures = new HashMap<>();
        radius = 3;
        robberLocation = new HexLocation(0, 0);
    }

    /**
     * Creates a map with the given ports, hexes, roads, structures, radius, and robber location.
     *
     * @param ports          the list of ports in the map.
     * @param hexes          the hexes in the map.
     * @param roads          the roads in the map.
     * @param structures     the structures in the map.
     * @param radius         the radius of the map.
     * @param robberLocation the location of the robber in the map.
     */
    public CatanMap(List<Port> ports, Map<HexLocation, Hex> hexes, Map<EdgeLocation, Road> roads,
            Map<VertexLocation, MapStructure> structures, int radius, HexLocation robberLocation)
    {
        this();
        this.ports = ports;
        this.hexes = hexes;
        this.roads = roads;
        this.structures = structures;
        this.radius = radius;
        this.robberLocation = robberLocation;
    }

    public CatanMap(boolean randomTiles, boolean randomNumbers, boolean randomPorts)
    {
        this();
        //initialize with water
        populateWaterTiles();
        if (randomTiles)
        {
            populateRandomHexes();
        } else
        {
            populateDefaultHexes();
        }

        if (randomNumbers)
        {
            placeRandomNumbers();
        } else
        {
            placeDefaultNumbers();
        }

        if (randomPorts)
        {
            populateRandomPorts();
        } else
        {
            populateDefaultPorts();
        }
    }

    public List<Port> getPorts()
    {
        return ports;
    }

    public void setPorts(List<Port> ports)
    {
        this.ports = ports;
    }

    public Map<VertexLocation, MapStructure> getStructures()
    {
        return structures;
    }

    public void setStructures(Map<VertexLocation, MapStructure> structures)
    {
        this.structures = structures;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public HexLocation getRobberLocation()
    {
        return robberLocation;
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

    //assuming hex objects are by reference, this should modify the map
    private void populateRandomHexes()
    {
        //3 ore,4 wood,3 brick,4 sheep,4 wheat,1 desert
        int assignedHexes = 0;
        //Lambda expression filters the list of hexes so none of the outer water tiles/ports are included in this operation
        ArrayList<Hex> hexes = this.hexes.values().stream()
                .filter(h -> (((h.getLocation().getY() < 3) && (h.getLocation().getY() > -3)) && (
                        (h.getLocation().getX() < 3) && (h.getLocation().getX() > -3))
                        && Math.abs(h.getLocation().getX() + h.getLocation().getY()) != 3))
                .collect(Collectors.toCollection(ArrayList::new));
        //randomize who gets what resource
        Collections.shuffle(hexes);
        for (Hex hex : hexes)
        {
            if (assignedHexes < 3)
            {
                hex.setHexType(HexType.ORE);
                hex.setResourceType(ResourceType.ORE);
                assignedHexes++;
            } else if (assignedHexes < 7)
            {
                hex.setHexType(HexType.WOOD);
                hex.setResourceType(ResourceType.WOOD);
                assignedHexes++;
            } else if (assignedHexes < 10)
            {
                hex.setHexType(HexType.BRICK);
                hex.setResourceType(ResourceType.BRICK);
                assignedHexes++;
            } else if (assignedHexes < 14)
            {
                hex.setHexType(HexType.SHEEP);
                hex.setResourceType(ResourceType.SHEEP);
                assignedHexes++;
            } else if (assignedHexes < 18)
            {
                hex.setHexType(HexType.WHEAT);
                hex.setResourceType(ResourceType.WHEAT);
                assignedHexes++;
            } else
            {
                hex.setHexType(HexType.DESERT);
                hex.setResourceType(null);
                hex.setHasRobber(true);
                robberLocation = hex.getLocation();
                assignedHexes++;
            }
        }
    }

    private void populateDefaultHexes()
    {
        CatanMap map = FileUtils.getCatanMapFromFile(null, "defaultMap");
        this.setHexes(map.getHexes());
    }

    private void placeRandomNumbers()
    {
        //  2-12, 1 of 2,12, 2 of the rest
        int assignedHexes = 0;
        //Lambda expression filters the list of hexes so desert and none of the outer water tiles/ports are included in this operation
        ArrayList<Hex> hexes = this.hexes.values().stream()
                .filter(h -> !h.hasRobber() && (((h.getLocation().getY() < 3) && (h.getLocation().getY() > -3)) && (
                        (h.getLocation().getX() < 3) && (h.getLocation().getX() > -3))
                        && Math.abs(h.getLocation().getX() + h.getLocation().getY()) != 3))
                .collect(Collectors.toCollection(ArrayList::new));
        //randomize who gets what number
        Collections.shuffle(hexes);
        for (Hex hex : hexes)
        {
            if (assignedHexes < 1)
            {
                hex.setNumberTile(2);
                assignedHexes++;
            } else if (assignedHexes < 3)
            {
                hex.setNumberTile(3);
                assignedHexes++;
            } else if (assignedHexes < 5)
            {
                hex.setNumberTile(4);
                assignedHexes++;
            } else if (assignedHexes < 7)
            {
                hex.setNumberTile(5);
                assignedHexes++;
            } else if (assignedHexes < 9)
            {
                hex.setNumberTile(6);
                assignedHexes++;
            } else if (assignedHexes < 11)
            {
                hex.setNumberTile(8);
                assignedHexes++;
            } else if (assignedHexes < 13)
            {
                hex.setNumberTile(9);
                assignedHexes++;
            } else if (assignedHexes < 15)
            {
                hex.setNumberTile(10);
                assignedHexes++;
            } else if (assignedHexes < 17)
            {
                hex.setNumberTile(11);
                assignedHexes++;
            } else
            {
                hex.setNumberTile(12);
                assignedHexes++;
            }
        }
    }

    private void placeDefaultNumbers()
    {
        CatanMap map = FileUtils.getCatanMapFromFile(null, "defaultMap");
        for (Map.Entry<HexLocation, Hex> hex : map.getHexes().entrySet())
        {
            int number = hex.getValue().getNumberTile();
            hexes.get(hex.getKey()).setNumberTile(number);
        }
    }

    private void populateRandomPorts()
    {

        //  5 2to1 (1 for each resource), 4 3to1
        int assignedHexes = 0;
        //Lambda expression filters out any tiles within the radius 3, so should return all surrounding water edges
        ArrayList<Hex> hexes = this.hexes.values().stream()
                .filter(h -> !(((h.getLocation().getY() < 3) && (h.getLocation().getY() > -3)) && (
                        (h.getLocation().getX() < 3) && (h.getLocation().getX() > -3))
                        && Math.abs(h.getLocation().getX() + h.getLocation().getY()) != 3))
                .collect(Collectors.toCollection(ArrayList::new));
        //randomize who gets what number
        Collections.shuffle(hexes);
        ResourceType type = ResourceType.WOOD;
        for (Hex hex : hexes)
        {
            if (assignedHexes < 5)
            {
                hex.setResourceType(type);
                ports.add(new Port(hex));
                type = type.next();
                assignedHexes++;
            } else if (assignedHexes < 9)
            {
                hex.setResourceType(null);
                ports.add(new Port(hex));
                assignedHexes++;
            } else
            {

            }
        }
    }

    private void populateDefaultPorts()
    {
        CatanMap map = FileUtils.getCatanMapFromFile(null, "defaultMap");
        this.setPorts(map.getPorts());
    }

    private boolean isPort(Hex hex)
    {
        for (Port port : ports)
        {
            if (port.getLocation().equals(hex.getLocation()))
            {
                return true;
            }
        }
        return false;
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
                    if (!isPort(hex) && !hex.getHexType().equals(HexType.WATER))
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

    private boolean isTwoRoadsAwayFromOpponents(VertexLocation location)
    {
        List<VertexLocation> vertices = getNearbyVertices(location);
        for (VertexLocation vertex : vertices)
        {
            if (structures.get(vertex) != null)
            {
                return false;
            }
        }
        return true;
    }

    private boolean isSettlementConnectedToPlayersRoads(VertexLocation location, PlayerIndex player)
    {
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
        return false;
    }

    /**
     * Method that indicates whether a player has the ability to place a settlement in a certain location on the map
     *
     * @param player   this will be the player placing the settlement.
     * @param location this will be the location of the settlement; must ensure that this space on the map is empty.
     * @return true if the location is vacant and at least
     * two spaces away from another settlement otherwise returns false
     */
    public boolean canPlaceSettlement(PlayerIndex player, VertexLocation location)
    {
        HexLocation hexLocation = location.getHexLoc();
        if (isHexWithinMapRadius(hexLocation, this.radius))
        {
            MapStructure atLocation = structures.get(location);
            // check if location exists, and is empty
            if (atLocation == null)
            {
                return ((isTwoRoadsAwayFromOpponents(location)) && (isSettlementConnectedToPlayersRoads(location,
                        player)));
            }
        }
        return false;

    }

    /**
     * Forces a settlement to be placed without any checks.
     *
     * @param player   the player to build for.
     * @param location the location to build at.
     * @throws PlacementException if something goes wrong.
     */
    public void placeSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {
        Settlement settlement = new Settlement(player, location);
        structures.put(location, settlement);
    }

    /**
     * Method that indicates whether a player has the ability to place a city in
     * a certain location on the map
     *
     * @param player   this will be the player placing the city
     * @param location this will be the location of the city; must ensure that this space already has a settlement
     *                 located their owned by this player
     * @return true if there is a settlement at the specified location and it is owned by the player otherwise
     * returns false
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
     * Builds a city for the given player at the given location.
     *
     * @param player   the player who is building the city.
     * @param location the location where the city is being built.
     */
    public void buildCity(PlayerIndex player, VertexLocation location)
    {

    }

    /**
     * Method that indicates whether a player has the ability to place a city on
     * a certain edge on the map
     *
     * @param player            this will be the player placing the road
     * @param location          this will be the edge location where the road will be placed;
     *                          must ensure this space is empty on the map
     * @param allowDisconnected whether this road must be connected to a settlement/road to place. True only during the setup phase.
     * @return true if the player owns a settlement or city at the neighboring vertex locations and there is no current
     * road there. False otherwise
     */
    public boolean canPlaceRoad(PlayerIndex player, EdgeLocation location, boolean allowDisconnected)
    {
        HexLocation hexLocation = location.getHexLoc();

        //if (Math.abs(hexLocation.getY()) <= radius && Math.abs(hexLocation.getX()) <= radius)
        if (isHexWithinMapRadius(hexLocation, this.radius))
        {
            Road atLocation = roads.get(location);
            // check if location exists, and is empty
            if (atLocation == null)
            {
                //check valid hex
                if (isEdgeOnlyOnWater(hexLocation, location.getDir()))
                {
                    return false;
                }
                //check nearby vertices
                List<VertexLocation> vertices = getNearbyVertices(location);
                for (VertexLocation vertex : vertices)
                {
                    if (isPlayerAtLocation(vertex, player))
                    {
                        return !allowDisconnected;
                    }
                }
                if (!allowDisconnected)
                {
                    return isRoadConnectedToPlayersRoads(location, player);
                } else
                {
                    List<VertexLocation> vertexes = getNearbyVertices(location);
                    for (VertexLocation vertex : vertexes)
                    {
                        if (structures.get(vertex) == null && isTwoRoadsAwayFromOpponents(vertex))
                        {
                            return true;
                        }
                    }
                    return false;
                }

            }
        }
        return false;

    }

    /**
     * Checks to see if the given hex is within the radius of the map.
     *
     * @param hexLocation the location of the hex in question.
     * @param radius      the radius of the map.
     * @return true if the hex is within the radius of the map, false otherwise.
     */
    private boolean isHexWithinMapRadius(HexLocation hexLocation, int radius)
    {
        return ((Math.abs(hexLocation.getY()) <= radius) && (Math.abs(hexLocation.getX()) <= radius));
    }

    /**
     * Checks to see if the edge (from the hex) is only located on water (used for bounds checking)
     *
     * @param hexLocation   the location of the hex.
     * @param roadDirection the direction of the hex that edge is on.
     * @return false if the edge is on land (always or on the edge of land or water) and true otherwise.
     */
    private boolean isEdgeOnlyOnWater(HexLocation hexLocation, EdgeDirection roadDirection)
    {
        Hex hex = hexes.get(hexLocation);
        if (hex != null)
        {
            if (!hex.getHexType().equals(HexType.WATER))
            {
                return false;
            }
            Hex opposite = hexes.get(hexLocation.getNeighborLoc(roadDirection));
            if (opposite == null || opposite.getHexType().equals(HexType.WATER))
            {
                return true;
            }

        }
        return false;
    }

    /**
     * Checks to see if a player is at an edge (road).
     *
     * @param location the location to check.
     * @param player   the player's index.
     * @return true if the player is at the location, false otherwise.
     */
    private boolean isPlayerAtLocation(EdgeLocation location, PlayerIndex player)
    {
        Road road = roads.get(location);
        return ((road != null) && (road.getOwner().getIndex() == player.getIndex()));
    }

    /**
     * Checks to see if a player is at a vertex (settlement or city).
     *
     * @param location the location to check.
     * @param player   the player's index.
     * @return true if the player is at the location, false otherwise.
     */
    private boolean isPlayerAtLocation(VertexLocation location, PlayerIndex player)
    {
        MapStructure mapStructure = structures.get(location);
        return ((mapStructure != null) && (mapStructure.getOwner().getIndex() == player.getIndex()));

    }

    /**
     * Checks to see if there is a settlement or city between the two adjacent edges.
     *
     * @param edge1 the first edge.
     * @param edge2 the second edge.
     * @return true if there is a structure between the two, false otherwise.
     */
    private boolean isStructureBetweenTwoEdges(EdgeLocation edge1, EdgeLocation edge2)
    {
        //get the vertex between the two
        for (VertexLocation vertex : getNearbyVertices(edge1))
        {
            if (getNearbyVertices(edge2).contains(vertex) && structures.get(vertex) != null)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the given edge is connected to a player's already existing road.
     *
     * @param location the location of the edge in question.
     * @param player   the player's index.
     * @return true if the edge is adjacent to one of the player's already existing roads, false otherwise.
     */
    private boolean isRoadConnectedToPlayersRoads(EdgeLocation location, PlayerIndex player)
    {
        List<EdgeLocation> edges = getNearbyEdges(location);
        for (EdgeLocation edge : edges)
        {
            if (isPlayerAtLocation(edge, player))
            {
                return !isStructureBetweenTwoEdges(location, edge);
            }
        }
        return false;
    }

    /**
     * Method that indicates whether a player has the ability to move a robber
     * on a certain Hex
     *
     * @param location -- this will be the hex location where the robber will be placed;
     *                 cannot place on water or where the robber already is
     * @return boolean -- returns true if it is not moving to its current
     * location and it is not a sea piece otherwise returns false
     */
    public boolean canMoveRobber(HexLocation location)
    {
        return ((hexes.get(location) != null) && (!robberLocation.equals(location)) && !hexes.get(location).getHexType()
                .equals(HexType.WATER));
    }

    /**
     * Places a Road without any checks.
     *
     * @param player   the player to build the road for.
     * @param location the location of the road.
     * @throws PlacementException if something goes wrong.
     */
    public void placeRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {
        Road road = new Road(player, location);
        roads.put(location, road);
    }

    /**
     * Places a City without any checks.
     *
     * @param player   the player to build the city for.
     * @param location the location of the city.
     * @throws PlacementException if something goes wrong.
     */
    public void placeCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {
        City city = new City(player, location);
        structures.put(location, city);
    }

    public Map<HexLocation, Hex> getHexes()
    {
        return hexes;
    }

    public void setHexes(Map<HexLocation, Hex> hexes)
    {
        this.hexes = hexes;
    }

    /**
     * Gets vertices that are one vertex away.
     *
     * @param normalized the normalized vertex to find neighbors for.
     * @return a list of adjacent vertices.
     * @pre the vertex location passed in is normalized.
     */
    private List<VertexLocation> getNearbyVertices(VertexLocation normalized)
    {
        ArrayList<VertexLocation> vertices = new ArrayList<>();
        switch (normalized.getDir())
        {
        case NorthEast:
            vertices.addAll(getNearbyVerticesFromNorthEastVertex(normalized));
            break;
        case NorthWest:
            vertices.addAll(getNearbyVerticesFromNorthWestVertex(normalized));
            break;
        }
        return vertices;
    }

    public void setRobberLocation(HexLocation robberLocation) throws PlacementException
    {
        if (canMoveRobber(robberLocation))
            this.robberLocation = robberLocation;
        else
            throw new PlacementException("Can't place robber at this location");
    }

    /**
     * Gets the vertices that are adjacent to the North East Vertex of a hex.
     *
     * @param vertexLocation the location of the NE vertex.
     * @return a list of adjacent vertices.
     */
    private List<VertexLocation> getNearbyVerticesFromNorthEastVertex(VertexLocation vertexLocation)
    {
        List<VertexLocation> vertices = new ArrayList<>();
        Hex hex = hexes.get(vertexLocation.getHexLoc());
        if (hex != null)
        {
            vertices.add(hex.getVertexLocation(VertexDirection.East));
            vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));

            hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthEast));
            if (hex != null)
            {
                vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
            }
        }
        return vertices;
    }

    /**
     * Gets the vertices that are adjacent to the North West Vertex of a hex.
     *
     * @param vertexLocation the location of the NW vertex.
     * @return a list of adjacent vertices.
     */
    private List<VertexLocation> getNearbyVerticesFromNorthWestVertex(VertexLocation vertexLocation)
    {
        List<VertexLocation> vertices = new ArrayList<>();
        Hex hex = hexes.get(vertexLocation.getHexLoc());
        if (hex != null)
        {
            vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
            vertices.add(hex.getVertexLocation(VertexDirection.West));

            hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthWest));
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
     * @param normalized the normalized location of the edge.
     * @return a list of vertices one distance away.
     * @pre the location is normalized.
     */
    public List<VertexLocation> getNearbyVertices(EdgeLocation normalized)
    {
        ArrayList<VertexLocation> vertices = new ArrayList<>();
        Hex hex = hexes.get(normalized.getHexLoc());
        if (hex != null)
        {
            switch (normalized.getDir())
            {
            case NorthWest:
                vertices.add(hex.getVertexLocation(VertexDirection.West));
                vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
                break;
            case North:
                vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
                vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
                break;
            case NorthEast:
                vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
                vertices.add(hex.getVertexLocation(VertexDirection.East));
                break;
            }
        }
        return vertices;
    }

    /**
     * Gets edges adjacent to the given vertexLocation.
     *
     * @param normalized the normalized location of the vertex.
     * @return a list of edges adjacent to the location of the vertex.
     */
    private List<EdgeLocation> getNearbyEdges(VertexLocation normalized)
    {
        ArrayList<EdgeLocation> edges = new ArrayList<>();
        switch (normalized.getDir())
        {
        case NorthEast:
            edges.addAll(getNearbyEdgesFromNorthEastVertex(normalized));
            break;
        case NorthWest:
            edges.addAll(getNearbyEdgesFromNorthWestVertex(normalized));
            break;
        }
        return edges;
    }

    /**
     * Gets edges adjacent to the North East Vertex
     *
     * @param vertexLocation the NE Vertex
     * @return a list of edges adjacent to the location of the vertex.
     */
    private List<EdgeLocation> getNearbyEdgesFromNorthEastVertex(VertexLocation vertexLocation)
    {
        List<EdgeLocation> edges = new ArrayList<>();
        Hex hex = hexes.get(vertexLocation.getHexLoc());
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
        return edges;
    }

    /**
     * Gets edges adjacent to the North West Vertex
     *
     * @param vertexLocation the NW Vertex
     * @return a list of edges adjacent to the location of the vertex.
     */
    private List<EdgeLocation> getNearbyEdgesFromNorthWestVertex(VertexLocation vertexLocation)
    {
        List<EdgeLocation> edges = new ArrayList<>();
        Hex hex = hexes.get(vertexLocation.getHexLoc());
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
        return edges;
    }

    /**
     * Gets edges that are one edge distance away. Edges are added clockwise
     *
     * @param normalized the current location of the edge.
     * @return a list of edges one distance away.
     */
    private List<EdgeLocation> getNearbyEdges(EdgeLocation normalized)
    {
        ArrayList<EdgeLocation> edges = new ArrayList<>();

        switch (normalized.getDir())
        {
        case NorthWest:
            edges.addAll(getNearbyEdgesFromNorthWestEdge(normalized));
            break;
        case North:
            edges.addAll(getNearbyEdgesFromNorthEdge(normalized));
            break;
        case NorthEast:
            edges.addAll(getNearbyEdgesFromNorthEastEdge(normalized));
            break;
        }
        return edges;
    }

    private List<EdgeLocation> getNearbyEdgesFromNorthWestEdge(EdgeLocation edgeLocation)
    {
        List<EdgeLocation> edges = new ArrayList<>();
        Hex hex = hexes.get(edgeLocation.getHexLoc());
        if (hex != null)
        {
            edges.add(hex.getEdgeLocation(EdgeDirection.North));
            edges.add(hex.getEdgeLocation(EdgeDirection.SouthWest));

            hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthWest));
            if (hex != null)
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
                edges.add(hex.getEdgeLocation(EdgeDirection.South));
            }
        }
        return edges;
    }

    private List<EdgeLocation> getNearbyEdgesFromNorthEdge(EdgeLocation edgeLocation)
    {
        List<EdgeLocation> edges = new ArrayList<>();
        Hex hex = hexes.get(edgeLocation.getHexLoc());
        if (hex != null)
        {
            edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
            edges.add(hex.getEdgeLocation(EdgeDirection.NorthWest));

            hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.North));
            if (hex != null)
            {
                edges.add(hex.getEdgeLocation(EdgeDirection.SouthEast));
                edges.add(hex.getEdgeLocation(EdgeDirection.SouthWest));
            }
        }
        return edges;
    }

    private List<EdgeLocation> getNearbyEdgesFromNorthEastEdge(EdgeLocation edgeLocation)
    {
        List<EdgeLocation> edges = new ArrayList<>();
        Hex hex = hexes.get(edgeLocation.getHexLoc());
        if (hex != null)
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
        return edges;
    }

    /**
     * Gets the players with settlements or cities at the given HexLocation.
     *
     * @param hexLocation the location to check.
     * @return a unique set of players at the hex.
     */
    public Set<PlayerIndex> getHexPlayers(HexLocation hexLocation)
    {
        Set<PlayerIndex> players = new HashSet<>();
        for (VertexLocation location : hexes.get(hexLocation).getVertices())
        {

            MapStructure structure = structures.get(location.getNormalizedLocation());
            if (structure != null)
            {
                players.add(structure.getOwner());
            }
        }
        return players;
    }

    public List<PlayerIndex> getHexPlayersWithSettlement(HexLocation hexLocation)
    {
        List<PlayerIndex> players = new ArrayList<>();
        for (VertexLocation location : hexes.get(hexLocation).getVertices())
        {

            MapStructure structure = structures.get(location.getNormalizedLocation());
            if (structure != null && structure instanceof Settlement)
            {
                players.add(structure.getOwner());
            }
        }
        return players;
    }

    public List<PlayerIndex> getHexPlayersWithCity(HexLocation hexLocation)
    {
        List<PlayerIndex> players = new ArrayList<>();
        for (VertexLocation location : hexes.get(hexLocation).getVertices())
        {

            MapStructure structure = structures.get(location.getNormalizedLocation());
            if (structure != null && structure instanceof City)
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

    public void setRoads(Map<EdgeLocation, Road> roads)
    {
        this.roads = roads;
    }

    public ArrayList<Hex> getHexesByNumber(int number)
    {
        ArrayList<Hex> selectedHexes = new ArrayList<>();
        for (Hex hex : hexes.values())
        {
            if (hex.getNumberTile() == number)
                selectedHexes.add(hex);
        }
        return selectedHexes;
    }

    private static void createMapGui(CatanMap map)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            JPanel contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            frame.setContentPane(contentPane);
            MapComponent mapComponent = new MapComponent();
            //populate mapview---------------------------------------------------------------
            Map<HexLocation, Hex> hexes = map.getHexes();
            for (Hex hex : hexes.values())
            {
                mapComponent.addHex(hex.getLocation(), hex.getHexType());
                if (hex.getNumberTile() != -1)
                    mapComponent.addNumber(hex.getLocation(), hex.getNumberTile());
            }
            mapComponent.placeRobber(map.getRobberLocation());
            ArrayList<Port> ports = (ArrayList<Port>) map.getPorts();
            for (Port port : ports)
            {
                mapComponent.placePort(port.getEdgeLocation(), port.getResource());
            }
            //--------------------------------------------------------------------------------
            frame.add(mapComponent, BorderLayout.CENTER);
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
        });
    }

    public static void main(String[] args)
    {
        CatanMap map = new CatanMap(true, true, true);
        createMapGui(map);
    }

    public EdgeLocation getRandomEdgeLocation()
    {
        Random generator = new Random();
        Object[] values = getHexes().values().toArray();
        Hex hex = (Hex) values[generator.nextInt(values.length)];

        Object[] edges = EdgeDirection.values();
        EdgeDirection randomDirection = (EdgeDirection) edges[generator.nextInt(edges.length)];
        return new EdgeLocation(hex.getLocation(), randomDirection).getNormalizedLocation();

    }
}
