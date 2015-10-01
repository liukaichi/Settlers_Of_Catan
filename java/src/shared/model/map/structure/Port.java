package shared.model.map.structure;

import com.google.gson.JsonObject;

import shared.definitions.*;
import shared.locations.*;

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
	        port.addProperty("ratio", 2);
	        port.addProperty("resource", this.resource.toString());
	        port.addProperty("direction", this.direction.toString());
	        JsonObject location = new JsonObject();
	        {
	            location.addProperty("x", this.location.getX());
	            location.addProperty("y", this.location.getY());
	        }
	        port.add("location", location);
	    }
	    return port.getAsString();
	}
}
