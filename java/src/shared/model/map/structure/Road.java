package shared.model.map.structure;

import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;

/*
 * I called this EdgeObject instead of EdgeValue, because it's same naming
 * convention as VertexObject
 */
public abstract class Road
{
    private PlayerIndex owner;
    private EdgeLocation location;
	public PlayerIndex getOwner() {
		return owner;
	}
	public void setOwner(PlayerIndex owner) {
		this.owner = owner;
	}
	public EdgeLocation getLocation() {
		return location;
	}
	public void setLocation(EdgeLocation location) {
		this.location = location;
	}
}