package server.communication.moveCommands;

import shared.model.resource.Resource;

/**
 * Monopoly command object.
 * 
 * @author Cache Staheli
 * @see Resource
 *
 */
public class MonopolyCommand extends MoveCommand
{
    /**
     * The resource the Monopoly Card applies to.
     */
    private Resource resource;
}
