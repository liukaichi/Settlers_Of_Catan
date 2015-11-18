package shared.communication.moveCommands;

import server.facade.AbstractServerFacade;
import shared.definitions.MoveType;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;

/**
 * buyDevCard command object.
 *
 * @author Cache Staheli
 */
public class BuyDevCardCommand extends SimpleSerializableCommand
{

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

    }

    /**
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID) throws CatanException
    {
        return AbstractServerFacade.getInstance().buyDevCard(gameID, getPlayerIndex()).toString();

    }
}
