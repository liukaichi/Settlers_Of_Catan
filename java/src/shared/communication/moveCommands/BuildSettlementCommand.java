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
     * @param playerIndex
     * @param settlementLocation
     * @param isFree
     */
    public BuildSettlementCommand(PlayerIndex playerIndex, VertexLocation settlementLocation, boolean isFree)
    {
        super(MoveType.buildSettlement, playerIndex);
        this.settlementLocation = settlementLocation;
        this.isFree = isFree;
    }

    /**
     * Instantiate a BuildSettlementCommand from JSON with the injected facade
     * @param json JSON of the BuildSettlementCommand
     * @param facade Facade to be used
     */
    public BuildSettlementCommand(String json, AbstractServerFacade facade)
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

    @Override public String execute()
    {
        return null;
    }
}
