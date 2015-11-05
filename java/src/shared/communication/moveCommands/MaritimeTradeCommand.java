package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.AbstractServerFacade;
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

    /**
     * @param playerIndex the player sending the maritime trade.
     * @param ratio the ratio at which the trade is being offered.
     * @param inputResource the resource the player is sending.
     * @param outputResource the resource the player is receiving.
     */
    public MaritimeTradeCommand(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource)
    {
        super(MoveType.maritimeTrade, playerIndex);
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }

    /**
     * Constructs a MaritimeTradeCommand from the client
     * @param json, the serialized MaritimeTradeCommand from the client
     */
    public MaritimeTradeCommand(String json, AbstractServerFacade facade)
    {
        JsonParser parser = new JsonParser();
        JsonObject tradeObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(tradeObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(tradeObject.getAsJsonPrimitive("receiver").getAsInt());
        this.ratio = TradeRatio.valueOf(tradeObject.get("ratio").getAsString());
        this.inputResource = ResourceType.valueOf(tradeObject.get("inputResource").getAsString());
        this.outputResource = ResourceType.valueOf(tradeObject.get("outputResource").getAsString());
    }


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
        obj.addProperty("inputResource", src.inputResource.toString().toLowerCase());
        obj.addProperty("outputResource", src.outputResource.toString().toLowerCase());
        return obj;
    }

    /**
     * Calls maritimeTrade method on the Server Facade
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute()
    {
        return null;
    }
}
