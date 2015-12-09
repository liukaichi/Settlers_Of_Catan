package shared.communication.moveCommands;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * sendChat command object.
 *
 * @author Cache Staheli
 */
public class SendChatCommand extends MoveCommand implements JsonSerializer<SendChatCommand>
{
    /**
     * The content of the message.
     */
    private String content;

    private static final Logger LOGGER = Logger.getLogger(SendChatCommand.class.getName());

    /**
     * Instantiates a SendChatCommand with the given player and message.
     *
     * @param sender  the index of the player sending the message.
     * @param content the content of the message being sent.
     */
    public SendChatCommand(PlayerIndex sender, String content)
    {
        super(MoveType.sendChat, sender);
        this.content = content;
    }

    /**
     * Instantiate a SendChatCommand from JSON.
     *
     * @param json JSON of the SendChatCommand.
     */
    public SendChatCommand(String json)
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
    @Override public JsonElement serialize(SendChatCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("content", content);
        return obj;
    }

    /**
     * Calls sendChat method from the Server Facade
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
{
    LOGGER.info(String.format("executing SendChatCommand(%d, %s) for game %d", getPlayerIndex().getIndex(), content, gameID));
    String model = AbstractServerFacade.getInstance().sendChat(gameID, getPlayerIndex(), this.content).toString();
    LOGGER.fine(model);
    persistMe(gameID);
    return model;
}

}
