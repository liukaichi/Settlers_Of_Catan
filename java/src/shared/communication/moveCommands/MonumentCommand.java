package shared.communication.moveCommands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;

import java.util.logging.Logger;

/**
 * Monument command object.
 *
 * @author Cache Staheli
 */
public class MonumentCommand extends SimpleSerializableCommand
{
    private static final Logger LOGGER = Logger.getLogger(MonumentCommand.class.getName());

    /**
     * Player of playerIndex receives a Victory Point
     *
     * @param playerIndex Index of player receiving a Victory Point
     */
    public MonumentCommand(PlayerIndex playerIndex)
    {
        super(MoveType.Monument, playerIndex);
    }

    /**
     * Instantiate a MonumentCommand from JSON.
     *
     * @param json JSON of the MonumentCommand.
     */
    public MonumentCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject monumentObject = (JsonObject) parser.parse(json);
        setPlayerIndex(PlayerIndex.fromInt(monumentObject.get("playerIndex").getAsInt()));
        setType(MoveType.Monument);

    }

    /**
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing MonumentCommand(%d) for game %d", getPlayerIndex().getIndex(), gameID));
        String model = AbstractServerFacade.getInstance().monument(gameID, getPlayerIndex()).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;

    }
}
