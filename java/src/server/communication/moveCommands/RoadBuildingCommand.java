package server.communication.moveCommands;

import shared.locations.EdgeLocation;

/**
 * Road_Building command object.
 * 
 * @author Cache Staheli
 * @see EdgeLocation
 *
 */
public class RoadBuildingCommand extends MoveCommand
{
    /**
     * The location of the road(s) to be built.
     */
    private EdgeLocation spot1, spot2;
}
