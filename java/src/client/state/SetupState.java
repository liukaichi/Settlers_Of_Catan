/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.facade.ClientFacade;
import client.map.MapController;
import shared.definitions.PieceType;
import shared.definitions.TurnStatus;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;

import java.util.logging.Logger;

/**
 * This state is used in the first two rounds of the game, where players place roads and cities only.
 *
 */
public class SetupState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private boolean isFirstTimeThroughRound;

    public SetupState(ObserverController controller, TurnStatus round)
    {
        super(controller);
        currentTurnStatus = round;
        if (controller instanceof MapController)
        {
            playRound();
        }
    }

    private void playRound()
    {
        startMove(PieceType.SETTLEMENT, true, true);
        startMove(PieceType.ROAD, true, true);
    }

    /*
     * Map controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceRoad(shared.definitions.
     * PlayerIndex, shared.locations.EdgeLocation)
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return facade.canPlaceRoad(edgeLoc, true);
    }


    @Override
    public void placeRoad(EdgeLocation edgeLoc)
    {
        facade.placeRoad(edgeLoc, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceSettlement(shared.locations.
     * VertexLocation)
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return facade.canPlaceSettlement(vertLoc, true);
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc)
    {
        facade.placeSettlement(vertLoc, true);
    }

    @Override public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {
        if (controller instanceof MapController)
        {
            ((MapController) controller).getView()
                    .startDrop(pieceType, ClientFacade.getInstance().getClientPlayer().getColor(), false);
        }
    }

    @Override
    public void endTurn()
    {
        facade.endTurn();
    }
}
