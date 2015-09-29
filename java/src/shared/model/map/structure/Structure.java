package shared.model.map.structure;

import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;

/**
 * Structures in our design include settlements, cities and roads. These are represented by the structure object
 * in our Catan game. They are each worth a certain number of victory points. 
 * @author amandafisher
 *
 */
public abstract class Structure
{
    private PlayerIndex owner;
    // private EdgeLocation location; // what is this? Why in the VertexObject?
    private VertexLocation location; // I think they meant this one.
    private int victoryPointValue;
	public PlayerIndex getOwner() {
		return owner;
	}
	public void setOwner(PlayerIndex owner) {
		this.owner = owner;
	}
	public VertexLocation getLocation() {
		return location;
	}
	public void setLocation(VertexLocation location) {
		this.location = location;
	}
	public int getVictoryPointValue() {
		return victoryPointValue;
	}
	public void setVictoryPointValue(int victoryPointValue) {
		this.victoryPointValue = victoryPointValue;
	}
}
