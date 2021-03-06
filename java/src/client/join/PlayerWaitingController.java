package client.join;

import client.base.ObserverController;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import shared.definitions.AIType;
import shared.model.ClientModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends ObserverController implements IPlayerWaitingController
{
    private final static Logger LOGGER = Logger.getLogger(PlayerWaitingController.class.getName());
    private ClientFacade facade;
    private ClientModel model;
    private ActionListener action = new ActionListener()
    {
        @Override public void actionPerformed(ActionEvent e)
        {
            LOGGER.info("PLAYER WAITING CONTROLLER TIMER UPDATING");
            if (model.getPlayerInfos().size() != 4)
            {
                model = facade
                        .getGameState(-1); /// should make it not update the model if players less than 4 and state = -1
                updateView();
            } else
            {
                LOGGER.info("PLAYER WAITING CONTROLLER TIMER STOPPING");
                timer.stop();
            }
        }
    };
    final private Timer timer = new Timer(3000, action);

    public PlayerWaitingController(IPlayerWaitingView view)
    {
        super(view);
        facade = ClientFacade.getInstance();
        facade.addObserver(this);
    }

    @Override public IPlayerWaitingView getView()
    {

        return (IPlayerWaitingView) super.getView();
    }

    /**
     * make a list of players from the current game getView().setPlayers(that
     * list) getView().setAIChoices(if you happen to have AI) updateView
     */
    @Override public void start()
    {
        model = facade.getGameState(-1);

        updateView();
        LOGGER.info("PLAYER WAITING CONTROLLER TIMER STARTING");
        timer.start();

    }

    private void updateView()
    {
        List<PlayerInfo> players = model.getPlayerInfos();
        if (players.size() != 4)
        {
            getView().setPlayers(players.toArray(new PlayerInfo[players.size()]));
            getView().setAIChoices(new String[] { AIType.LARGEST_ARMY.toString() });
            if (getView().isModalShowing())
                getView().closeModal();
            getView().showModal();
        }
        else
        {
            if (getView().isModalShowing())
            {
                getView().closeModal();
                LOGGER.info("PLAYER WAITING CONTROLLER TIMER STOPPING");
                timer.stop();
            }

            if (!facade.pollerStarted())
            {
                LOGGER.info("PLAYER WAITING CONTROLLER STARTING POLLER");
                facade.startPoller();
            }
        }
    }

    /**
     * Do whatever you need to do to generate an AI and add it to the player
     * list;
     */
    @Override public void addAI()
    {
        facade.addAI(AIType.valueOf(getView().getSelectedAI()));
        model = facade.getGameState(-1);
        updateView();
    }

    @Override public void update(Observable observable, Object o)
    {
        this.model = (ClientModel) observable;
        updateView();
    }

}
