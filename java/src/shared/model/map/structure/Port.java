package shared.model.map.structure;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.map.Hex;

import java.io.Serializable;

/**
 * Object representing a port in the Catan game
 *
 * @author amandafisher
 */
public class Port implements Serializable
{
    private static final long serialVersionUID = -619734400686565921L;
    /**
     * This indicates which resource the port trade is available with
     */
    private PortType resource;
    /**
     * This indicates where the port is on the Catan map
     */
    private HexLocation location;
    /**
     * This is the location of the hex.
     */
    private EdgeDirection direction;
    /**
     * This indicates the ratio for the resource in maritime trade
     */
    private TradeRatio ratio;

    /**
     * Initializes a Port from Json.
     * @param json the Json to parse.
     */
    public Port(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject port = (JsonObject) parser.parse(json);
        this.ratio = TradeRatio.fromInt(port.get("ratio").getAsInt());
        if (port.has("resource"))
        {
            this.resource = PortType.valueOf(port.get("resource").getAsString().toUpperCase());
        } else
        {
            this.resource = PortType.THREE;
        }
        this.direction = EdgeDirection.fromAbreviation(port.get("direction").getAsString());
        JsonObject location = port.getAsJsonObject("location");
        this.location = new HexLocation(location.get("x").getAsInt(), location.get("y").getAsInt());
    }

    public Port(Hex hex) {
        if(hex.getResourceType() != null) {
            this.resource = ResourceType.toPortType(hex.getResourceType());
            this.ratio = TradeRatio.TWO;
        }
        else
        {
            this.resource = PortType.THREE;
            this.ratio = TradeRatio.THREE;
        }
        this.location = hex.getLocation();
        int x = location.getX();
        int y = location.getY();

        if(y == 3)//bottom left
        {
            if(x == 0)
                this.direction = EdgeDirection.North;
            else
                this.direction = EdgeDirection.NorthEast;
        }
        else if(y == -3)//top right
        {
            if(x != 3)
                this.direction = EdgeDirection.South;
            else
                this.direction = EdgeDirection.SouthWest;
        }
        else if(x == -3)//left
        {
            if(y!=0)
                this.direction = EdgeDirection.NorthEast;
            else
                this.direction = EdgeDirection.SouthEast;
        }
        else if(x == 3)//right
        {
            if(y!=0)
                this.direction = EdgeDirection.SouthWest;
            else
                this.direction = EdgeDirection.NorthWest;
        }
        else if(x>0)//bottom right
        {
            this.direction = EdgeDirection.NorthWest;
        }
        else if(x<0)//top left
        {
            this.direction = EdgeDirection.SouthEast;
        }
    }

    public PortType getResource()
    {
        return resource;
    }

    public void setResource(PortType resource)
    {
        this.resource = resource;
    }

    public HexLocation getLocation()
    {
        return location;
    }

    public void setLocation(HexLocation location)
    {
        this.location = location;
    }

    public EdgeDirection getDirection()
    {
        return direction;
    }

    public void setDirection(EdgeDirection direction)
    {
        this.direction = direction;
    }

    public TradeRatio getRatio()
    {
        return ratio;
    }

    public void setRatio(TradeRatio ratio)
    {
        this.ratio = ratio;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override public String toString()
    {
        JsonObject port = new JsonObject();
        {
            port.addProperty("ratio", this.ratio.getRatio());
            if (this.resource != null)
            {
                if (!this.resource.equals(PortType.THREE))
                {
                    port.addProperty("resource", this.resource.toString().toLowerCase());
                }
            }
            port.addProperty("direction", EdgeDirection.toAbreviation(this.direction));
            JsonObject location = new JsonObject();
            {
                location.addProperty("x", this.location.getX());
                location.addProperty("y", this.location.getY());
            }
            port.add("location", location);
        }
        return port.toString();
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

        Port other = (Port) obj;
        if (!this.direction.equals(other.direction))
            return false;
        if (!this.location.equals(other.location))
            return false;
        if (this.ratio.getRatio() != other.ratio.getRatio())
            return false;
        return this.resource.ordinal() == other.resource.ordinal();
    }

    public EdgeLocation getEdgeLocation()
    {
        return new EdgeLocation(location, direction);
    }
}
