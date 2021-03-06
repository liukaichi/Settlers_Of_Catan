package shared.model.message;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.PlayerIndex;

import java.io.Serializable;

/**
 * Represents a single message line in the chat.
 */
public class MessageLine implements Serializable
{
    private static final long serialVersionUID = -5273254851544133069L;
    /**
     * The text body of the message.
     */
    private String message;

    /**
     * The index of the player, or NONE if the server.
     */
    private String sourceName;

    /**
     * Initializes a MessageLine from Json.
     *
     * @param json the Json to initialize from.
     */
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
     * @param sourceName the player's index, or NONE if from the server.
     * @param message    the text body of this message.
     * @see PlayerIndex
     */
    public MessageLine(String sourceName, String message)
    {
        this.sourceName = sourceName;
        this.message = message;
    }

    @Override public String toString()
    {
        //@formatter.off
        //          "lines": [
        //    	    {
        //    		 "message": "string",
        //    		 "source": "string"
        //    		 }
        //@formatter.on
        JsonObject line = new JsonObject();
        line.addProperty("message", message);
        line.addProperty("source", sourceName);
        return line.toString();
    }

    public String getMessage()
    {
        return message;
    }

    public String getSourceName()
    {
        return sourceName;
    }

}
