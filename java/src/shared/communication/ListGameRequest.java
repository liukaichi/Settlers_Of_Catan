package shared.communication;

import server.facade.AbstractServerFacade;
import shared.definitions.exceptions.CatanException;

import java.util.logging.Logger;

/**
 * Can be executed to list the games in the server.
 *
 * @author Cache Staheli
 */
public class ListGameRequest implements CatanCommand
{
    private static final Logger LOGGER = Logger.getLogger(ListGameRequest.class.getName());

    /**
     * Creates a ListGameRequest.
     */
    public ListGameRequest(String json)
    {
        //do nothing
    }

    /**
     * Calls createGame method on the Server Facade
     *
     * @param gameID possibly not used.
     * @return Json String representing the current state of the Server Model
     */
    @Override public String execute(int gameID) throws CatanException
    {
        LOGGER.info(String.format("executing ListGameRequest() for game %d", gameID));
        String games = AbstractServerFacade.getInstance().listGames().toString();
        LOGGER.fine(games);
        return games;
    }
}
