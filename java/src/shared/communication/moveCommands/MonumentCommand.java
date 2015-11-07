package shared.communication.moveCommands;

import server.facade.AbstractServerFacade;
import shared.definitions.*;

/**
 * Monument command object.
 * 
 * @author Cache Staheli
 *
 */
public class MonumentCommand extends SimpleSerializableCommand
{

    /**
     * Player of playerIndex receives a Victory Point
     * @param playerIndex Index of player receiving a Victory Point
     */
    public MonumentCommand(PlayerIndex playerIndex)
    {
        super(MoveType.Monument, playerIndex);
    }

    /**
     * Instantiate a MonumentCommand from JSON.
     * @param json JSON of the MonumentCommand.
     */
    public MonumentCommand(String json)
    {

    }

    /**
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json representation of the model after the command is executed.
     */
    @Override public String execute(int gameID)
    {
        return null;
    }
}
