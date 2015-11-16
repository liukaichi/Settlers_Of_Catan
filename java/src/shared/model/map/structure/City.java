package shared.model.map.structure;

import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;

/**
 * Object representing a city in the Catan game
 *
 * @author amandafisher
 */
public class City extends MapStructure
{

    /**
     * Creates a new City for the player associated with the given location.
     *
     * @param owner    the owner of the city.
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
    public City(String json)
    {
        super(json);
    }

}
