package client.points;

import java.util.Observable;

import client.base.ObserverController;

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

        initFromModel();
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

    private void initFromModel()
    {
        // <temp>
        getPointsView().setPoints(5);
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
