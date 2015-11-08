package shared.communication.moveCommands;

import java.lang.reflect.Type;

import com.google.gson.*;

import server.facade.AbstractServerFacade;
import shared.definitions.*;

/**
 * rollNumber command object.
 *
 * @author Cache Staheli
 *
 */
public class RollNumberCommand extends MoveCommand implements JsonSerializer<RollNumberCommand>
{
    /**
     * The number rolled.
     */
    private int number;

    /**
     * Instantiates a RollNumberCommand with the given player and number.
     * @param playerIndex the index of the player rolling the number.
     * @param number the number to roll.
     */
    public RollNumberCommand(PlayerIndex playerIndex, int number)
    {
        super(MoveType.rollNumber, playerIndex);
        this.number = number;
    }
    /**
     * Instantiate a RollNumberCommand from JSON.
     * @param json JSON of the RollNumberCommand.
     */
    public RollNumberCommand(String json)
    {

    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(RollNumberCommand src, Type srcType, JsonSerializationContext context)
    {
        JsonObject obj = (JsonObject) serializeCommand(src);
        obj.addProperty("number", number);
        return obj;
    }


    /**
     * Rolls the number for the player and gives all the players the earned resources.
     * Also changes the state to robbing state if a 7 is rolled.
     * @return the Json representation of the model after the command is executed.
     * @param gameID the ID of the game for which to execute the command.
     */
    @Override public String execute(int gameID)
    {
        return null;
    }
}
