package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.definitions.*;

/**
 * sendChat command object.
 * 
 * @author Cache Staheli
 *
 */
public class SendChatCommand extends MoveCommand implements JsonSerializer<SendChatCommand>
{
    /**
     * The content of the message.
     */
    private String content;

    /**
     * 
     * @param sender
     * @param content
     */
    public SendChatCommand(PlayerIndex sender, String content)
    {
        super(MoveType.sendChat, sender);
        this.content = content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(SendChatCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("content", content);
        return obj;
    }

    @Override public void execute()
    {

    }
}
