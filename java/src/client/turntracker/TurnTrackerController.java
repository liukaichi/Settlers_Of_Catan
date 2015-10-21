package client.turntracker;

import client.base.ObserverController;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import shared.definitions.TurnStatus;
import shared.model.ClientModel;
import shared.model.TurnTracker;
import shared.model.player.Player;

import java.util.Observable;

/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends ObserverController implements ITurnTrackerController
{
    private ClientFacade facade;

    public TurnTrackerController(ITurnTrackerView view)
    {

        super(view);

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

        getView().initializePlayer(clientPlayer.getPlayerIndex().getIndex(), clientPlayer.getName(),
                clientPlayer.getColor());

        getView().setLocalPlayerColor(clientPlayer.getColor());


        for (Player player : model.getGameInfo().getPlayers())
        {
            boolean hasLargestArmy = (turnTracker.getLargestArmy().equals(player.getPlayerIndex()));
            boolean hasLongestRoad = (turnTracker.getLargestArmy().equals(player.getPlayerIndex()));
            boolean isCurrentTurn = (turnTracker.getCurrentTurn().equals(player.getPlayerIndex()));

            getView().updatePlayer(player.getPlayerIndex().getIndex(), player.getVictoryPoints(), isCurrentTurn,
                    hasLargestArmy, hasLongestRoad);
        }

        // TODO This might need to include more statuses
        boolean stateButtonEnabled = turnTracker.getStatus().equals(TurnStatus.Playing);
        getView().updateGameState(turnTracker.getStatus().toString(),stateButtonEnabled);

    }

    @Override public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;

        this.initFromModel(model);

    }
}
