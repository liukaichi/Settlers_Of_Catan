package shared.model.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
    private PlayerIndex source;
  
    public MessageLine(String json)
    {
    	JsonParser parser = new JsonParser();
    	JsonObject messageObj = (JsonObject) parser.parse(json);
    	message = messageObj.getAsJsonObject("message").getAsString();
    	int sourceInt = messageObj.getAsJsonObject("source").getAsInt();
    	source = PlayerIndex.fromInt(sourceInt); 
    }

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
    
    @Override
    public String toString()
    {
    	String returnString = "\"lines\":[{";
        returnString += "\"message\":"  + message + "\",";
        returnString += "\"source\":"  + source.toString() + "\"}]";
        return returnString; 
    }
}
