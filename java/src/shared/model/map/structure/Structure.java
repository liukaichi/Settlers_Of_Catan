package shared.model.map.structure;

import com.google.gson.JsonObject;

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
    
    public Structure(PlayerIndex owner, VertexLocation location)
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
	    JsonObject structure = new JsonObject();
	    structure.addProperty("owner", this.owner.getIndex());
	    JsonObject location = new JsonObject();
	    {
	        location.addProperty("direction", this.location.getDir().toString());
	        location.addProperty("x", this.location.getHexLoc().getX());
	        location.addProperty("y", this.location.getHexLoc().getY()); 
	    }
	    structure.add("location", location);
	    return structure.toString();
	}
}
