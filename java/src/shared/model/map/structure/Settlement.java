package shared.model.map.structure;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.PlayerIndex;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * Object representing a settlement in the Catan game
 * @author amandafisher
 *
 */
public class Settlement extends Structure
{

	/**
	 * @param owner
	 * @param location
	 */
	public Settlement(PlayerIndex owner, VertexLocation location) {
		super(owner, location);
		setVictoryPointValue(1);
	}
	
	public Settlement(String json)
	{
		JsonParser parser = new JsonParser();
		JsonObject settlement = (JsonObject) parser.parse(json);
		super.setOwner(PlayerIndex.fromInt(settlement.get("owner").getAsInt()));
		JsonObject location = (JsonObject) settlement.get("location");
		super.setLocation(new VertexLocation(new HexLocation(location.get("x").getAsInt(),location.get("y").getAsInt()), VertexDirection.fromAbreviation(location.get("direction").getAsString())).getNormalizedLocation());
	}
	
}
