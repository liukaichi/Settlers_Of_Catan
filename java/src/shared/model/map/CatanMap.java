package shared.model.map;

import com.google.gson.*;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;
import shared.model.map.structure.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    // populated on map initialization
    private List<Port> ports = new ArrayList<Port>();
    private Map<HexLocation, Hex> hexes = new HashMap<HexLocation, Hex>();
    // populated on buy
    private Map<EdgeLocation, Road> roads = new HashMap<EdgeLocation, Road>();
    private Map<VertexLocation, Structure> structures = new HashMap<VertexLocation, Structure>();
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

    @Override
    public String toString()
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
                for (Structure city : this.structures.values())
                {
                    if (city.getClass().equals(City.class))
                        cities.add(parser.parse(city.toString()));
                }
            }
            map.add("cities", cities);
            // settlements
            JsonArray settlements = new JsonArray();
            {
                for (Structure settlement : this.structures.values())
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
        structures = new HashMap<VertexLocation, Structure>();
        radius = -1;
        robberLocation = new HexLocation(0, 0);
    }

    /**
     *
     * @param ports
     * @param hexes
     * @param roads
     * @param structures
     * @param radius
     * @param robberLocation
     */
    public CatanMap(List<Port> ports, Map<HexLocation, Hex> hexes, Map<EdgeLocation, Road> roads,
            Map<VertexLocation, Structure> structures, int radius, HexLocation robberLocation)
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
        // check if location exists, and is empty
        if (atLocation == null)
        {
            List<VertexLocation> vertices = getNearbyVertices(normalizedVertex);
            for (VertexLocation vertex : vertices)
            {
                if (structures.get(vertex.getNormalizedLocation()) != null)
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
        Structure structure = structures.get(location.getNormalizedLocation());
        if (structure != null && structure.getOwner().getIndex() == player.getIndex())
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
        Road atLocation = roads.get(normalizedEdge);
        // check if location exists, and is empty
        if (atLocation == null)
        {
            List<VertexLocation> vertices = getNearbyVertices(normalizedEdge);
            for (VertexLocation vertex : vertices)
            {
                Structure structure = structures.get(vertex.getNormalizedLocation());
                if (structure != null && structure.getOwner().getIndex() == player.getIndex())
                {
                    return true;
                }
            }
            //if no settlement then check for connecting road
            List<EdgeLocation> edges = getNearbyEdges(normalizedEdge);
            for (EdgeLocation edge : edges)
            {
                Road road = roads.get(edge.getNormalizedLocation());
                if (road != null && road.getOwner().equals(player))
                {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * @param location
     * @return
     */
    private List<VertexLocation> getNearbyVertices(EdgeLocation location)
    {
        EdgeLocation normalized = location.getNormalizedLocation();
        ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
        Hex hex = hexes.get(normalized.getHexLoc());
        if (normalized.getDir().equals(EdgeDirection.NorthWest))
        {
            vertices.add(hex.getVertexLocation(VertexDirection.West));
            vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
        }
        else if (normalized.getDir().equals(EdgeDirection.North))
        {
            vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
            vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
        }
        else if (normalized.getDir().equals(EdgeDirection.NorthEast))
        {
            vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
            vertices.add(hex.getVertexLocation(VertexDirection.East));
        }
        return vertices;
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
        if (hexes.get(location) != null && !robberLocation.equals(location))
        {
            return true;
        }
        return false;
    }

    /**
     * Method that places a road on the map
     * 
     * @param player
     *        -- this will be the player placing the road
     * @param location
     *        -- this will be the hex location where the road will be placed
     * @throws PlacementException
     */
    public void placeRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {
        if(canPlaceRoad(player, location))
        {
            Road road = new Road(player, location.getNormalizedLocation());
            roads.put(location.getNormalizedLocation(), road);
        }
        else
        {
            throw new PlacementException();
        }

    }
    public void forcePlaceRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {
            Road road = new Road(player, location.getNormalizedLocation());
            roads.put(location.getNormalizedLocation(), road);

    }

    /**
     * Method that places a settlement on the map
     * 
     * @param player
     *        -- this will be the player placing the settlement
     * @param location
     *        -- this will be the vertex location where the settlement will be
     *        placed
     * @throws PlacementException
     */
    public void placeSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {
            if(canPlaceSettlement(player, location))
            {
                Settlement settlement = new Settlement(player, location.getNormalizedLocation());
                structures.put(location.getNormalizedLocation(), settlement);
            }
            else
            {
                throw new PlacementException();
            }
    }
    public void forcePlaceSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {
                Settlement settlement = new Settlement(player, location.getNormalizedLocation());
                structures.put(location.getNormalizedLocation(), settlement);
    }

    /**
     * Method that places a city on the map
     * 
     * @param player
     *        -- this will be the player placing the city
     * @param location
     *        -- this will be the vertex location where the city will be placed
     * @throws PlacementException
     */
    public void placeCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {
        if(canPlaceCity(player,location))
        {
            City city = new City(player, location.getNormalizedLocation());
            structures.put(location.getNormalizedLocation(), city);
        }
        else
        {
            throw new PlacementException();
        }
    }
    public void forcePlaceCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {
            City city = new City(player, location.getNormalizedLocation());
            structures.put(location.getNormalizedLocation(), city);
    }

    /**
     * Method that moves the robber on the map
     * 
     * @param player
     *        -- this will be the player moving the robber
     * @param location
     *        -- this will be the hex location where the robber will be placed
     * @throws PlacementException
     */
    public void moveRobber(PlayerIndex player, HexLocation location) throws PlacementException
    {
        if(canMoveRobber(player, location))
        {
            this.robberLocation = location;
        }
        else
        {
            throw new PlacementException();
        }
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

    public void setStructures(Map<VertexLocation, Structure> structures)
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

    private List<VertexLocation> getNearbyVertices(VertexLocation location)
    {
        VertexLocation normalized = location.getNormalizedLocation();
        ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
        if (normalized.getDir().equals(VertexDirection.NorthEast))
        {
            Hex hex = hexes.get(normalized.getHexLoc());
            vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
            vertices.add(hex.getVertexLocation(VertexDirection.East));
            hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthEast));
            vertices.add(hex.getVertexLocation(VertexDirection.NorthWest));
            return vertices;
        }
        else if (normalized.getDir().equals(VertexDirection.NorthWest))
        {
            Hex hex = hexes.get(normalized.getHexLoc());
            vertices.add(hex.getVertexLocation(VertexDirection.West));
            vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
            hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.NorthWest));
            vertices.add(hex.getVertexLocation(VertexDirection.NorthEast));
            return vertices;
        }
        return null;
    }

    /**
     * @param location
     * @return
     */
    private List<EdgeLocation> getNearbyEdges(EdgeLocation location)
    {
        EdgeLocation normalized = location.getNormalizedLocation();
        ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();

        Hex hex = hexes.get(normalized.getHexLoc());
        if (normalized.getDir().equals(EdgeDirection.NorthWest))
        {
            edges.add(hex.getEdgeLocation(EdgeDirection.North));
            edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
        }
        else if (normalized.getDir().equals(EdgeDirection.North))
        {
            edges.add(hex.getEdgeLocation(EdgeDirection.NorthWest));
            edges.add(hex.getEdgeLocation(EdgeDirection.NorthEast));
        }
        else if (normalized.getDir().equals(EdgeDirection.NorthEast))
        {
            edges.add(hex.getEdgeLocation(EdgeDirection.NorthWest));
            edges.add(hex.getEdgeLocation(EdgeDirection.North));
        }
        else
        {
            return null;
        }
        hex = hexes.get(hex.getLocation().getNeighborLoc(EdgeDirection.North));
        edges.add(hex.getEdgeLocation(EdgeDirection.SouthEast));
        edges.add(hex.getEdgeLocation(EdgeDirection.SouthWest));
        return edges;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        CatanMap other = (CatanMap) obj;
        if(this.hexes.keySet().size() != other.hexes.keySet().size())
            return false;
        for(HexLocation key : this.hexes.keySet())
        {
            if(!this.hexes.get(key).equals(other.hexes.get(key)))
                return false;
        }
        if(this.ports.size() != other.ports.size())
            return false;
        for(int i = 0; i < this.ports.size(); i++)
        {
            if(!this.ports.get(i).equals(other.ports.get(i)))
            	return false;
        }
        if (this.radius != other.radius)
            return false;
        if(this.roads.keySet().size() != other.roads.keySet().size())
            return false;
        for(EdgeLocation key : this.roads.keySet())
        {
            if(!this.roads.get(key).equals(other.roads.get(key)))
                return false;
        }
        if (!this.robberLocation.equals(other.robberLocation))
            return false;
        if(this.structures.keySet().size() != other.structures.keySet().size())
            return false;
        for(VertexLocation key : this.structures.keySet())
        {
            if(!this.structures.get(key).equals(other.structures.get(key)))
                return false;
        }
        return true;
    }
}
