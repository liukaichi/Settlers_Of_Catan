package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.IServerFacade;
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
     * Packages a discard cards command for use by the server.
     * @param index the player discarding cards.
     * @param brickAmount the amount of brick to discard.
     * @param oreAmount the amount of ore to discard.
     * @param sheepAmount the amount of sheep to discard.
     * @param wheatAmount the amount of wheat to discard.
     * @param woodAmount the amount of wood to discard.
     */
    public DiscardCardsCommand(PlayerIndex index, int brickAmount, int woodAmount, int sheepAmount, int wheatAmount,
            int oreAmount)
    {
        super(MoveType.discardCards, index);
        discardedCards = new Resources(brickAmount, woodAmount, sheepAmount, wheatAmount, oreAmount);
    }

    /**
     * Constructor that takes in JSON and parses it.
     * @param json JSON of the DiscardCardsCommand
     */
    public DiscardCardsCommand(String json)
    {

    }

    /**
     * Packages a discard cards command for use by the server.
     * @param index the player discarding cards.
     * @param resources the resources to discard.
     */
    public DiscardCardsCommand(PlayerIndex index, Resources resources)
    {
        super(MoveType.discardCards, index);
        discardedCards = resources;
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


    /**
    * This will discard the amount of cards within the resources object to subtract from the ServerModel
     * @return returns the JSON of the new model after the discards are completed
    */
    @Override public String execute(IServerFacade facade)
    {
        return null;
    }
}
