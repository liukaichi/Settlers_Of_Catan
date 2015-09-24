package shared.model.map.structure;

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
}
