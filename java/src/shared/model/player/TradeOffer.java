package shared.model.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.ResourceType;
import shared.model.bank.resource.Resources;

/**
 * This class represents a tradeOffer made by one of the four players
 * @author amandafisher
 *
 */
public class TradeOffer
{
	/**
	 * player index of the sender and receiver of player trade
	 */
    private int sender, receiver;
    /**
     * Object that represents the trade offer made by a player
     */
    private Resources offer;

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offer == null) ? 0 : offer.hashCode());
		result = prime * result + receiver;
		result = prime * result + sender;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		TradeOffer other = (TradeOffer) obj;
		if (offer == null) {
			if (other.offer != null)
				return false;
		} else if (!offer.equals(other.offer))
			return false;
		if (receiver != other.receiver)
			return false;
		if (sender != other.sender)
			return false;
		return true;
	}

	public TradeOffer(Player sender, Player receiver){
        this.sender = sender.getPlayerInfo().getPlayerIndex().getIndex();
        this.receiver = receiver.getPlayerInfo().getPlayerIndex().getIndex();
        this.offer = new Resources(false);
    }

    public TradeOffer(int sender, int reciever, int brick, int ore, int sheep, int wheat, int wood){
        this.sender = sender;
        this.receiver = reciever;
        this.offer = new Resources(false);
        offer.getResource(ResourceType.BRICK).setAmount(brick);
        offer.getResource(ResourceType.ORE).setAmount(ore);
        offer.getResource(ResourceType.SHEEP).setAmount(sheep);
        offer.getResource(ResourceType.WHEAT).setAmount(wheat);
        offer.getResource(ResourceType.WOOD).setAmount(wood);
    }

    public TradeOffer(String json){
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject)parser.parse(json);
        this.sender = tradeObject.get("playerIndex").getAsInt();
        this.receiver = tradeObject.get("receiver").getAsInt();
        JsonObject newOffer = (JsonObject) tradeObject.get("offer");
        this.offer = new Resources(newOffer.toString());

    }

    public int getSender() {
        return sender;
    }

    @Override
    public String toString()
    {
        /*
        {
  "type": "offerTrade",
  "playerIndex": "integer",
  "offer": {
    "brick": "integer",
    "ore": "integer",
    "sheep": "integer",
    "wheat": "integer",
    "wood": "integer"
  },
  "receiver": "integer"
}
*/
        JsonObject tradeOffer = new JsonObject();
        {
            tradeOffer.addProperty("type", "offerTrade");
            tradeOffer.addProperty("playerIndex", this.sender);

            JsonObject jsonOffer = new JsonObject();
            {
                jsonOffer.addProperty("brick", offer.getResource(ResourceType.BRICK).getAmount());
                jsonOffer.addProperty("ore", offer.getResource(ResourceType.ORE).getAmount());
                jsonOffer.addProperty("sheep", offer.getResource(ResourceType.SHEEP).getAmount());
                jsonOffer.addProperty("wheat", offer.getResource(ResourceType.WHEAT).getAmount());
                jsonOffer.addProperty("wood", offer.getResource(ResourceType.WOOD).getAmount());
            }
            tradeOffer.add("offer", jsonOffer);
            tradeOffer.addProperty("receiver", this.receiver);
        }
        return tradeOffer.toString();
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

    public void addToOffer(ResourceType type, int num){
        offer.getResource(type).addResource(num);
    }

    public void subFromOffer(ResourceType type, int num){
        offer.getResource(type).subResource(num);
    }

    public Resources getOffer()
    {
        return offer;
    }
}
