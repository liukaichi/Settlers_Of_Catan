package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.model.player.TradeOffer;

/**
 * offerTrade command object.
 * 
 * @author Cache Staheli
 * @see OfferTradeCommand
 *
 */
public class OfferTradeCommand extends MoveCommand implements JsonSerializer<OfferTradeCommand>
{
    /**
     * What you get (+) and what you give (-), as well as with whom.
     */
    private OfferTradeCommand offer;

    /**
     * 
     * @param sender
     * @param reciever
     * @param brick
     * @param ore
     * @param sheep
     * @param wheat
     * @param wood
     */
    public OfferTradeCommand(int sender, int reciever, int brick, int ore, int sheep, int wheat, int wood)
    {
        // TODO fix this. This is not correct.
        super(MoveType.offerTrade, PlayerIndex.NONE);
        offer = new OfferTradeCommand(sender, reciever, brick, ore, sheep, wheat, wood);
        this.type = MoveType.offerTrade;
    }

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
