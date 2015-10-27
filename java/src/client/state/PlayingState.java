package client.state;

import client.base.ObserverController;
import client.data.RobPlayerInfo;
import client.map.MapController;
import client.turntracker.TurnTrackerController;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.logging.Logger;

/**
 * This state is the main state that allows most gameplay actions.
 */
public class PlayingState extends GameplayState
{
    private static final Logger LOGGER = Logger.getLogger(PlayingState.class.getName());

    public PlayingState(ObserverController controller)
    {
        super(controller);
        currentTurnStatus = TurnStatus.Playing;
    }

    @Override public void endTurn()
    {
        LOGGER.info("Ending Turn");
        facade.endTurn();
    }
    @Override public void setTurnTrackerInfo(ObserverController newController)
    {
        TurnTrackerController turnTrackerController = ((TurnTrackerController) newController);
        turnTrackerController.getView().updateGameState("Finish this, homie!", true);
    }

    @Override public boolean canBuyDevCard()
    {
        return facade.canBuyDevCard();
    }

    @Override public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return facade.canPlaceCity(vertLoc);
    }

    @Override public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return facade.canPlaceRoad(edgeLoc);
    }

    @Override public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return facade.canPlaceSettlement(vertLoc);
    }

    @Override public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return facade.canPlaceRobber(hexLoc);
    }

    @Override public void placeCity(VertexLocation vertLoc)
    {
        facade.placeCity(vertLoc);
    }

    @Override public void placeRoad(EdgeLocation edgeLoc)
    {
        facade.placeRoad(edgeLoc, false);
    }

    @Override public void placeSettlement(VertexLocation vertLoc)
    {
        facade.placeSettlement(vertLoc, false);
    }

    @Override public boolean canBuyCity()
    {
        return facade.canBuyCity();
    }

    @Override public boolean canBuyRoad()
    {
        return facade.canBuyRoad();
    }

    @Override public boolean canBuySettlement()
    {
        return facade.canBuySettlement();
    }

    @Override public boolean canPlayDevCard(DevCardType type)
    {
        return facade.canPlayDevCard(type);
    }

    @Override
    public void playMonopolyCard(ResourceType resource)
    {

    }

    @Override
    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
    {

    }

    /**
     * Plays a Monument Card.
     */
    @Override
    public void playMonumentCard()
    {

    }

    @Override
    public void playRoadBuildingCard(EdgeLocation edge1, EdgeLocation edge2)
    {

    }

    @Override
    public void playSoldierCard(RobPlayerInfo info, HexLocation location)
    {

    }

    @Override public void updateView()
    {
        if (controller instanceof MapController)
        {
            //((MapController) controller).getView().startDrop(PieceType.ROBBER, null, false);
            //((MapController) controller).getRobView().updateView();

        }
    }
}
