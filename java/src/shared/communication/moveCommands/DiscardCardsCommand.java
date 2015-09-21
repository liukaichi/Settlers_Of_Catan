package shared.communication.moveCommands;

import shared.model.resource.ResourceList;

/**
 * discardCards command object.
 * 
 * @author Cache Staheli
 * @see ResourceList
 */
public class DiscardCardsCommand extends MoveCommand
{
    /**
     * List of discarded cards.
     */
    private ResourceList discardedCards;
}
