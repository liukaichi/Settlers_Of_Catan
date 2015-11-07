package shared.communication.moveCommands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.facade.AbstractServerFacade;
import shared.definitions.*;

/**
 * finishTurn command object.
 * 
 * @author Cache Staheli
 *
 */
public class FinishTurnCommand extends SimpleSerializableCommand
{
    /**
     * @param playerIndex
     */
    public FinishTurnCommand(PlayerIndex playerIndex)
    {
        super(MoveType.finishTurn, playerIndex);
    }

    /**
     * Instantiate a FinishTurnCommand from JSON.
     * @param json JSON of the FinishTurnCommand.
     */
    public FinishTurnCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject finishTurnObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(finishTurnObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(finishTurnObject.getAsJsonPrimitive("playerindex").getAsInt());
    }

    /**
     * Calls FinishTurn method on the Server Facade
     * @return the Json representation of the model after the command is executed.
     * @param gameID the ID of the game for which to execute the command.
     */
    @Override public String execute(int gameID)
    {
        return null;
    }
}
