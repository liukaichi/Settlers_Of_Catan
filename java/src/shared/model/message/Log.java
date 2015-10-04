package shared.model.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

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
    
    public Log(String json)
    {
    	JsonParser parser = new JsonParser();
    	JsonObject log = (JsonObject) parser.parse(json);
    	JsonArray messageLines = log.getAsJsonArray("lines");
    	this.lines = new ArrayList<>();
        for(JsonElement messageLine : messageLines)
    	{
    		JsonObject messageLineObj = (JsonObject) messageLine;
    		
    		MessageLine newMessageLine = new MessageLine(messageLineObj.toString());
    		lines.add(newMessageLine);
    	}
    }

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