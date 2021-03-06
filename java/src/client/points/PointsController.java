package client.points;

import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.model.ClientModel;
import shared.model.player.Player;

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
     * @param view         Points view
     * @param finishedView Game finished view, which is displayed when the game is over
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

    @Override public void update(Observable o, Object arg)
    {
        ClientFacade facade = ClientFacade.getInstance();
        ClientModel model = (ClientModel) o;
        int clientPlayerPoints = facade.getPlayerPoints(facade.getPlayer().getPlayerIndex());
        getPointsView().setPoints(clientPlayerPoints);
        if (model.getWinner().getIndex() == -1)
        {
            return;
        }
        else
        {
            if (facade.getPlayer(model.getWinner()).getPlayerIndex() == facade.getPlayer().getPlayerIndex())
            {
                getFinishedView().setWinner(facade.getPlayer(model.getWinner()).getName(), true);

            } else
            {
                getFinishedView().setWinner(facade.getPlayer(model.getWinner()).getName(), false);
            }

            if (!getFinishedView().isModalShowing())
            {
                getFinishedView().showModal();
            }
        }

    }

}
