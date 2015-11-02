package client.state;

import client.base.ObserverController;
import client.data.RobPlayerInfo;
import client.discard.DiscardController;
import client.domestic.DomesticTradeController;
import client.map.MapController;
import client.maritime.MaritimeTradeController;
import client.resources.ResourceBarController;
import client.turntracker.TurnTrackerController;
import shared.definitions.DevCardType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.definitions.exceptions.PlacementException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.TurnTracker;
import shared.model.player.Player;

import java.util.logging.Logger;

/**
 * This state is the main state that allows most gameplay actions.
 */
public class PlayingState extends GameplayState
{
    private static final Logger LOGGER = Logger.getLogger(PlayingState.class.getName());
    private boolean isDevCard;
    private EdgeLocation edgeLoc1;

    public PlayingState(ObserverController controller)
    {
        super(controller);
        currentTurnStatus = TurnStatus.Playing;
        isDevCard = false;
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
        return facade.canPlaceRoad(edgeLoc, false);
    }

    @Override public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return facade.canPlaceSettlement(vertLoc, false);
    }

    @Override public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return facade.canPlaceRobber(hexLoc);
    }

    @Override public void buyDevCard()
    {
        facade.buyDevCard();
    }

    @Override public void placeCity(VertexLocation vertLoc)
    {
        facade.placeCity(vertLoc);
    }

    @Override public void placeRoad(EdgeLocation edgeLoc)
    {
        MapController ctrl = (MapController) controller;

        if(ctrl.isDevCard()){

            if(ctrl.getRoadBuildingLoc1() == null){
                ctrl.setRoadBuildingLoc1(edgeLoc);
                ctrl.getView().placeRoad(edgeLoc, facade.getPlayer().getPlayerColor());
                try {
                    facade.getModel().getMap().forcePlaceRoad(facade.getPlayer().getPlayerIndex(), edgeLoc);
                } catch (PlacementException e) {
                    e.printStackTrace();
                }

                ctrl.startMove(PieceType.ROAD, true, false);
            }
            else
            {
//                ctrl.setRoadBuildingLoc2(edgeLoc);
                facade.playRoadBuildingCard(ctrl.getRoadBuildingLoc1(), edgeLoc);
                ctrl.setIsDevCard(false);
                ctrl.setRoadBuildingLoc1(null);
            }


//            if(edgeLoc1 == null){
//                edgeLoc1 = edgeLoc;
//                ((MapController) controller).setRoadBuildingLoc(edgeLoc);
////                facade.placeRoad(edgeLoc1, true);
//            }
//            else{
////                facade.placeRoad(edgeLoc, true);
//                facade.playRoadBuildingCard(edgeLoc1, edgeLoc);
//                isDevCard = false;
//                edgeLoc1 = null;
//            }
        }
        else
        {
            facade.placeRoad(edgeLoc, false);

        }
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

    @Override public void playMonopolyCard(ResourceType resource)
    {
        facade.playMonopolyCard(resource);
    }

    @Override public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
    {
        facade.playYearOfPlentyCard(resource1, resource2);
    }

    /**
     * Plays a Monument Card.
     */
    @Override public void playMonumentCard()
    {
        facade.playMonumentCard();
    }

    @Override public void playRoadBuildingCard()
    {
        MapController ctrl = (MapController) controller;
        facade.playRoadBuildingCard(ctrl.getRoadBuildingLoc1(), ctrl.getRoadBuildingLoc2());
    }

    @Override public void playSoldierCard(RobPlayerInfo info, HexLocation location)
    {
        isDevCard = true;
        facade.playSoldierCard(info, location);
    }
    @Override
    public void placeRobber(HexLocation hexLoc){

        if (controller instanceof MapController)
        {
            MapController mapController = (MapController) controller;
            mapController.getRobView().setPlayers(facade.getRobPlayerInfo(hexLoc));
            mapController.setRobberLocation(hexLoc);
            mapController.getRobView().showModal();
        }
    }
    @Override
    public void robPlayer(RobPlayerInfo victim, HexLocation location)
    {
        facade.playSoldierCard(victim, location);
    }

    @Override public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {
        if (controller instanceof MapController)
        {
            ((MapController) controller).getView()
                    .startDrop(pieceType, facade.getClientPlayer().getColor(), true);
        }
    }

    @Override public void updateView()
    {
        if (controller instanceof MapController)
        {
            //((MapController) controller).getView().startDrop(PieceType.ROBBER, null, false);
            //((MapController) controller).getRobView().updateView();

        }
        else if (controller instanceof DiscardController)
        {
            DiscardController control = (DiscardController) controller;
            if(control.getWaitView().isModalShowing())
            {
                control.getWaitView().closeModal();
            }
        }
        else if (controller instanceof TurnTrackerController){
            ((TurnTrackerController) controller).updatePlayers(facade.getModel());
        }
        else if (controller instanceof DomesticTradeController)
        {
            DomesticTradeController control = (DomesticTradeController) controller;
            control.getTradeView().enableDomesticTrade(true);
            if(control.getWaitOverlay().isModalShowing())
            {
                control.getWaitOverlay().closeModal();
            }
            else if(control.getTradeOverlay().isModalShowing())
            {
                control.getTradeOverlay().closeModal();
            }
        }
        else if (controller instanceof MaritimeTradeController)
        {
            MaritimeTradeController control = (MaritimeTradeController) controller;
            control.getTradeView().enableMaritimeTrade(true);
        }
        else if (controller instanceof ResourceBarController)
        {
            ResourceBarController control = (ResourceBarController) controller;
            control.enableActions();
        }
    }
}
