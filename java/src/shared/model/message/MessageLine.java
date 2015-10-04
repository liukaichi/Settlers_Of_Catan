package shared.model.message;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    private String sourceName;
  
    public MessageLine(String json)
    {
    	JsonParser parser = new JsonParser();
    	JsonObject messageObj = (JsonObject) parser.parse(json);
    	message = messageObj.getAsJsonPrimitive("message").getAsString();
    	sourceName = messageObj.getAsJsonPrimitive("source").getAsString();
    }

    /**
     * Builds the Message Line.
     * 
     * @param sourceName
     *        the player's index, or NONE if from the server.
     * @param message
     *        the text body of this message.
     * @see PlayerIndex
     */
    public MessageLine(String sourceName, String message)
    {
        this.sourceName = sourceName;
        this.message = message;
    }
    
    @Override
    public String toString()
    {
    	String returnString = "\"lines\":[{";
        returnString += "\"message\":"  + message + "\",";
        returnString += "\"source\":"  + sourceName + "\"}]";
        return returnString; 
    }
}
