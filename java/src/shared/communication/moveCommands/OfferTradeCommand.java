package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.model.player.TradeOffer;

/**
 * offerTrade command object.
 *
 * @author Cache Staheli
 * @see TradeOffer
 */
public class OfferTradeCommand extends MoveCommand implements JsonSerializer<OfferTradeCommand>
{
    /**
     * What you get (+) and what you give (-), as well as with whom.
     */
    private TradeOffer offer;

    /**
     * @param sender
     * @param reciever
     * @param brick
     * @param wood
     * @param sheep
     * @param wheat
     * @param ore
     */
    public OfferTradeCommand(PlayerIndex sender, PlayerIndex reciever, int brick, int wood, int sheep, int wheat,
            int ore)
    {
        super(MoveType.offerTrade, sender);
        offer = new TradeOffer(sender, reciever, brick, wood, sheep, wheat, ore);
        this.type = MoveType.offerTrade;
    }

    /**
     * Constructs a OfferTradeCommand from the client
     * @param json, the serialized OfferTradeCommand from the client
     */
    public OfferTradeCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject offerTradeCommand = (JsonObject) parser.parse(json);
        String moveType = offerTradeCommand.get("type").getAsString();
        int sender = offerTradeCommand.get("playerIndex").getAsInt();
        String content = offerTradeCommand.get("content").getAsString();
        int receiver = offerTradeCommand.get("receiver").getAsInt();

        JsonObject offer = offerTradeCommand.get("offer").getAsJsonObject();
        int brick = offer.get("brick").getAsInt();
        int ore = offer.get("ore").getAsInt();
        int sheep = offer.get("sheep").getAsInt();
        int wheat = offer.get("wheat").getAsInt();
        int wood = offer.get("wood").getAsInt();

        this.type = MoveType.valueOf(moveType);
        this.playerIndex = PlayerIndex.fromInt(sender);
        this.offer = new TradeOffer(playerIndex, PlayerIndex.fromInt(receiver),brick,wood,sheep,wheat,ore);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(OfferTradeCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) src.offer.serialize(src.offer, src.offer.getClass(), context);
        obj.remove("sender");
        obj.addProperty("playerIndex", src.playerIndex.getIndex());
        obj.addProperty("type", src.type.toString());
        return obj;
    }

    /**
     * Calls offerTrade method on the Server Facade
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute()
    {
        return null;
    }
}
