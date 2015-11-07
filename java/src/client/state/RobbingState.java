/**
 *
 */
package client.state;

import client.gui.base.ObserverController;
import client.gui.data.RobPlayerInfo;
import client.gui.discard.DiscardController;
import client.gui.map.IRobView;
import client.gui.map.MapController;
import client.gui.turntracker.TurnTrackerController;
import shared.definitions.PieceType;
import shared.definitions.TurnStatus;
import shared.locations.HexLocation;

/**
 * @author cstaheli
 *         This state contains the methods that are available for use when robbing.
 */
public class RobbingState extends GameplayState
{
    private IRobView robView;
    private MapController mapController;

    public RobbingState(ObserverController controller)
    {
        super(controller);
        if(controller instanceof MapController)
        {
            mapController = (MapController)controller;
            robView = mapController.getRobView();
        }
        currentTurnStatus = TurnStatus.Robbing;
    }

    @Override public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return facade.canPlaceRobber(hexLoc);
    }

    @Override public void setTurnTrackerInfo(ObserverController controller)
    {
        TurnTrackerController turnTrackerController = ((TurnTrackerController) controller);
        turnTrackerController.getView().updateGameState("Let's rob some peeps, yo.", false);
    }

    @Override public void updateView()
    {
        if (controller instanceof MapController && facade.isMyTurn())
        {
            mapController.getView().startDrop(PieceType.ROBBER, null, false);
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
            robView.setPlayers(facade.getRobPlayerInfo(hexLoc));
            mapController.setRobberLocation(hexLoc);
            if(robView.isModalShowing())
                robView.closeModal();
            robView.showModal();
        }

    }
    @Override
    public void robPlayer(RobPlayerInfo victim, HexLocation location)
    {
        if (robView.isModalShowing())
        {
            robView.closeModal();
        }
        facade.robPlayer(victim, location);
    }
}
