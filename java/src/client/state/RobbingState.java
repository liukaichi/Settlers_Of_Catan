/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.data.RobPlayerInfo;
import shared.locations.HexLocation;

import java.util.logging.Logger;

/**
 * This state contains the methods that are available for use when robbing.
 */
public class RobbingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public RobbingState(ObserverController controller)
    {
        super(controller);
    }

    @Override public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return facade.canPlaceRobber(hexLoc);
    }

    @Override public void placeRobber(HexLocation hexLoc)
    {
        facade.placeRobber(hexLoc);
    }

    @Override public void robPlayer(RobPlayerInfo victim, HexLocation hexLocation)
    {
        //TODO we talked about how the map will know the robber's location at this point. So, is this how we want it done,
        // or is this incorrect?
        facade.robPlayer(victim, hexLocation);
    }
}
