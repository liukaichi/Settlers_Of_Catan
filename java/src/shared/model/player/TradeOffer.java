package shared.model.player;

import client.data.PlayerInfo;
import com.google.gson.*;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resources;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * This class represents a tradeOffer made by one of the four players
 *
 * @author amandafisher
 */
public class TradeOffer implements JsonSerializer<TradeOffer>
{
    /**
     * player index of the sender and receiver of player trade
     */
    private PlayerIndex sender, receiver;
    /**
     * Object that represents the trade offer made by a player
     */
    private HashMap<ResourceType, Hand> resourceHand;
    private Resources offer;

    private TradeOffer()
    {
        resourceHand = new HashMap<>();
        this.offer = new Resources();
    }

    public TradeOffer(Player sender, Player receiver)
    {
        this(sender.getPlayerInfo().getPlayerIndex(), receiver.getPlayerInfo().getPlayerIndex());
    }

    public TradeOffer(PlayerIndex sender, PlayerIndex receiver)
    {
        this();
        this.sender = sender;
        this.receiver = receiver;
    }

    public TradeOffer(PlayerIndex sender, PlayerIndex receiver, int brick, int wood, int sheep, int wheat, int ore)
    {
        this(sender, receiver);
        this.offer = new Resources(brick, wood, sheep, wheat, ore);
    }

    public TradeOffer(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject) parser.parse(json);
        if (tradeObject.has("sender"))
        {
            this.sender = PlayerIndex.fromInt(tradeObject.getAsJsonPrimitive("sender").getAsInt());
        } else
        {
            this.sender = PlayerIndex.fromInt(tradeObject.getAsJsonPrimitive("playerIndex").getAsInt());
        }
        this.receiver = PlayerIndex.fromInt(tradeObject.getAsJsonPrimitive("receiver").getAsInt());
        JsonObject newOffer = (JsonObject) tradeObject.get("offer");
        this.offer = new Resources(newOffer.toString());

    }

    public Hand getResourceHand(ResourceType resource)
    {
        if (resourceHand.containsKey(resource))
            return resourceHand.get(resource);
        else
            return Hand.none;
    }

    public boolean isSending()
    {
        for (ResourceType type : ResourceType.values())
        {
            if (getResourceHand(type).equals(Hand.send) && getOffer(type) > 0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean isReceiving()
    {
        for (ResourceType type : ResourceType.values())
        {
            if (getResourceHand(type).equals(Hand.receive) && getOffer(type) < 0)
            {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((offer == null) ? 0 : offer.hashCode());
        result = prime * result + receiver.hashCode();
        result = prime * result + sender.hashCode();
        return result;
    }

    public void setResourceHand(ResourceType type, Hand value)
    {
        resourceHand.put(type, value);
        this.setOffer(type, 0);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        TradeOffer other = (TradeOffer) obj;
        if (offer == null)
        {
            if (other.offer != null)
                return false;
        } else if (!offer.equals(other.offer))
            return false;
        if (receiver != other.receiver)
            return false;
        return sender == other.sender;
    }

    public int getSender()
    {
        return sender.getIndex();
    }

    public void setSender(int sender)
    {
        this.sender = PlayerIndex.fromInt(sender);
    }

    public PlayerIndex getSenderIndex()
    {
        return sender;
    }

    public PlayerIndex getReceiverIndex()
    {
        return receiver;
    }

    @Override public String toString()
    {
        /*
         * { "type": "offerTrade", "playerIndex": "integer", "offer": { "brick":
         * "integer", "ore": "integer", "sheep": "integer", "wheat": "integer",
         * "wood": "integer" }, "receiver": "integer" }
         */
        JsonObject tradeOffer = new JsonObject();
        {
            tradeOffer.addProperty("type", "offerTrade");
            tradeOffer.addProperty("playerIndex", this.sender.getIndex());

            JsonObject jsonOffer = new JsonObject();
            {
                jsonOffer.addProperty("brick", offer.getAmount(ResourceType.BRICK));
                jsonOffer.addProperty("ore", offer.getAmount(ResourceType.ORE));
                jsonOffer.addProperty("sheep", offer.getAmount(ResourceType.SHEEP));
                jsonOffer.addProperty("wheat", offer.getAmount(ResourceType.WHEAT));
                jsonOffer.addProperty("wood", offer.getAmount(ResourceType.WOOD));
            }
            tradeOffer.add("offer", jsonOffer);
            tradeOffer.addProperty("receiver", this.receiver.getIndex());
        }
        return tradeOffer.toString();
    }

    public int getReceiver()
    {
        return receiver.getIndex();
    }

    public void setReceiver(int receiver)
    {
        this.receiver = PlayerIndex.fromInt(receiver);
    }

    public void addToOffer(ResourceType type, int num)
    {
        offer.getResource(type).addResource(num);
    }

    public void setOffer(ResourceType type, int num)
    {
        offer.setAmount(type, num);
    }

    public void subFromOffer(ResourceType type, int num)
    {
        offer.getResource(type).subResource(num);
    }

    public Resources getOffer()
    {
        return offer;
    }

    public int getOffer(ResourceType type)
    {
        return offer.getAmount(type);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(TradeOffer src, Type srcType, JsonSerializationContext context)
    {
        JsonObject tradeOffer = new JsonObject();
        {
            tradeOffer.addProperty("sender", this.sender.getIndex());
            tradeOffer.addProperty("receiver", this.receiver.getIndex());
            tradeOffer.add("offer", src.offer.serialize(src.offer, src.offer.getClass(), context));
        }
        return tradeOffer;
    }

    public enum Hand
    {
        send, none, receive
    }
}
