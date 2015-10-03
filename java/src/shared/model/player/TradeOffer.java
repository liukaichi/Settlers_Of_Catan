package shared.model.player;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.communication.moveCommands.MoveCommand;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resources;

/**
 * This class represents a tradeOffer made by one of the four players
 * 
 * @author amandafisher
 *
 */
public class TradeOffer extends MoveCommand implements JsonSerializer<TradeOffer>
{
    /**
     * player index of the sender and receiver of player trade
     */
    private int sender, receiver;
    /**
     * Object that represents the trade offer made by a player
     */
    private Resources offer;

    public TradeOffer(Player sender, Player receiver)
    {
        this.sender = sender.getPlayerInfo().getPlayerIndex().getIndex();
        this.receiver = receiver.getPlayerInfo().getPlayerIndex().getIndex();
        this.offer = new Resources(false);
    }

    public TradeOffer(int sender, int reciever, int brick, int ore, int sheep, int wheat, int wood)
    {
        this.sender = sender;
        this.receiver = reciever;
        this.offer = new Resources(brick, ore, sheep, wheat, wood);
    }

    @Override
    public String toString()
    {
        //@formatter:off
        /*
         * { 
         *  "type": "offerTrade",
         *  "playerIndex": "integer", 
         *  "offer": { 
         *      "brick": "integer", 
         *      "ore": "integer", 
         *      "sheep": "integer", 
         *      "wheat": "integer",
         *      "wood": "integer" 
         *  }, "receiver": "integer" 
         * }
         */
        // @formatter:on
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

    public TradeOffer(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject) parser.parse(json);
        this.sender = tradeObject.get("playerIndex").getAsInt();
        this.receiver = tradeObject.get("receiver").getAsInt();
        JsonObject newOffer = (JsonObject) tradeObject.get("offer");
        this.offer = new Resources(newOffer.toString());

    }

    public int getSender()
    {
        return sender;
    }

    public void setSender(int sender)
    {
        this.sender = sender;
    }

    public int getReceiver()
    {
        return receiver;
    }

    public void setReceiver(int receiver)
    {
        this.receiver = receiver;
    }

    public Resources getOffer()
    {
        return offer;
    }

    public void addToOffer(ResourceType type, int num)
    {
        offer.getResource(type).addResource(num);
    }

    public void subFromOffer(ResourceType type, int num)
    {
        offer.getResource(type).subResource(num);
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
        JsonObject obj = (JsonObject) serializeCommand(src);
        return obj;
    }
}
