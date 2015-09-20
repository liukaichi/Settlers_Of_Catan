package server.communication.moveCommands;

import shared.definitions.TradeRatio;
import shared.model.player.TradeOffer;

/**
 * maritimeTrade command object.
 * 
 * @author Cache Staheli
 * @see TradeRatio
 * @see TradeOffer
 */
public class MaritimeTradeCommand extends MoveCommand
{
    /**
     * The ratio at which the maritime offer is being extended.
     * 
     */
    private TradeRatio ratio;
    /**
     * What is being offered in the trade.
     */
    private TradeOffer offer;
}
