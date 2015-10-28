/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.data.RobPlayerInfo;
import client.facade.ClientFacade;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.player.TradeOffer;

import java.util.logging.Logger;

/**
 * This is the generic state. This state allows nothing to be done, and returns false for all can-do methods.
 * GameplayState contains methods to set the various states of the controllers so that they can use the allowed/necessary methods.
 */
public abstract class GameplayState
{
    protected ClientFacade facade;
    protected ObserverController controller;
    protected TurnStatus currentTurnStatus;
    private static final Logger LOGGER = Logger.getLogger(GameplayState.class.getName());

    public GameplayState()
    {
        facade = ClientFacade.getInstance();
        currentTurnStatus = null;
    }
    /*
     * Chat Controller methods
     */

    public GameplayState(ObserverController controller)
    {
        this();
        this.controller = controller;

    }

    public void sendMessage(String message)
    {
    }

    /*
     * Dev Card Controller Methods
     */

    public boolean canBuyDevCard()
    {
        return false;
    }

    public boolean canPlayDevCard(DevCardType type)
    {
        return false;
    }

    public void buyDevCard()
    {

    }

    public void playMonopolyCard(ResourceType resource)
    {

    }

    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
    {

    }

    /**
     * Plays a Monument Card.
     */
    public void playMonumentCard()
    {

    }

    public void playRoadBuildingCard(EdgeLocation edge1, EdgeLocation edge2)
    {

    }

    public void playSoldierCard(RobPlayerInfo info, HexLocation location)
    {

    }

    /*
     * Discard Controller methods
     */

    public void discardResources()
    {

    }

    /*
     * Domestic trade controller methods
     */

    public void sendTradeOffer()
    {

    }

    public void acceptTrade(boolean willAccept)
    {

    }

    /*
     * Map controller methods
     */

    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return false;
    }

    public boolean canBuyRoad()
    {
        return false;
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }

    public boolean canBuySettlement()
    {
        return false;
    }

    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }

    public boolean canBuyCity()
    {
        return false;
    }

    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }

    public void placeRoad(EdgeLocation edgeLoc)
    {

    }

    public void placeSettlement(VertexLocation vertLoc)
    {

    }

    public void placeCity(VertexLocation vertLoc)
    {

    }

    public void placeRobber(HexLocation hexLoc)
    {

    }

    public void robPlayer(RobPlayerInfo victim, HexLocation location)
    {
        facade.robPlayer(victim, location);
    }

    /*
     * Martitime Trade Controller methods
     */

    public void makeMaritimeTrade(TradeOffer offer)
    {

    }

    /*
     * Roll Dice Controller methods
     */

    public int rollDice(Dice dice)
    {
        return -1;
    }

    public void updateView()
    {

    }

    /*
     * Turn tracker controller methods
     */

    public void endTurn()
    {

    }

    public void setTurnTrackerInfo(ObserverController newController)
    {

    }

    /**
     * @param controller the controller that is calling this state.
     * @param model      the clientModel that was updated.
     * @param state      the state that the ClientModel's turnTracker contains.
     */
    public void update(ObserverController controller, ClientModel model, Object state)
    {
        if (state instanceof TurnStatus)
        {
            if (currentTurnStatus != null)
            {
                if (currentTurnStatus.equals((TurnStatus) state))
                {
                    return;
                }
            }

            currentTurnStatus = (TurnStatus) state;

            //this commented code determines if it's your turn right now.
            if (model.getTurnTracker().getCurrentTurn() != facade.getClientPlayer().getPlayerIndex())
            {
                switch (currentTurnStatus)
                {
                case Discarding:
                    LOGGER.info("<<<<<<<<<<NOW IN DISCARDING STATE>>>>>>>>>>>");
                    controller.setState(new DiscardingState(controller));
                    break;
                default:
                    LOGGER.info("<<<<<<<<<<NOW IN NOT-MY-TURN STATE>>>>>>>>>>>");
                    controller.setState(new NotMyTurnState(controller));
                    break;
                }
            } else
            {
                switch (currentTurnStatus)
                {
                case Rolling:
                    LOGGER.info("<<<<<<<<<<NOW IN ROLLING STATE>>>>>>>>>>>");
                    controller.setState(new RollingState(controller));

                    break;

                case Playing:
                    LOGGER.info("<<<<<<<<<<NOW IN PLAYING STATE>>>>>>>>>>>");
                    controller.setState(new PlayingState(controller));
                    break;

                case Robbing:
                    LOGGER.info("<<<<<<<<<<NOW IN ROBBING STATE>>>>>>>>>>>");
                    controller.setState(new RobbingState(controller));
                    break;

                case Discarding:
                    LOGGER.info("<<<<<<<<<<NOW IN DISCARDING STATE>>>>>>>>>>>");
                    controller.setState(new DiscardingState(controller));
                    break;

                case FirstRound:
                    LOGGER.info("<<<<<<<<<<NOW IN FIRSTROUND STATE>>>>>>>>>>>");
                    controller.setState(new SetupState(controller, currentTurnStatus));
                    break;

                case SecondRound:
                    LOGGER.info("<<<<<<<<<<NOW IN SECONDROUND STATE>>>>>>>>>>>");
                    controller.setState(new SetupState(controller, currentTurnStatus));
                    break;
                default:
                    break;
                }

            }

        }
        controller.getState().updateView();
    }

    public void increaseAmount(ResourceType resource)
    {
    }

    public void decreaseAmount(ResourceType resource)
    {
    }

    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {

    }

}


