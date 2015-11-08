package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.AbstractServerFacade;
import shared.definitions.*;
import shared.locations.VertexLocation;

/**
 * buildSettlement command object.
 *
 * @author Cache Staheli
 * @see VertexLocation
 *
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

    /**
     * Instantiates a BuildSettlementCommand from the player's index, the location, and whether or not it is free.
     * @param playerIndex the index of the player building the settlement.
     * @param settlementLocation the location of the settlement being built.
     * @param isFree whether or not the settlement is free (only true during the setup phase).
     */
    public BuildSettlementCommand(PlayerIndex playerIndex, VertexLocation settlementLocation, boolean isFree)
    {
        super(MoveType.buildSettlement, playerIndex);
        this.settlementLocation = settlementLocation;
        this.isFree = isFree;
    }

    /**
     * Instantiate a BuildSettlementCommand from JSON.
     * @param json JSON of the BuildSettlementCommand
     */
    public BuildSettlementCommand(String json)
    {

    }



    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(BuildSettlementCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("vertexLocation",
                src.settlementLocation.serialize(src.settlementLocation, src.settlementLocation.getClass(), context));
        obj.addProperty("free", isFree);
        return obj;
    }

    /**
     * Calls the ServerFacade to build a settlement for the person and at the location specified in this command.
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID)
    {
        return null;
    }
}
