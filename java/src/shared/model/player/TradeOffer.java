package shared.model.player;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.model.bank.resource.Resources;

/**
 * This class represents a tradeOffer made by one of the four players
 * 
 * @author amandafisher
 *
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
    private Resources offer;

    public TradeOffer(Player sender, Player receiver)
    {
        this.sender = sender.getPlayerInfo().getPlayerIndex();
        this.receiver = receiver.getPlayerInfo().getPlayerIndex();
        this.offer = new Resources(false);
    }

    public TradeOffer(PlayerIndex sender, PlayerIndex reciever, int brick, int wood, int sheep, int wheat, int ore)
    {
        this.sender = sender;
        this.receiver = reciever;
        this.offer = new Resources(brick, wood, sheep, wheat, ore);
    }

    public TradeOffer(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject) parser.parse(json);
        this.sender = PlayerIndex.fromInt(tradeObject.get("playerIndex").getAsInt());
        this.receiver = PlayerIndex.fromInt(tradeObject.get("receiver").getAsInt());
        JsonObject newOffer = (JsonObject) tradeObject.get("offer");
        this.offer = new Resources(newOffer.toString());

    }

    public int getSender()
    {
        return sender.getIndex();
    }

    @Override
    public String toString()
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
                jsonOffer.addProperty("brick", offer.getResource(ResourceType.BRICK).getAmount());
                jsonOffer.addProperty("ore", offer.getResource(ResourceType.ORE).getAmount());
                jsonOffer.addProperty("sheep", offer.getResource(ResourceType.SHEEP).getAmount());
                jsonOffer.addProperty("wheat", offer.getResource(ResourceType.WHEAT).getAmount());
                jsonOffer.addProperty("wood", offer.getResource(ResourceType.WOOD).getAmount());
            }
            tradeOffer.add("offer", jsonOffer);
            tradeOffer.addProperty("receiver", this.receiver.getIndex());
        }
        return tradeOffer.toString();
    }

    public void setSender(int sender)
    {
        this.sender = PlayerIndex.fromInt(sender);
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

    public void subFromOffer(ResourceType type, int num)
    {
        offer.getResource(type).subResource(num);
    }

    public Resources getOffer()
    {
        return offer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(TradeOffer src, Type srcType, JsonSerializationContext context)
    {
        JsonObject tradeOffer = new JsonObject();
        {
            tradeOffer.addProperty("sender", this.sender.getIndex());
            tradeOffer.addProperty("receiver", this.receiver.getIndex());
            tradeOffer.add("offer", src.offer.serialize(src.offer, src.offer.getClass(), context));
        }
        return tradeOffer;
    }
}
