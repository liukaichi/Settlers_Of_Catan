package client.points;

import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.model.ClientModel;

import java.util.Observable;

/**
 * Implementation for the points controller
 */
public class PointsController extends ObserverController implements IPointsController
{

    private IGameFinishedView finishedView;

    /**
     * PointsController constructor
     * 
     * @param view
     *        Points view
     * @param finishedView
     *        Game finished view, which is displayed when the game is over
     */
    public PointsController(IPointsView view, IGameFinishedView finishedView)
    {

        super(view);

        setFinishedView(finishedView);

    }

    public IPointsView getPointsView()
    {

        return (IPointsView) super.getView();
    }

    public IGameFinishedView getFinishedView()
    {
        return finishedView;
    }

    public void setFinishedView(IGameFinishedView finishedView)
    {
        this.finishedView = finishedView;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        ClientFacade facade = ClientFacade.getInstance();
        int points = facade.getPlayerPoints(facade.getPlayer().getPlayerIndex());
        getPointsView().setPoints(points);
        
        if (points == 10)
        {
        	getFinishedView(); 
        }
    }

}
