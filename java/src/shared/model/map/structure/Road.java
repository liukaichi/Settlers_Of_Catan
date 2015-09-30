package shared.model.map.structure;

import java.util.List;

import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * Object representing a road or a hex edge in the Catan game
 */
public class Road
{
    private PlayerIndex owner;
    private EdgeLocation location;
    
    public Road(PlayerIndex owner, EdgeLocation location)
    {
    	this.owner = owner;
    	this.location = location;
    }
    
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
