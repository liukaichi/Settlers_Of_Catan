package shared.model.map;

import client.data.PlayerInfo;
import shared.definitions.*;
import shared.definitions.exceptions.PlacementException;
import shared.locations.*;

/**
 * Represents a Hex tile on the map.
 */
public class Hex
{
    /**
     * @see shared.definitions.ResourceType
     */
    private HexLocation location;

    /**
     * @see shared.definitions.HexType
     */
    private HexType hexType;

    /**
     * @see shared.definitions.ResourceType
     */
    private ResourceType resourceType;

    /**
     * The number assigned to this tile. Range of number is [2-12],
     * representative of the possible rolls of 2 dices
     */
    private int numberTile; // Do we want this to be a separate class?

    /**
     * This is stored as true if the robber is on this hex.
     */
    private boolean robberPresent;
    
    public void setHasRobber(boolean newHasRobber){
        robberPresent = newHasRobber;
    }

    public boolean hasRobber()
    {
        return robberPresent;
    }
}
