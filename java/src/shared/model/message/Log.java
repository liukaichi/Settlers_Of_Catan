package shared.model.message;

import java.util.List;

import shared.definitions.PlayerIndex;

/**
 * Represents the log of messages in the chat.
 * 
 * @see MessageLine
 */
public class Log
{
    /**
     * An array of MessageLine.
     */
    private List<MessageLine> lines;

    /**
     * Returns a list of the messages.
     * 
     * @return a list of the messages.
     */
    public List<MessageLine> getMessages()
    {
        return lines;
    }

    /**
     * Adds the message to the list.
     * 
     * @param source
     *        the player from whom this message originates, or NONE, if the
     *        server.
     * @param message
     *        the message to add.
     */
    public void addMessageLine(PlayerIndex source, String message)
    {
        MessageLine line = new MessageLine(source, message);
        lines.add(line);
    }

    /**
     * Adds the message to the list.
     * 
     * @param message
     *        the message to add.
     */
    public void addMessageLine(MessageLine message)
    {
        lines.add(message);
    }
    
    @Override
    public String toString()
    {
        String returnValue = "\"log\":{";
        for (MessageLine messageLine : lines) {
			returnValue += messageLine.toString();
		}
        returnValue += "},";
        return returnValue; 
    }

}