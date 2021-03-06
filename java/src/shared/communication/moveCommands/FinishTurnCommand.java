package shared.communication.moveCommands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;

import java.util.logging.Logger;

/**
 * finishTurn command object.
 *
 * @author Cache Staheli
 */
public class FinishTurnCommand extends SimpleSerializableCommand
{
    private static final Logger LOGGER = Logger.getLogger(FinishTurnCommand.class.getName());
    private static final long serialVersionUID = -2957735873047029876L;

    /**
     * Instantiates a FinishTurnCommand from the given PlayerIndex.
     *
     * @param playerIndex the index of the player finishing the turn.
     */
    public FinishTurnCommand(PlayerIndex playerIndex)
    {
        super(MoveType.finishTurn, playerIndex);
    }

    /**
     * Instantiate a FinishTurnCommand from JSON.
     *
     * @param json JSON of the FinishTurnCommand.
     */
    public FinishTurnCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject finishTurnObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(finishTurnObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(finishTurnObject.getAsJsonPrimitive("playerIndex").getAsInt());
    }

    /**
     * Calls FinishTurn method on the Server Facade
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing FinishTurnCommand(%d) for game %d", getPlayerIndex().getIndex(), gameID));
        String model = AbstractServerFacade.getInstance().finishTurn(gameID, getPlayerIndex()).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;

    }
}
