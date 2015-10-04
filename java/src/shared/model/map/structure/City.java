package shared.model.map.structure;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.PlayerIndex;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 * Object representing a city in the Catan game
 * @author amandafisher
 *
 */
public class City extends Structure
{

	/**
	 * @param owner
	 * @param location
	 */
	public City(PlayerIndex owner, VertexLocation location) 
	{
		super(owner, location);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param string
	 */
	public City(String json) {
		JsonParser parser = new JsonParser();
		JsonObject city = (JsonObject) parser.parse(json);
		super.setOwner(PlayerIndex.fromInt(city.get("owner").getAsInt()));
		JsonObject location = (JsonObject) city.get("location");
		super.setLocation(new VertexLocation(new HexLocation(location.get("x").getAsInt(),location.get("y").getAsInt()), VertexDirection.fromAbreviation(location.get("direction").getAsString())).getNormalizedLocation());
	}

}
