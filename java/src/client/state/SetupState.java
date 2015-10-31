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

import java.util.logging.Logger;

/**
 * This state is used in the first two rounds of the game, where players place roads and cities only.
 */
public class SetupState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private boolean roundComplete = false;

    public SetupState(ObserverController controller, TurnStatus round)
    {
        super(controller);
        currentTurnStatus = round;
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
    @Override public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return facade.canPlaceRoad(edgeLoc, true);
    }

    @Override public void placeRoad(EdgeLocation edgeLoc)
    {
        facade.placeRoad(edgeLoc, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceSettlement(shared.locations.
     * VertexLocation)
     */
    @Override public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return facade.canPlaceSettlement(vertLoc, true);
    }

    @Override public void placeSettlement(VertexLocation vertLoc)
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

    @Override public void endTurn()
    {
        facade.endTurn();
    }

    @Override public void updateView()
    {
        if (controller instanceof MapController)
        {
            playRound();

        }
    }
    private void playRound()
    {
        switch (currentTurnStatus)
        {
            case FirstRound:
                firstRound();
                break;
            case SecondRound:
                secondRound();
                break;
        }
    }

    private void firstRound() {
        switch (facade.getClientPlayerRoadCount())
        {
            case 0:
                LOGGER.severe("------------------CALLING startMove Road 1---------------");
                startMove(PieceType.ROAD, true, true);
                break;
            case 1:
                switch (facade.getClientPlayerSettlementCount())
                {
                    case 0:
                        if(!roundComplete && facade.getClientPlayerRoadCount() == 1) {
                            roundComplete = true;
                            LOGGER.severe("------------------CALLING startMove Settlement 1---------------");
                            startMove(PieceType.SETTLEMENT, true, true);
                        }
                        break;
                    case 1:
                        this.endTurn();
                        break;
                }
                break;
        }
    }

    private void secondRound() {
        switch (facade.getClientPlayerRoadCount())
        {
            case 1:
                    LOGGER.severe("------------------CALLING startMove Road 2---------------");
                    startMove(PieceType.ROAD, true, true);
                break;
            case 2:
                switch (facade.getClientPlayerSettlementCount())
                {
                    case 1:
                        if(!roundComplete && facade.getClientPlayerRoadCount() == 2) {
                            roundComplete = true;
                            LOGGER.severe("------------------CALLING startMove Settlement 2---------------");
                            startMove(PieceType.SETTLEMENT, true, true);
                        }
                        break;
                    case 2:
                        this.endTurn();
                        break;
                }
                break;
        }
    }
}
