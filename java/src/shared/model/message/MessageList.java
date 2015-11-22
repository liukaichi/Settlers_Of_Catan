package shared.model.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of messages.
 *
 * @see Chat
 * @see Log
 */
public abstract class MessageList
{
    private List<MessageLine> lines;

    public MessageList()
    {
        lines = new ArrayList<>();
    }

    public MessageList(String json)
    {
        lines = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonObject chat = (JsonObject) parser.parse(json);
        JsonArray messageLines = chat.getAsJsonArray("lines");
        for (JsonElement messageLine : messageLines)
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
     * @param sourceName the player from whom this message originates, or NONE, if the
     *                   server.
     * @param message    the message to add.
     */
    public void addMessageLine(String sourceName, String message)
    {
        MessageLine line = new MessageLine(sourceName, message);
        lines.add(line);
    }

    /**
     * Adds a line to the log.
     *
     * @param messageLine the message line to add.
     */
    public void addMessageLine(MessageLine messageLine)
    {
        lines.add(messageLine);
    }

    @Override public String toString()
    {
        JsonParser parser = new JsonParser();
        JsonObject messageLines = new JsonObject();
        {
            JsonArray lines = new JsonArray();

            for (MessageLine line : this.lines)
            {
                lines.add(parser.parse(line.toString()));
            }

            messageLines.add("lines", lines);
        }
        return messageLines.toString();
    }

    /**
     * Returns the amount of messages in the log.
     *
     * @return the amount of messages in the log.
     */
    public int size()
    {
        return lines.size();
    }
}
