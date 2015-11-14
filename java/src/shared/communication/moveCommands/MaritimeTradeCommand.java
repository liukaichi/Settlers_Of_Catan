package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.model.player.TradeOffer;

import java.lang.reflect.Type;

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
     * Instantiates a MaritimeTradeCommand with the given player, ratio of trade, and which resources area being traded.
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
     * Instantiate a MaritimeTradeCommand from JSON.
     * @param json JSON of the MaritimeTradeCommand.
     */
    public MaritimeTradeCommand(String json)
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
     * @return the Json representation of the model after the command is executed.
     * @param gameID the ID of the game for which to execute the command.
     */
    @Override public String execute(int gameID)
    {
        return AbstractServerFacade.getInstance().maritimeTrade(gameID, getPlayerIndex(), this.ratio, this.inputResource, this.outputResource).toString();

    }
}
