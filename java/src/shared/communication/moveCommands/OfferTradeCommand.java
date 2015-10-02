package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.model.player.TradeOffer;

/**
 * offerTrade command object.
 * 
 * @author Cache Staheli
 * @see TradeOffer
 *
 */
public class OfferTradeCommand extends MoveCommand implements JsonSerializer<OfferTradeCommand>
{
    /**
     * What you get (+) and what you give (-), as well as with whom.
     */
    private TradeOffer offer;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(OfferTradeCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        return obj;
    }
}
