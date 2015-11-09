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
public class City extends MapStructure
{

	/**
	 * Creates a new City for the player associated with the given location.
	 * @param owner the owner of the city.
	 * @param location the location of the city.
	 */
	public City(PlayerIndex owner, VertexLocation location) 
	{
		super(owner, location);
		setVictoryPointValue(2);
	}

	/**
	 * @param json json String
	 */
	public City(String json) {
		JsonParser parser = new JsonParser();
		JsonObject city = (JsonObject) parser.parse(json);
		super.setOwner(PlayerIndex.fromInt(city.get("owner").getAsInt()));
		JsonObject location = (JsonObject) city.get("location");
		super.setLocation(new VertexLocation(new HexLocation(location.get("x").getAsInt(),location.get("y").getAsInt()), VertexDirection.fromAbreviation(location.get("direction").getAsString())).getNormalizedLocation());
	}

}
