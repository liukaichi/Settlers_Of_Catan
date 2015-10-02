package shared.model.map.structure;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.PlayerIndex;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
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
    
	/**
	 * @param string
	 */
	public Road(String json) {
		JsonParser parser = new JsonParser();
		JsonObject road = (JsonObject) parser.parse(json);
		this.owner = PlayerIndex.fromInt(road.get("owner").getAsInt());
		JsonObject location = (JsonObject) road.get("location");
		this.location = new EdgeLocation(new HexLocation(location.get("x").getAsInt(),location.get("y").getAsInt()), EdgeDirection.fromAbreviation((location.get("direction").getAsString())));
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
	@Override
	public String toString() {
		JsonObject road = new JsonObject();
		road.addProperty("owner", owner.getIndex());
		JsonObject location = new JsonObject();
		location.addProperty("direction", EdgeDirection.toAbreviation(this.location.getDir()));
		location.addProperty("x", this.getLocation().getHexLoc().getX());
		location.addProperty("y", this.getLocation().getHexLoc().getY());
		road.add("location", location);
		return road.toString();
	}
}
