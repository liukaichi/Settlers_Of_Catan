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
public class RobbingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public RobbingState(ObserverController controller)
    {
        super(controller);
    }
}
