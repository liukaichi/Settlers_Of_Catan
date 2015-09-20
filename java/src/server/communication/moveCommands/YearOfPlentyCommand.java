package server.communication.moveCommands;

import shared.model.resource.Resource;

/**
 * Year_of_Plenty command object.
 * 
 * @author Cache Staheli
 * @see Resource
 *
 */
public class YearOfPlentyCommand extends MoveCommand
{
    /**
     * The resource(s) you collect.
     */
    private Resource resource1, resource2;
}
