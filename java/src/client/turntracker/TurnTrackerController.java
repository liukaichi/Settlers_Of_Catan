package client.turntracker;

import java.util.Observable;

import client.base.Controller;
import shared.definitions.CatanColor;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController
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

    @Override
    public void endTurn()
    {

    }

    private void initFromModel()
    {
        // <temp>
        getView().setLocalPlayerColor(CatanColor.RED);
        // </temp>
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        // TODO Auto-generated method stub

    }

}
