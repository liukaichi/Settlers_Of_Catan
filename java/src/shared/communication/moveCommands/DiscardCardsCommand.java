package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;
import shared.model.bank.resource.Resources;

/**
 * discardCards command object.
 * 
 * @author Cache Staheli
 * @see Resources
 */
public class DiscardCardsCommand extends MoveCommand implements JsonSerializer<DiscardCardsCommand>
{
    /**
     * List of discarded cards.
     */
    private Resources discardedCards;

    /**
     * 
     * @param index
     * @param brickAmount
     * @param oreAmount
     * @param sheepAmount
     * @param wheatAmount
     * @param woodAmount
     */
    public DiscardCardsCommand(PlayerIndex index, int brickAmount, int woodAmount, int sheepAmount, int wheatAmount,
            int oreAmount)
    {
        super(MoveType.discardCards, index);
        discardedCards = new Resources(brickAmount, woodAmount, sheepAmount, wheatAmount, oreAmount);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(DiscardCardsCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        // obj.add("discardedCards", context.serialize(src.discardedCards));
        obj.add("discardedCards",
                src.discardedCards.serialize(src.discardedCards, src.discardedCards.getClass(), context));
        return obj;
    }
}
