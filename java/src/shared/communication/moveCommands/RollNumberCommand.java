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
     * @param playerIndex
     * @param number
     */
    public RollNumberCommand(PlayerIndex playerIndex, int number)
    {
        super(MoveType.rollNumber, playerIndex);
        this.number = number;
    }
    /**
     * Instantiate a RollNumberCommand from JSON with the injected facade
     * @param json JSON of the RollNumberCommand
     * @param facade Facade to be used
     */
    public RollNumberCommand(String json, AbstractServerFacade facade)
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
     * @return returns a JSON of the new model
     */
    @Override public String execute()
    {
        return null;
    }
}
