package shared.model.map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Hex tile on the map.
 */
public class Hex
{
    private Map<VertexDirection, VertexLocation> vertices;
    private Map<EdgeDirection, EdgeLocation> edges = new HashMap<EdgeDirection, EdgeLocation>();
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
    private int numberTile;

    /**
     * This is stored as true if the robber is on this hex.
     */
    private boolean robberPresent;

    public Hex()
    {
        vertices = new HashMap<>();
        edges = new HashMap<>();
        numberTile = -1;
    }

    /**
     * Parses a JSON object into a Hex
     *
     * @param json the JSON to parse.
     */
    public Hex(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonObject hex = (JsonObject) parser.parse(json);
        if (hex.has("resource"))
        {
            this.resourceType = ResourceType.valueOf(hex.get("resource").getAsString().toUpperCase());
            this.hexType = HexType.valueOf(hex.get("resource").getAsString().toUpperCase());
        } else
        {
            this.resourceType = null;
            this.hexType = HexType.DESERT;
        }

        JsonObject location = (JsonObject) hex.get("location");
        this.location = new HexLocation(location.get("x").getAsInt(), location.get("y").getAsInt());
        if (hex.has("number"))
        {
            this.numberTile = hex.get("number").getAsInt();
        } else
        {
            this.numberTile = -1;
        }
        if (this.location != null)
        {
            for (VertexDirection dir : VertexDirection.values())
            {
                vertices.put(dir, new VertexLocation(this.location, dir).getNormalizedLocation());
            }
            for (EdgeDirection dir : EdgeDirection.values())
            {
                edges.put(dir, new EdgeLocation(this.location, dir).getNormalizedLocation());
            }
        }
    }

    /**
     * @param location      the location of the hex
     * @param hexType       the type of the hex
     * @param resourceType  the resource type of the hex
     * @param numberTile    the number tile on the hex
     * @param robberPresent if the robber is present on the hex
     */
    public Hex(HexLocation location, HexType hexType, ResourceType resourceType, int numberTile, boolean robberPresent)
    {
        this(location, hexType);
        this.resourceType = resourceType;
        this.numberTile = numberTile;
        this.robberPresent = robberPresent;
    }

    public Hex(HexLocation location, HexType hexType)
    {
        this();
        this.location = location;
        this.hexType = hexType;
        if (location != null)
        {
            for (VertexDirection dir : VertexDirection.values())
            {
                vertices.put(dir, new VertexLocation(location, dir));
            }
            for (EdgeDirection dir : EdgeDirection.values())
            {
                edges.put(dir, new EdgeLocation(location, dir));
            }
        }
    }

    public VertexLocation getVertexLocation(VertexDirection dir)
    {
        return vertices.get(dir).getNormalizedLocation();
    }

    public EdgeLocation getEdgeLocation(EdgeDirection dir)
    {
        return edges.get(dir).getNormalizedLocation();
    }

    /**
     * @return the location
     */
    public HexLocation getLocation()
    {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(HexLocation location)
    {
        this.location = location;
    }

    /**
     * @return the hexType
     */
    public HexType getHexType()
    {
        return hexType;
    }

    /**
     * @param hexType the hexType to set
     */
    public void setHexType(HexType hexType)
    {
        this.hexType = hexType;
    }

    /**
     * @return the resourceType
     */
    public ResourceType getResourceType()
    {
        return resourceType;
    }

    /**
     * @param resourceType the resourceType to set
     */
    public void setResourceType(ResourceType resourceType)
    {
        this.resourceType = resourceType;
    }

    /**
     * @return the numberTile
     */
    public int getNumberTile()
    {
        return numberTile;
    }

    /**
     * @param numberTile the numberTile to set
     */
    public void setNumberTile(int numberTile)
    {
        this.numberTile = numberTile;
    }

    /**
     * @return the robberPresent
     */
    public boolean isRobberPresent()
    {
        return robberPresent;
    }

    /**
     * @param robberPresent the robberPresent to set
     */
    public void setRobberPresent(boolean robberPresent)
    {
        this.robberPresent = robberPresent;
    }

    public void setHasRobber(boolean newHasRobber)
    {
        robberPresent = newHasRobber;
    }

    public boolean hasRobber()
    {
        return robberPresent;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override public String toString()
    {
        JsonObject hex = new JsonObject();
        {
            if (this.resourceType != null)
                hex.addProperty("resource", this.resourceType.toString().toLowerCase());
            JsonObject location = new JsonObject();
            {
                location.addProperty("x", this.location.getX());
                location.addProperty("y", this.location.getY());
            }
            hex.add("location", location);
            if (this.numberTile != -1)
                hex.addProperty("number", this.numberTile);
        }
        return hex.toString();
    }

    /* (non-Javadoc)
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

        Hex other = (Hex) obj;
        if (this.edges.keySet().size() != other.edges.keySet().size())
            return false;
        for (EdgeDirection key : this.edges.keySet())
        {
            if (!this.edges.get(key).equals(other.edges.get(key)))
                return false;
        }
        if (!this.hexType.equals(other.hexType))
            return false;
        if (!this.location.equals(other.location))
            return false;
        if (this.numberTile != other.numberTile)
            return false;
        if (this.resourceType == null)
        {
            if (other.resourceType != null)
                return false;
        } else if (!this.resourceType.equals(other.resourceType))
        {
            return false;
        }
        if (this.robberPresent != other.robberPresent)
            return false;
        if (this.vertices.keySet().size() != other.vertices.keySet().size())
            return false;
        for (VertexDirection key : this.vertices.keySet())
        {
            if (!this.vertices.get(key).equals(other.vertices.get(key)))
                return false;
        }
        return true;
    }

    public Collection<VertexLocation> getVertices()
    {
        return vertices.values();
    }
}
