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
public class DiscardingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public DiscardingState(ObserverController controller)
    {
        super(controller);
    }

    @Override public void showModal()
    {
        super.showModal();
    }
}
