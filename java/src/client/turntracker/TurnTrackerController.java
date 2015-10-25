package client.turntracker;

import client.base.ObserverController;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import client.state.InitialState;
import shared.definitions.TurnStatus;
import shared.model.ClientModel;
import shared.model.TurnTracker;
import shared.model.player.Player;

import java.util.Observable;
import java.util.logging.Logger;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends ObserverController implements ITurnTrackerController
{
    private static final Logger LOGGER = Logger.getLogger(TurnTrackerController.class.getName());
    private ClientFacade facade;

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
    }

    /**
     * getView().setLocalPlayerColor( your Player's color); call
     * getView().updatePlayer(...) to update the player's acheivements and
     * score.
     */
    private void initFromModel(ClientModel model)
    {
        TurnTracker turnTracker = model.getTurnTracker();
        PlayerInfo clientPlayer = facade.getClientPlayer();

        getView().setLocalPlayerColor(clientPlayer.getColor());

        for (Player player : model.getGameInfo().getPlayers())
        {
            getView().initializePlayer(player.getPlayerIndex().getIndex(), player.getName(), player.getPlayerColor());

            boolean hasLargestArmy = (turnTracker.getLargestArmy().equals(player.getPlayerIndex()));
            boolean hasLongestRoad = (turnTracker.getLargestArmy().equals(player.getPlayerIndex()));
            boolean isCurrentTurn = (turnTracker.getCurrentTurn().equals(player.getPlayerIndex()));

            getView().updatePlayer(player.getPlayerIndex().getIndex(), player.getVictoryPoints(), isCurrentTurn,
                    hasLargestArmy, hasLongestRoad);
        }

        // TODO This might need to include more statuses
        boolean stateButtonEnabled = turnTracker.getStatus().equals(TurnStatus.Playing);
        LOGGER.info(turnTracker.getStatus().toString());
        String buttonText = turnTracker.getStatus().toString();

        stateButtonEnabled = false;
        switch (turnTracker.getStatus())
        {
        case Rolling:
            buttonText = "Rolling the Dice, homeslice!";
            break;

        case Playing:
            buttonText = "Finish this, homie!";
            stateButtonEnabled = true;
            break;

        case Robbing:
            buttonText = "Let's rob some stuff from people";
            break;

        default:
            break;
        }


        getView().updateGameState(buttonText, stateButtonEnabled);

    }

    @Override public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;

        this.initFromModel(model);
        state.update(this, model, model.getTurnTracker().getStatus());

    }
}
