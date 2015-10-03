package shared.model.message;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.PlayerIndex;
import shared.locations.HexLocation;
import shared.model.map.Hex;
import shared.model.map.structure.City;
import shared.model.map.structure.Port;
import shared.model.map.structure.Road;
import shared.model.map.structure.Settlement;

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