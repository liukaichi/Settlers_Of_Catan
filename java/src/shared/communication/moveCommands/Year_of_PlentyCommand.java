package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * Year_of_Plenty command object.
 *
 * @author Cache Staheli
 * @see ResourceType
 */
public class Year_of_PlentyCommand extends MoveCommand implements JsonSerializer<Year_of_PlentyCommand>
{
    /**
     * The resource(s) you collect.
     */
    private ResourceType resource1, resource2;

    private static final Logger LOGGER = Logger.getLogger(Year_of_PlentyCommand.class.getName());

    /**
     * Player of playerIndex receives resource1 and resource2 for free.
     *
     * @param playerIndex index of player receiving the resources
     * @param resource1   first free resource
     * @param resource2   second free resource
     */
    public Year_of_PlentyCommand(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        super(MoveType.Year_of_Plenty, playerIndex);
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    /**
     * Instantiate a Year_of_PlentyCommand from JSON.
     *
     * @param json JSON of the Year_of_PlentyCommand
     */
    public Year_of_PlentyCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject Year_of_PlentyObject = (JsonObject) parser.parse(json);
        this.resource1 = ResourceType.valueOf(Year_of_PlentyObject.get("resource1").getAsString().toUpperCase());
        this.resource2 = ResourceType.valueOf(Year_of_PlentyObject.get("resource2").getAsString().toUpperCase());
        setPlayerIndex(PlayerIndex.fromInt(Year_of_PlentyObject.get("playerIndex").getAsInt()));
        setType(MoveType.Year_of_Plenty);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(Year_of_PlentyCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("resource1", src.resource1.toString().toLowerCase());
        obj.addProperty("resource2", src.resource2.toString().toLowerCase());
        return obj;
    }

    /**
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing Year_of_PlentyCommand(%d, %s, %s) for game %d", getPlayerIndex().getIndex(), resource1.toString(),
                resource2.toString(), gameID));
        String model = AbstractServerFacade.getInstance().yearOfPlenty(gameID, getPlayerIndex(), this.resource1, this.resource2).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;
    }
}
