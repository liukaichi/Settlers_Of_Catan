package shared.locations;

import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation implements JsonSerializer<EdgeLocation>, Serializable
{

    private static final long serialVersionUID = 8223962700300409011L;
    private HexLocation hexLoc;
    private EdgeDirection dir;

    public EdgeLocation(HexLocation hexLoc, EdgeDirection dir)
    {
        setHexLoc(hexLoc);
        setDir(dir);
    }

    public EdgeLocation(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject edgeLocationObject = (JsonObject) parser.parse(json);
        this.hexLoc = new HexLocation(edgeLocationObject.get("x").getAsInt(), edgeLocationObject.get("y").getAsInt());
        this.dir = EdgeDirection.fromAbreviation(edgeLocationObject.get("direction").getAsString());
    }

    public HexLocation getHexLoc()
    {
        return hexLoc;
    }

    private void setHexLoc(HexLocation hexLoc)
    {
        if (hexLoc == null)
        {
            throw new IllegalArgumentException("hexLoc cannot be null");
        }
        this.hexLoc = hexLoc;
    }

    public EdgeDirection getDir()
    {
        return dir;
    }

    private void setDir(EdgeDirection dir)
    {
        this.dir = dir;
    }

    @Override public String toString()
    {
        return "EdgeLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
    }

    @Override public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dir == null) ? 0 : dir.hashCode());
        result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
        return result;
    }

    /**
     * Equals function comparing Direction and Hex Location
     *
     * @param obj Object to be compared with
     * @return returns true if equals, false if not.
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EdgeLocation other = (EdgeLocation) obj;
        if (dir != other.dir)
            return false;
        if (hexLoc == null)
        {
            if (other.hexLoc != null)
                return false;
        } else if (!hexLoc.equals(other.hexLoc))
            return false;
        return true;
    }

    /**
     * Returns a canonical (i.e., unique) value for this edge location. Since
     * each edge has two different locations on a map, this method converts a
     * hex location to a single canonical form. This is useful for using hex
     * locations as map keys.
     *
     * @return Normalized hex location
     */
    public EdgeLocation getNormalizedLocation()
    {

        // Return an EdgeLocation that has direction NW, N, or NE

        switch (dir)
        {
        case NorthWest:
        case North:
        case NorthEast:
            return this;
        case SouthWest:
        case South:
        case SouthEast:
            return new EdgeLocation(hexLoc.getNeighborLoc(dir), dir.getOppositeDirection());
        default:
            assert false;
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(EdgeLocation src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty("direction", EdgeDirection.toAbreviation(src.dir));
        obj.addProperty("x", src.getNormalizedLocation().hexLoc.getX());
        obj.addProperty("y", src.getNormalizedLocation().hexLoc.getY());
        return obj;
    }
}
