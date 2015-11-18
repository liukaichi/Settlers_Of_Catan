package shared.communication.moveCommands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.locations.EdgeLocation;

import java.lang.reflect.Type;

/**
 * Road_Building command object.
 *
 * @author Cache Staheli
 * @see EdgeLocation
 */
public class Road_BuildingCommand extends MoveCommand implements JsonSerializer<Road_BuildingCommand>
{

    /**
     * The location of the road(s) to be built.
     */
    private EdgeLocation spot1, spot2;

    /**
     * Instantiates a Road_BuildingCommand based the given player, and two locations to build the roads.
     *
     * @param playerIndex the index of the player playing the card.
     * @param spot1       the first location to build a road.
     * @param spot2       the second location to build a road.
     */
    public Road_BuildingCommand(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        super(MoveType.Road_Building, playerIndex);
        this.spot1 = spot1;
        this.spot2 = spot2;
    }

    /**
     * Instantiate a Road_BuildingCommand from JSON.
     *
     * @param json JSON of the Road_BuildingCommand.
     */
    public Road_BuildingCommand(String json)
    {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(Road_BuildingCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.add("spot1", src.spot1.serialize(src.spot1, src.spot1.getClass(), context));
        obj.add("spot2", src.spot2.serialize(src.spot2, src.spot2.getClass(), context));
        return obj;
    }

    /**
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
            return AbstractServerFacade.getInstance().roadBuilding(gameID, getPlayerIndex(), this.spot1, this.spot1)
                    .toString();
    }
}
