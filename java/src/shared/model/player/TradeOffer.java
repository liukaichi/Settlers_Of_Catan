package shared.model.player;

import com.sun.org.apache.regexp.internal.RESyntaxException;
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
    private Resources send, receive;

    public TradeOffer(Player sender, Player receiver){
        this.sender = sender.getPlayerInfo().getPlayerIndex();
        this.receiver = receiver.getPlayerInfo().getPlayerIndex();
        this.send = new Resources(false);
        this.receive = new Resources(false);
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public Resources getSend() {
        return send;
    }

    public void setSend(Resources send) {
        this.send = send;
    }

    public Resources getReceive() {
        return receive;
    }

    public void setReceive(Resources receive) {
        this.receive = receive;
    }

    public void addToSend(ResourceType type, int num){
        send.getResource(type).addResource(num);
    }

    public void subFromSend(ResourceType type, int num){
        send.getResource(type).subResource(num);
    }

    public void addToReceive(ResourceType type, int num){
        receive.getResource(type).addResource(num);
    }

    public void subFromReceive(ResourceType type, int num){
        receive.getResource(type).subResource(num);
    }
}
