package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.model.player.TradeOffer;

/**
 * maritimeTrade command object.
 * 
 * @author Cache Staheli
 * @see TradeRatio
 * @see TradeOffer
 */
public class MaritimeTradeCommand extends MoveCommand implements JsonSerializer<MaritimeTradeCommand>
{
    /**
     * The ratio at which the maritime offer is being extended.
     * 
     */
    private TradeRatio ratio;
    /**
     * What is being offered in the trade.
     */
    private ResourceType inputResource, outputResource;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(MaritimeTradeCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("ratio", src.ratio.getRatio());
        obj.addProperty("inputResource", inputResource.toString().toLowerCase());
        obj.addProperty("outputResource", outputResource.toString().toLowerCase());
        return obj;
    }
}
