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

    @Override public String execute()
    {
        return null;
    }
}
