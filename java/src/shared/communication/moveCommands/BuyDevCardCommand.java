package shared.communication.moveCommands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;

import java.util.logging.Logger;

/**
 * buyDevCard command object.
 *
 * @author Cache Staheli
 */
public class BuyDevCardCommand extends SimpleSerializableCommand
{
    private static final Logger LOGGER = Logger.getLogger(BuyDevCardCommand.class.getName());

    /**
     * Player of playerIndex receives a Development Card and loses a sheep, ore and wheat each.
     *
     * @param playerIndex Index of player buying a Development Card
     */
    public BuyDevCardCommand(PlayerIndex playerIndex)
    {
        super(MoveType.buyDevCard, playerIndex);
    }

    /**
     * Instantiate a BuyDevCardCommand from JSON.
     *
     * @param json JSON of the BuyDevCardCommand.
     */
    public BuyDevCardCommand(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject buyDevCardObject = (JsonObject) parser.parse(json);
        this.type = MoveType.valueOf(buyDevCardObject.getAsJsonPrimitive("type").getAsString());
        this.playerIndex = PlayerIndex.fromInt(buyDevCardObject.getAsJsonPrimitive("playerIndex").getAsInt());
    }

    /**
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing BuyDevCardCommand(%d) for game %d", getPlayerIndex().getIndex(), gameID));
        String model = AbstractServerFacade.getInstance().buyDevCard(gameID, getPlayerIndex()).toString();
        LOGGER.fine(model);
        persistMe(gameID);
        return model;
    }
}
