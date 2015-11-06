package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.*;

/**
 * Monopoly command object.
 * 
 * @author Cache Staheli
 * @see ResourceType
 *
 */
public class MonopolyCommand extends MoveCommand implements JsonSerializer<MonopolyCommand>
{
    /**
     * The resource the Monopoly Card applies to.
     */
    private ResourceType resource;

    /**
     * The player takes all of one type of resource from all other players
     * @param playerIndex Index of Player playing the card
     * @param resource Resource to take from all other players
     */
    public MonopolyCommand(PlayerIndex playerIndex, ResourceType resource)
    {

        super(MoveType.Monopoly, playerIndex);
        this.resource = resource;
    }

    /**
     * Instantiate a MonopolyCommand from JSON with the injected facade
     * @param json JSON of the MonopolyCommand
     * @param facade Facade to be used
     */
    public MonopolyCommand(String json, AbstractServerFacade facade)
    {

    }


    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(MonopolyCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("resource", src.resource.toString().toLowerCase());
        return obj;
    }

    @Override public String execute()
    {
        return null;
    }
}
