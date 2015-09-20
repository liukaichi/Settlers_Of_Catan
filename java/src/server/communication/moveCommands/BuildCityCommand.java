package server.communication.moveCommands;

import shared.locations.VertexLocation;

/**
 * buildCity command object.
 * 
 * @author Cache Staheli
 * @see VertexLocation
 *
 */
public class BuildCityCommand extends MoveCommand
{
    /**
     * Location of the city.
     */
    private VertexLocation cityLocation;
}
