package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

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
     * Location of the Settlement.
     */
    private VertexLocation settlementLocation;
    /**
     * Whether this is placed for free (setup).
     */
    private boolean isFree;

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
        obj.add("vertexLocation", context.serialize(src.settlementLocation));
        obj.addProperty("free", isFree);
        return obj;
    }

}
