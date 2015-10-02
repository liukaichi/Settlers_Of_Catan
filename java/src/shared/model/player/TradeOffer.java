package shared.model.player;

import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;

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
    private int sender, receiver;
    /**
     * Object that represents the trade offer made by a player
     */
    private Resources offer;

    public TradeOffer(Player sender, Player receiver){
        this.sender = sender.getPlayerInfo().getPlayerIndex();
        this.receiver = receiver.getPlayerInfo().getPlayerIndex();
    }

    public void addToOffer(ResourceType type){
        offer.getResource(type).addResource(1);
    }

    public void subFromOffer(ResourceType type){
        offer.getResource(type).subResource(1);
    }
}
