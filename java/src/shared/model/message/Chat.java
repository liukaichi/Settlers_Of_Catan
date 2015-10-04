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
	public void addMessageLine(MessageLine messageLine)
	{
		lines.add(messageLine);
	}
    
    @Override
    public String toString()
    {
    	JsonParser parser = new JsonParser();
        // map
        JsonObject chat = new JsonObject();
        {
            JsonArray lines = new JsonArray();
            {
                for ( MessageLine line : this.lines)
                {
                    lines.add(parser.parse(line.toString()));
                }
            }
        }
        return chat.toString(); 
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Chat other = (Chat) obj;
		if (lines == null) {
			if (other.lines.toString() != null)
				return false;
		} else if (!(lines.toString()).equals(other.lines.toString()))
			return false;
		return true;
	}

    
}
