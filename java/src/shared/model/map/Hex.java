package shared.model.map;

import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.*;

import java.util.Map;

/**
 * Represents a Hex tile on the map.
 */
public class Hex
{
    private Map<VertexDirection, VertexLocation> vertices;
    private Map<EdgeDirection, EdgeLocation> edges;
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

    public Hex()
    {
        //populate maps for lookup
        vertices = new HashMap<VertexDirection, VertexLocation>();
        for(VertexDirection dir : VertexDirection.values())
        {
            vertices.put(dir, new VertexLocation(location,dir));
        }
        edges = new HashMap<EdgeDirection, EdgeLocation>();
        for(EdgeDirection dir : EdgeDirection.values())
        {
            edges.put(dir, new EdgeLocation(location,dir));
        }
    }

    /**
     * Parses a JSON object into a Hex
     * 
     * @param json
     *        the JSON to parse.
     */
    public Hex(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonObject hex = (JsonObject)parser.parse(json);
        this.resourceType = ResourceType.valueOf(hex.get("resource").getAsString());
        this.hexType = HexType.valueOf(hex.get("resource").getAsString());
        JsonObject location = (JsonObject) hex.get("location");
        this.location = new HexLocation(location.get("x").getAsInt(),location.get("y").getAsInt());
        this.numberTile = hex.get("number").getAsInt();
    }

    /**
     * @param location
     *        the location of the hex
     * @param hexType
     *        the type of the hex
     * @param resourceType
     *        the resource type of the hex
     * @param numberTile
     *        the number tile on the hex
     * @param robberPresent
     *        if the robber is present on the hex
     */
    public Hex(HexLocation location, HexType hexType, ResourceType resourceType, int numberTile, boolean robberPresent)
    {
        this();
        this.location = location;
        this.hexType = hexType;
        this.resourceType = resourceType;
        this.numberTile = numberTile;
        this.robberPresent = robberPresent;
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
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}

	/**
	 * @return the hexType
	 */
	public HexType getHexType() {
		return hexType;
	}

	/**
	 * @param hexType the hexType to set
	 */
	public void setHexType(HexType hexType) {
		this.hexType = hexType;
	}

	/**
	 * @return the resourceType
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the numberTile
	 */
	public int getNumberTile() {
		return numberTile;
	}

	/**
	 * @param numberTile the numberTile to set
	 */
	public void setNumberTile(int numberTile) {
		this.numberTile = numberTile;
	}

	/**
	 * @return the robberPresent
	 */
	public boolean isRobberPresent() {
		return robberPresent;
	}

	/**
	 * @param robberPresent the robberPresent to set
	 */
	public void setRobberPresent(boolean robberPresent) {
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
    @Override
    public String toString()
    {
        JsonObject hex = new JsonObject();
        {
            hex.addProperty("resource", this.resourceType.toString().toLowerCase());
            JsonObject location = new JsonObject();
            {
                location.addProperty("x", this.location.getX());
                location.addProperty("y", this.location.getY());
            }
            hex.add("location", location);
            hex.addProperty("number", this.numberTile);
        }
        return hex.toString();
    }
}
