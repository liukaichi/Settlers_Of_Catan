/**
 * 
 */
package client.state;

import client.base.ObserverController;

import java.util.logging.Logger;

/**
 * @author cstaheli
 *
 */
public class NotMyTurnState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public NotMyTurnState(ObserverController controller)
    {
        super(controller);
    }

    @Override
    public void acceptTrade(boolean willAccept)
    {
        LOGGER.fine("State calling facade willAccept(" + willAccept + ").");
        facade.acceptTrade(willAccept);
    }
}
