package shared.communication.moveCommands;

import shared.model.bank.resource.Resources;


/**
 * discardCards command object.
 * 
 * @author Cache Staheli
 * @see Resources
 */
public class DiscardCardsCommand extends MoveCommand
{
    /**
     * List of discarded cards.
     */
    private Resources discardedCards;
}
