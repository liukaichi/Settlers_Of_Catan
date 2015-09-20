package server.communication.moveCommands;

import shared.locations.EdgeLocation;

/**
 * buildRoad command object.
 * 
 * @author Cache Staheli
 *
 */
public class BuildRoadCommand extends MoveCommand
{
    /**
     * Location of the Road.
     */
    private EdgeLocation roadLocation;
    /**
     * Whether this is placed for free (setup).
     */
    private boolean isFree;
}
