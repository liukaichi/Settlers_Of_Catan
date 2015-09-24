package shared.model.player;

import shared.definitions.PlayerIndex;
import shared.model.resource.Resources;

/**
 * This class represents a tradeOffer made by one of the four players
 * @author amandafisher
 *
 */
public class TradeOffer
{
	/**
	 * Player objects of the sender and receiver of player trade
	 */
    private PlayerIndex sender, receiver;
    /**
     * Object that represents the trade offer made by a player
     */
    private Resources offer;
}
