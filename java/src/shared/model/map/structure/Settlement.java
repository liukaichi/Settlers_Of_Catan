package shared.model.map.structure;

import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;

/**
 * Object representing a settlement in the Catan game
 *
 * @author amandafisher
 */
public class Settlement extends MapStructure
{

    /**
     * Creates a new settlement.
     * @param owner the owner of the settlement.
     * @param location the location of the settlement.
     */
    public Settlement(PlayerIndex owner, VertexLocation location)
    {
        super(owner, location);
        setVictoryPointValue(1);
    }

    public Settlement(String json)
    {
        super(json);
    }

}
