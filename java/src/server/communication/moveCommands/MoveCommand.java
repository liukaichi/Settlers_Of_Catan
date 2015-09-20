package server.communication.moveCommands;

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
    private MoveType type;
    /**
     * The Index of the player performing the command.
     */
    private PlayerIndex playerIndex;

    protected MoveType getType()
    {
        return type;
    }

    protected void setType(MoveType type)
    {
        this.type = type;
    }

    protected PlayerIndex getPlayerIndex()
    {
        return playerIndex;
    }

    protected void setPlayerIndex(PlayerIndex playerIndex)
    {
        this.playerIndex = playerIndex;
    }
}
