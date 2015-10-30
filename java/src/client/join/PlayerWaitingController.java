package client.join;

import client.base.Controller;
import client.base.ObserverController;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import shared.definitions.AIType;
import shared.model.ClientModel;

import java.util.List;
import java.util.Observable;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends ObserverController implements IPlayerWaitingController
{
    private ClientFacade facade;
    private ClientModel model;

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
        ClientModel model = facade.getGameState(-1);
        GameInfo game = model.getGameInfo();
        List<PlayerInfo> players = game.getPlayerInfos();
        getView().setPlayers(players.toArray(new PlayerInfo[players.size()]));
        // Unnecessary to get the AITypes, since there is only one.
        // List<AIType> AITypes = facade.listAI();
        // String[] type = new
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
        this.start();
    }
}
