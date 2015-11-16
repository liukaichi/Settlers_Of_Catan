package shared.locations;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation implements JsonSerializer<VertexLocation>
{
    private HexLocation hexLoc;
    private VertexDirection dir;

    /**
     * Creates a VertexLocation with the given location and direction.
     * @param hexLoc the hex that this vertex is on.
     * @param dir the direction on the hex.
     */
    public VertexLocation(HexLocation hexLoc, VertexDirection dir)
    {
        setHexLoc(hexLoc);
        setDir(dir);
    }

    /**
     * Builds a VertexLocation from Json.
     *
     * @param json the json representing the VertexLocation.
     */
    public VertexLocation(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject location = (JsonObject) parser.parse(json);
        int x = location.get("x").getAsInt();
        int y = location.get("y").getAsInt();
        this.hexLoc = new HexLocation(x, y);
        this.dir = VertexDirection.fromAbreviation(location.get("direction").getAsString());
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

    public VertexDirection getDir()
    {
        return dir;
    }

    private void setDir(VertexDirection direction)
    {
        this.dir = direction;
    }

    @Override public String toString()
    {
        return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
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
     * Equals function comparing direction and hex location
     *
     * @param obj Object to be compared to
     * @return True if equal, otherwise false.
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VertexLocation other = (VertexLocation) obj;
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
     * Returns a canonical (i.e., unique) value for this vertex location. Since
     * each vertex has three different locations on a map, this method converts
     * a vertex location to a single canonical form. This is useful for using
     * vertex locations as map keys.
     *
     * @return Normalized vertex location
     */
    public VertexLocation getNormalizedLocation()
    {

        // Return location that has direction NW or NE

        switch (dir)
        {
        case NorthWest:
        case NorthEast:
            return this;
        case West:
            return new VertexLocation(hexLoc.getNeighborLoc(EdgeDirection.SouthWest), VertexDirection.NorthEast);
        case SouthWest:
            return new VertexLocation(hexLoc.getNeighborLoc(EdgeDirection.South), VertexDirection.NorthWest);
        case SouthEast:
            return new VertexLocation(hexLoc.getNeighborLoc(EdgeDirection.South), VertexDirection.NorthEast);
        case East:
            return new VertexLocation(hexLoc.getNeighborLoc(EdgeDirection.SouthEast), VertexDirection.NorthWest);
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
    @Override public JsonElement serialize(VertexLocation src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", src.getNormalizedLocation().getHexLoc().getX());
        obj.addProperty("y", src.getNormalizedLocation().getHexLoc().getY());
        obj.addProperty("direction", VertexDirection.toAbreviation(src.dir));
        return obj;
    }
}
