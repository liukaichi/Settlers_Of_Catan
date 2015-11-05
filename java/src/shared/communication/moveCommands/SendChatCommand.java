package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
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

    /**
     * Constructs a SendChatCommand from the client
     * @param json, the serialized SendChatCommand from the client
     */
    public SendChatCommand(String json, AbstractServerFacade facade)
    {
        JsonParser parser = new JsonParser();
        JsonObject sendChatCommand = (JsonObject) parser.parse(json);
        String moveType = sendChatCommand.get("type").getAsString();
        int index = sendChatCommand.get("playerIndex").getAsInt();
        String content = sendChatCommand.get("content").getAsString();

        this.type = MoveType.valueOf(moveType);
        this.playerIndex = PlayerIndex.fromInt(index);
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

    /**
     * Calls sendChat method from the Server Facade
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute()
    {
        return null;
    }
}
