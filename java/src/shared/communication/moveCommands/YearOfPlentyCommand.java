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
 * Year_of_Plenty command object.
 *
 * @author Cache Staheli
 * @see ResourceType
 *
 */
public class YearOfPlentyCommand extends MoveCommand implements JsonSerializer<YearOfPlentyCommand>
{
    /**
     * The resource(s) you collect.
     */
    private ResourceType resource1, resource2;

    /**
     * Player of playerIndex receives resource1 and resource2 for free.
     * @param playerIndex index of player receiving the resources
     * @param resource1 first free resource
     * @param resource2 second free resource
     */
    public YearOfPlentyCommand(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        super(MoveType.Year_of_Plenty, playerIndex);
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    /**
     * Instantiate a YearOfPlentyCommand from JSON.
     * @param json JSON of the YearOfPlentyCommand
     */
    public YearOfPlentyCommand(String json)
    {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(YearOfPlentyCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("resource1", src.resource1.toString().toLowerCase());
        obj.addProperty("resource2", src.resource2.toString().toLowerCase());
        return obj;
    }

    /**
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID)

    {
        return AbstractServerFacade.getInstance().yearOfPlenty(gameID, getPlayerIndex(), this.resource1, this.resource2)
                .toString();
    }
}
