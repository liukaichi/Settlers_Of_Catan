package shared.communication.moveCommands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;

import java.lang.reflect.Type;

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
     * Instantiate a MonopolyCommand from JSON.
     * @param json JSON of the MonopolyCommand
     */
    public MonopolyCommand(String json)
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

    /**
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID)
    {
        return AbstractServerFacade.getInstance().monopoly(gameID, getPlayerIndex(), this.resource).toString();

    }
}
