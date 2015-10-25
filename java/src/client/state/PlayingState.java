package client.state;

import client.base.ObserverController;

import java.util.logging.Logger;

/**
 * Created by liukaichi on 10/24/2015.
 */
public class PlayingState extends GameplayState
{
    private static final Logger LOGGER = Logger.getLogger(PlayingState.class.getName());

    public PlayingState(ObserverController controller)
    {
        super(controller);
    }

    @Override public void endTurn()
    {
        LOGGER.info("Ending Turn");
        facade.endTurn();
    }
}
