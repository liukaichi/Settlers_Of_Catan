package client.turntracker;

import client.base.ObserverController;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import client.state.InitialState;
import shared.definitions.TurnStatus;
import shared.model.ClientModel;
import shared.model.TurnTracker;
import shared.model.player.Player;

import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends ObserverController implements ITurnTrackerController
{
    private static final Logger LOGGER = Logger.getLogger(TurnTrackerController.class.getName());
    private ClientFacade facade;
    private boolean stateButtonEnabled = false;
    private boolean isInitialized = false;

    public TurnTrackerController(ITurnTrackerView view)
    {

        super(view);
        state = new InitialState();
        facade = ClientFacade.getInstance();
    }

    @Override public ITurnTrackerView getView()
    {

        return (ITurnTrackerView) super.getView();
    }

    /**
     * verify you can end turn send server request to end turn,
     * setGameStateButton("waiting for other player", false);
     */
    @Override public void endTurn()
    {
        state.endTurn();
        state.updateView();

    }

    /**
     * getView().setLocalPlayerColor( your Player's color); call
     * getView().updatePlayer(...) to update the player's acheivements and
     * score.
     */

    public void init(){
        List<Player> players = facade.getPlayers();
        TurnTracker turnTracker = facade.getModel().getTurnTracker();

        for(Player p : players){
            getView().initializePlayer(p.getPlayerIndex().getIndex(), p.getName(), p.getPlayerColor());
        }

        isInitialized = true;
        stateButtonEnabled = turnTracker.getStatus().equals(TurnStatus.Playing);
        state.setTurnTrackerInfo(this);
    }

    public void updatePlayers(ClientModel model){
        List<Player> players = facade.getPlayers();
        TurnTracker turnTracker = model.getTurnTracker();

        for(Player p : players){
            boolean hasLargestArmy = (turnTracker.getLargestArmy().equals(p.getPlayerIndex()));
            boolean hasLongestRoad = (turnTracker.getLongestRoad().equals(p.getPlayerIndex()));
            boolean isCurrentTurn = (turnTracker.getCurrentTurn().equals(p.getPlayerIndex()));

            getView().updatePlayer(p.getPlayerIndex().getIndex(), p.getVictoryPoints(), isCurrentTurn, hasLargestArmy, hasLongestRoad);
        }

        stateButtonEnabled = turnTracker.getStatus().equals(TurnStatus.Playing);
        state.setTurnTrackerInfo(this);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    @Override public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;
        state.update(this, model, arg);
        state.updateView();
    }

}
