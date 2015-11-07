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
     * @param playerIndex Index of player receving a Victory Point
     */
    public MonumentCommand(PlayerIndex playerIndex)
    {
        super(MoveType.Monument, playerIndex);
    }

    /**
     * Instantiate a MonumentCommand from JSON with the injected facade
     * @param json JSON of the MonumentCommand
     * @param facade Facade to be used
     */
    public MonumentCommand(String json, AbstractServerFacade facade)
    {

    }

    @Override public String execute()
    {
        return null;
    }
}
