package shared.model.map.structure;

import shared.definitions.*;
import shared.locations.*;

public class Port
{
    private PortType resource;
    private HexLocation location;
    private EdgeDirection direction;
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
