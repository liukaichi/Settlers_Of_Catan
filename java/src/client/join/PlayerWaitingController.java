package client.join;

import client.base.Controller;
import client.base.ObserverController;
import client.data.GameInfo;
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
    private ClientFacade facade;
    private ClientModel model;
    private ActionListener action = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LOGGER.info("PLAYER WAITING CONTROLLER TIMER UPDATING");
            if(model.getGameInfo().getPlayerInfos().size() != 4)
            {
                model = facade.getGameState(-1); /// should make it not update the model if players less than 4 and state = -1
                updateView();
            }
            else
            {
                LOGGER.info("PLAYER WAITING CONTROLLER TIMER STOPING");
                timer.stop();
            }
        }
    };
    final private Timer timer = new Timer(2000,action);
    private final static Logger LOGGER = Logger.getLogger(PlayerWaitingController.class.getName());

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
        GameInfo game = model.getGameInfo();
        List<PlayerInfo> players = game.getPlayerInfos();
        getView().setPlayers(players.toArray(new PlayerInfo[players.size()]));
        getView().setAIChoices(new String[] { AIType.LARGEST_ARMY.toString() });
        getView().showModal();
        if (players.size() == 4)
        {
            getView().closeModal();
        }
    }

    /**
     * Do whatever you need to do to generate an AI and and it to the player
     * list;
     */
    @Override public void addAI()
    {
        facade.addAI(AIType.valueOf(getView().getSelectedAI()));
        this.start();
    }

    @Override public void update(Observable observable, Object o)
    {
        this.model = (ClientModel) observable;
        updateView();
    }

}
