package client.turntracker;

import java.util.Observable;

import client.base.ObserverController;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends ObserverController implements ITurnTrackerController
{

    public TurnTrackerController(ITurnTrackerView view)
    {

        super(view);

        initFromModel();
    }

    @Override
    public ITurnTrackerView getView()
    {

        return (ITurnTrackerView) super.getView();
    }

    /**
     * verify you can end turn send server request to end turn,
     * setGameStateButton("waiting for other player", false);
     */
    @Override
    public void endTurn()
    {

    }

    /**
     * getView().setLocalPlayerColor( your Player's color); call
     * getView().updatePlayer(...) to update the player's acheivements and
     * score.
     */
    private void initFromModel()
    {

    }

    @Override
    public void update(Observable o, Object arg)
    {

    }
}
