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
     * Instantiate a FinishTurnCommand from JSON with the injected facade
     * @param json JSON of the FinishTurnCommand
     * @param facade Facade to be used
     */
    public FinishTurnCommand(String json, AbstractServerFacade facade)
    {
        JsonParser parser = new JsonParser();
        JsonObject finishTurnObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(finishTurnObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(finishTurnObject.getAsJsonPrimitive("playerindex").getAsInt());
    }

    /**
     * Calls FinishTurn method on the Server Facade
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute()
    {
        return null;
    }
}
