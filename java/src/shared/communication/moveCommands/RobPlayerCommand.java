package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.locations.HexLocation;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * robPlayer command object
 *
 * @author Cache Staheli
 * @see HexLocation
 */
public class RobPlayerCommand extends MoveCommand implements JsonSerializer<RobPlayerCommand>
{

    /**
     * The information about the player being robbed.
     */
    private PlayerIndex victimIndex;
    /**
     * The location of the robber.
     */
    private HexLocation location;

    private static final Logger LOGGER = Logger.getLogger(RobPlayerCommand.class.getName());

    public RobPlayerCommand()
    {
        super(MoveType.robPlayer, PlayerIndex.NONE);
    }

    /**
     * Instantiates a RobPlayerCommand  with the given player, victim, and new location of the robber.
     *
     * @param playerIndex the index of the player who is robbing.
     * @param victimIndex the idex of the player being robbed.
     * @param location    the location where the robber is now.
     */
    public RobPlayerCommand(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        super(MoveType.robPlayer, playerIndex);
        this.victimIndex = victimIndex;
        this.location = location;
    }

    /**
     * Instantiate a RobPlayerCommand from JSON.
     *
     * @param json JSON of the RobPlayerCommand.
     */
    public RobPlayerCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject robPlayerObject = (JsonObject) parser.parse(json);
        this.victimIndex = PlayerIndex.fromInt(robPlayerObject.get("victimIndex").getAsInt());

        JsonObject locationObject = robPlayerObject.get("location").getAsJsonObject();
        this.location = new HexLocation(locationObject.get("x").getAsInt(), locationObject.get("y").getAsInt());
        setPlayerIndex(PlayerIndex.fromInt(robPlayerObject.get("playerIndex").getAsInt()));
        setType(MoveType.robPlayer);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(RobPlayerCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("victimIndex", src.victimIndex.getIndex());
        JsonObject location = new JsonObject();
        location.addProperty("x", Integer.toString(src.location.getX()));
        location.addProperty("y", Integer.toString(src.location.getY()));
        obj.add("location", location);
        return obj;
    }

    /**
     * Robs the player and moves the robber the new hex location
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing RobPlayerCommand(%d, %d, %s) for game %d", getPlayerIndex().getIndex(), victimIndex.getIndex(),
                location.toString(), gameID));
        String model = AbstractServerFacade.getInstance().robPlayer(gameID, getPlayerIndex(), this.victimIndex, this.location).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;
    }

    public PlayerIndex getVictimIndex()
    {
        return victimIndex;
    }

    public void setVictimIndex(PlayerIndex victimIndex)
    {
        this.victimIndex = victimIndex;
    }

    public HexLocation getLocation()
    {
        return location;
    }
}
