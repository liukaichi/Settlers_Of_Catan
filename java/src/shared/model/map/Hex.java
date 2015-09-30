package shared.model.map;

import com.google.gson.*;

import shared.definitions.*;
import shared.locations.HexLocation;

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

    public Hex()
    {

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
        JsonElement jElement = new JsonParser().parse(json);
        JsonObject jObject = jElement.getAsJsonObject();
        jObject = jObject.getAsJsonObject("hexes");
        JsonArray jArray = jObject.getAsJsonArray();

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

    public void setHasRobber(boolean newHasRobber)
    {
        robberPresent = newHasRobber;
    }

    public boolean hasRobber()
    {
        return robberPresent;
    }
}
