package shared.communication.moveCommands;

import shared.locations.VertexLocation;

/**
 * buildSettlement command object.
 * 
 * @author Cache Staheli
 * @see VertexLocation
 *
 */
public class BuildSettlementCommand extends MoveCommand
{
    /**
     * Location of the Settlement.
     */
    private VertexLocation settlementLocation;
    /**
     * Whether this is placed for free (setup).
     */
    private boolean isFree;
}
