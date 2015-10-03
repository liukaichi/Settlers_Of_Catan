package shared.model.map.structure;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.*;
import shared.locations.*;
import shared.model.map.Hex;

/**
 * Object representing a port in the Catan game
 * @author amandafisher
 *
 */
public class Port
{
	/**
	 * This indicates which resource the port trade is available with
	 */
    private PortType resource;
    /**
     * This indicates where the port is on the Catan map
     */
    private HexLocation location;
    /**
     *  This is the location of the hex.
     */
    private EdgeDirection direction;
    /**
     * This indicates the ratio for the resource in maritime trade
     */
    private TradeRatio ratio;


	/**
	 * @param json
	 */
	public Port(String json) {
		JsonParser parser = new JsonParser();
		JsonObject port = (JsonObject) parser.parse(json);
		this.ratio = TradeRatio.fromInt(port.get("ratio").getAsInt());
		if(port.has("resource"))
		{
	        this.resource = PortType.valueOf(port.get("resource").getAsString().toUpperCase());
		}
		else
		{
		    this.resource = PortType.THREE;
		}
		this.direction = EdgeDirection.fromAbreviation(port.get("direction").getAsString());
		JsonObject location = port.getAsJsonObject("location");
		this.location = new HexLocation(location.get("x").getAsInt(), location.get("y").getAsInt());
	}
	public PortType getResource() {
		return resource;
	}
	public void setResource(PortType resource) {
		this.resource = resource;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	public EdgeDirection getDirection() {
		return direction;
	}
	public void setDirection(EdgeDirection direction) {
		this.direction = direction;
	}
	public TradeRatio getRatio() {
		return ratio;
	}
	public void setRatio(TradeRatio ratio) {
		this.ratio = ratio;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
	    JsonObject port = new JsonObject();
	    {
	        port.addProperty("ratio", this.ratio.getRatio());
	        if(this.resource != null)
	        {
	            if(this.resource.equals(PortType.THREE))
	            {
	                //do nothing
	            }
	            else
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
        
        Port other = (Port) obj;
        if (!this.direction.equals(other.direction))
            return false;
        if (!this.location.equals(other.location))
            return false;
        if (this.ratio.getRatio() != other.ratio.getRatio())
            return false;
        if (this.resource.ordinal() != other.resource.ordinal())
            return false;
        return true;
	}
}
