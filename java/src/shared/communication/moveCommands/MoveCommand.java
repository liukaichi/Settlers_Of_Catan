package shared.communication.moveCommands;

import com.google.gson.*;

import shared.definitions.*;

/**
 * All move commands objects contain at least the following properties. These
 * properties are described once here and should be assumed for each move type.
 * 
 * @author Cache Staheli
 * @see MoveType
 * @see PlayerIndex
 */
public abstract class MoveCommand
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
    private MoveCommand()
    {
        playerIndex = PlayerIndex.NONE;
        type = null;
    }

    /**
     * @param type
     * @param playerIndex
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
     * @param src
     *        this command
     * @return a JsonElement representing this command
     */
    public JsonElement serializeCommand(MoveCommand src)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", src.type.toString());
        obj.addProperty("playerIndex", src.playerIndex.getIndex());
        return obj;
    }
}
