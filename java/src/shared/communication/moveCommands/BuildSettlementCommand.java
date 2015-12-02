package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.locations.VertexLocation;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * buildSettlement command object.
 *
 * @author Cache Staheli
 * @see VertexLocation
 */
public class BuildSettlementCommand extends MoveCommand implements JsonSerializer<BuildSettlementCommand>
{

    /**
     * Location of the Settlement.
     */
    private VertexLocation settlementLocation;
    /**
     * Whether this is placed for free (setup).
     */
    private boolean isFree;

    private static final Logger LOGGER = Logger.getLogger(BuildSettlementCommand.class.getName());

    /**
     * Instantiates a BuildSettlementCommand from the player's index, the location, and whether or not it is free.
     *
     * @param playerIndex        the index of the player building the settlement.
     * @param settlementLocation the location of the settlement being built.
     * @param isFree             whether or not the settlement is free (only true during the setup phase).
     */
    public BuildSettlementCommand(PlayerIndex playerIndex, VertexLocation settlementLocation, boolean isFree)
    {
        super(MoveType.buildSettlement, playerIndex);
        this.settlementLocation = settlementLocation;
        this.isFree = isFree;
    }

    /**
     * Instantiate a BuildSettlementCommand from JSON.
     *
     * @param json JSON of the BuildSettlementCommand
     */
    public BuildSettlementCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject buildSettlementObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(buildSettlementObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(buildSettlementObject.getAsJsonPrimitive("playerIndex").getAsInt());
        this.settlementLocation = new VertexLocation(buildSettlementObject.get("vertexLocation").toString());
        this.isFree = buildSettlementObject.get("free").getAsBoolean();

    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(BuildSettlementCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("vertexLocation", src.settlementLocation.serialize(src.settlementLocation, src.settlementLocation.getClass(), context));
        obj.addProperty("free", isFree);
        return obj;
    }

    /**
     * Calls the ServerFacade to build a settlement for the person and at the location specified in this command.
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(
                String.format("executing BuildSettlementCommand(%d, %s, %s) for game %d", getPlayerIndex().getIndex(), settlementLocation.toString(),
                        String.valueOf(isFree), gameID));
        String model = AbstractServerFacade.getInstance().buildSettlement(gameID, getPlayerIndex(), settlementLocation, isFree).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;
    }
}
