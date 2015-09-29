package shared.communication.moveCommands;

import shared.model.player.TradeOffer;

/**
 * offerTrade command object.
 * 
 * @author Cache Staheli
 * @see TradeOffer
 *
 */
public class OfferTradeCommand extends MoveCommand
{
    /**
     * What you get (+) and what you give (-), as well as with whom.
     */
    private TradeOffer offer;
}
