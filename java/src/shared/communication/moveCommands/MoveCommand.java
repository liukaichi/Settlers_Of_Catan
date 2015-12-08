package shared.communication.moveCommands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.manager.GameManager;
import shared.communication.CatanCommand;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;

import java.io.Serializable;

/**
 * All move commands objects contain at least the following properties. These
 * properties are described once here and should be assumed for each move type.
 *
 * @author Cache Staheli
 * @see MoveType
 * @see PlayerIndex
 */
public abstract class MoveCommand extends CatanCommand implements Serializable
{
    /**
     * The type of the moveCommand
     */
    protected MoveType type;
    /**
     * The Index of the player performing the command.
     */
    protected PlayerIndex playerIndex;

    /**
     *
     */
    protected MoveCommand()
    {
        playerIndex = PlayerIndex.NONE;
        type = null;
    }

    /**
     * Instantiates the type of command and player index who is sending this command.
     *
     * @param type        the type of command that this is (specified by sub-class)
     * @param playerIndex the index of the player that is executing this command to the server.
     */
    public MoveCommand(MoveType type, PlayerIndex playerIndex)
    {
        this();
        this.type = type;
        this.playerIndex = playerIndex;
    }

    /**
     * Custom Serializer method, based off of interface JsonSerializer<T>
     *
     * @param src this command
     * @return a JsonElement representing this command
     */
    public JsonElement serializeCommand(MoveCommand src)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", src.type.toString());
        obj.addProperty("playerIndex", src.playerIndex.getIndex());
        return obj;
    }

    public PlayerIndex getPlayerIndex()
    {
        return playerIndex;
    }

    public void setType(MoveType type)
    {
        this.type = type;
    }

    public void setPlayerIndex(PlayerIndex playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    protected void persistMe(int gameID)
    {
        GameManager.getInstance().saveCommand(gameID, this);
    }

}
