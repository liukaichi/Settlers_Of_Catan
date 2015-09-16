package shared.model;

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
     * The source of the message.
     * The source can be a player or the server.
     */
    private String source;
}
