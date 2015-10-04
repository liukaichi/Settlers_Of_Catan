package shared.model.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the list of messages in the chat.
 * 
 * @see MessageLine
 */
public class Chat {

	public Chat(String json)
	{
        lines = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonObject chat = (JsonObject) parser.parse(json);
		JsonArray messageLines = chat.getAsJsonArray("lines");
		for(JsonElement messageLine : messageLines)
		{
			JsonObject messageLineObj = (JsonObject) messageLine;
			
			MessageLine newMessageLine = new MessageLine(messageLineObj.toString());
			lines.add(newMessageLine);
		}
	}

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
     * @param sourceName
     *        the player from whom this message originates, or NONE, if the
     *        server.
     * @param message
     *        the message to add.
     */
    public void addMessageLine(String sourceName, String message)
    {
        MessageLine line = new MessageLine(sourceName, message);
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
        String returnValue = "\"chat\":{";
        for (MessageLine messageLine : lines) {
			returnValue += messageLine.toString();
		}
        returnValue += "},";
        return returnValue; 
    }

}
