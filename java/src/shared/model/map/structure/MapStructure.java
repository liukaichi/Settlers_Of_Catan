package shared.model.map.structure;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.PlayerIndex;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.io.Serializable;

/**
 * Structures in our design include settlements, cities and roads. These are
 * represented by the structure object in our Catan game. They are each worth a
 * certain number of victory points.
 *
 * @author amandafisher
 */
public abstract class MapStructure implements Serializable
{
    private static final long serialVersionUID = 7190061240268105873L;
    private PlayerIndex owner;
    private VertexLocation location;
    private int victoryPointValue;
    private int maxAmount;

    public MapStructure()
    {

    }

    public MapStructure(PlayerIndex owner, VertexLocation location)
    {
        this.owner = owner;
        this.location = location;
    }

    public MapStructure(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject settlement = (JsonObject) parser.parse(json);
        this.setOwner(PlayerIndex.fromInt(settlement.get("owner").getAsInt()));
        JsonObject location = (JsonObject) settlement.get("location");
        this.setLocation(new VertexLocation(new HexLocation(location.get("x").getAsInt(), location.get("y").getAsInt()),
                VertexDirection.fromAbreviation(location.get("direction").getAsString())).getNormalizedLocation());
    }


    public PlayerIndex getOwner()
    {
        return owner;
    }

    public void setOwner(PlayerIndex owner)
    {
        this.owner = owner;
    }

    public VertexLocation getLocation()
    {
        return location;
    }

    public void setLocation(VertexLocation location)
    {
        this.location = location;
    }

    public int getVictoryPointValue()
    {
        return victoryPointValue;
    }

    public void setVictoryPointValue(int victoryPointValue)
    {
        this.victoryPointValue = victoryPointValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override public String toString()
    {
        JsonObject structure = new JsonObject();
        structure.addProperty("owner", this.owner.getIndex());
        JsonObject location = new JsonObject();
        {
            location.addProperty("direction", VertexDirection.toAbreviation(this.location.getDir()));
            location.addProperty("x", this.location.getHexLoc().getX());
            location.addProperty("y", this.location.getHexLoc().getY());
        }
        structure.add("location", location);
        return structure.toString();
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

        MapStructure other = (MapStructure) obj;
        if (!this.location.equals(other.location))
            return false;
        if (this.owner.getIndex() != other.owner.getIndex())
            return false;
        return this.victoryPointValue == other.victoryPointValue;
    }
}
