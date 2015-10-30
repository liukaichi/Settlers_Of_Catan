/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.data.RobPlayerInfo;
import client.discard.DiscardController;
import client.map.MapController;
import client.turntracker.TurnTrackerController;
import shared.definitions.PieceType;
import shared.definitions.TurnStatus;
import shared.locations.HexLocation;

import java.util.logging.Logger;

/**
 * @author cstaheli
 *         This state contains the methods that are available for use when robbing.
 */
public class RobbingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public RobbingState(ObserverController controller)
    {
        super(controller);
        currentTurnStatus = TurnStatus.Robbing;
    }

    @Override public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return facade.canPlaceRobber(hexLoc);
    }

    @Override public void setTurnTrackerInfo(ObserverController newController)
    {
        TurnTrackerController turnTrackerController = ((TurnTrackerController) newController);
        turnTrackerController.getView().updateGameState("Let's rob some peeps, yo.", false);
    }

    @Override public void updateView()
    {
        if (controller instanceof MapController && facade.isMyTurn())
        {
            ((MapController) controller).getView().startDrop(PieceType.ROBBER, null, false);
        }
        else if (controller instanceof DiscardController)
        {
            DiscardController control = (DiscardController) controller;
            if(control.getWaitView().isModalShowing())
            {
                control.getWaitView().closeModal();
            }
        }
    }

    @Override public void placeRobber(HexLocation hexLoc)
    {
        if (controller instanceof MapController)
        {
            MapController mapController = (MapController) controller;
            mapController.getRobView().setPlayers(facade.getRobPlayerInfo(hexLoc));
            mapController.setRobberLocation(hexLoc);
            mapController.getRobView().showModal();
        }

    }
    @Override
    public void robPlayer(RobPlayerInfo victim, HexLocation location)
    {
        facade.robPlayer(victim, location);
    }
}
