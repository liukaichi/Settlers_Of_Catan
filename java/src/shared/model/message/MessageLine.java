package shared.model.message;

import shared.definitions.PlayerIndex;

/**
 * Represents a single message line in the chat.
 */
public class MessageLine
{
    /**
     * The text body of the message.
     */
    private String message;

    /**
     * The index of the player, or NONE if the server.
     */
    private PlayerIndex source;

    /**
     * Builds the Message Line.
     * 
     * @param source
     *        the player's index, or NONE if from the server.
     * @param message
     *        the text body of this message.
     * @see PlayerIndex
     */
    public MessageLine(PlayerIndex source, String message)
    {
        this.source = source;
        this.message = message;
    }
}
