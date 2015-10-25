/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.data.GameInfo;
import client.data.RobPlayerInfo;
import client.facade.ClientFacade;
import shared.communication.Credentials;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

/**
 * @author cstaheli
 */
public abstract class GameplayState
{
    protected ClientFacade facade;
    protected ObserverController controller;
    protected TurnStatus currentStatus;

    public GameplayState()
    {
        facade = ClientFacade.getInstance();
    }
    /*
     * Chat Controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#sendMessage(shared.definitions.
     * PlayerIndex, java.lang.String)
     */

    public GameplayState(ObserverController controller)
    {
        this.controller = controller;
        facade = ClientFacade.getInstance();
    }

    public void sendMessage(String message)
    {
    }

    /*
     * Game History Controller
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#initHistoryFromModel(shared.model.
     * ClientModel)
     */

    public void initHistoryFromModel(ClientModel model)
    {

    }

    /*
     * Dev Card Controller Methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canBuyDevCard(shared.definitions.
     * PlayerIndex)
     */

    public boolean canBuyDevCard()
    {
        // model.canBuyDevCard();
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * client.state.ICatanGameMethods#buyDevCard(shared.definitions.PlayerIndex)
     */

    public void buyDevCard()
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#playMonopolyCard(shared.definitions.
     * PlayerIndex, shared.definitions.ResourceType)
     */

    public void playMonopolyCard(ResourceType resource)
    {
        // proxy.monopoly(new MonopolyCommand(player, resource))
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * client.state.ICatanGameMethods#playYearOfPlentyCard(shared.definitions.
     * PlayerIndex, shared.definitions.ResourceType,
     * shared.definitions.ResourceType)
     */

    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#playOtherDevCard(shared.definitions.
     * PlayerIndex, shared.definitions.DevCardType)
     */

    public void playOtherDevCard(DevCardType type)
    {

    }

    /*
     * Discard Controller methods
     */

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#discardResources(shared.definitions.
     * PlayerIndex, shared.model.bank.resource.Resources)
     */

    public void discardResources(Resources discardedResources)
    {

    }

    /*
     * Domestic trade controller methods
     */

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#sendTradeOffer(shared.definitions.
     * PlayerIndex)
     */

    public void sendTradeOffer()
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#acceptTrade(shared.definitions.
     * PlayerIndex, boolean)
     */

    public void acceptTrade(boolean willAccept)
    {

    }

    /*
     * Join Game Controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#createNewGame(client.data.GameInfo)
     */

    public void createNewGame(GameInfo gameInfo)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#joinGame(client.data.GameInfo)
     */

    public void joinGame(GameInfo gameInfo)
    {

    }

    /*
     * player Waiting Controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#addAI(shared.definitions.AIType)
     */

    public void addAI(AIType type)
    {

    }

    /*
     * Login Controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#signInUser(shared.communication.
     * Credentials)
     */

    public void signInUser(Credentials credentials)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#registerUser(shared.communication.
     * Credentials)
     */

    public void registerUser(Credentials credentials)
    {

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

    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceSettlement(shared.locations.
     * VertexLocation)
     */

    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceCity(shared.locations.
     * VertexLocation)
     */

    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceRobber(shared.locations.
     * HexLocation)
     */

    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * client.state.ICatanGameMethods#placeRoad(shared.definitions.PlayerIndex,
     * shared.locations.EdgeLocation, boolean)
     */

    public void placeRoad(EdgeLocation edgeLoc)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#placeSettlement(shared.locations.
     * VertexLocation)
     */

    public void placeSettlement(VertexLocation vertLoc)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * client.state.ICatanGameMethods#placeCity(shared.locations.VertexLocation)
     */

    public void placeCity(VertexLocation vertLoc)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * client.state.ICatanGameMethods#placeRobber(shared.locations.HexLocation)
     */

    public void placeRobber(HexLocation hexLoc)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#robPlayer(client.data.RobPlayerInfo)
     */

    public void robPlayer(RobPlayerInfo victim)
    {

    }

    /*
     * Martitime Trade Controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * client.state.ICatanGameMethods#makeMaritimeTrade(shared.model.player.
     * TradeOffer)
     */

    public void makeMaritimeTrade(TradeOffer offer)
    {

    }

    /*
     * Points Controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#initPointsFromModel()
     */

    public void initPointsFromModel()
    {

    }

    /*
     * Roll Dice Controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#rollDice()
     */

    public int rollDice(Dice dice)
    {
        // roll dice
        return -1;
    }

    public void showModal()
    {
        return;
    }

    /*
     * Turn tracker controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#endTurn()
     */

    public void endTurn()
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
            boolean newState = true;
            TurnStatus newStatus = (TurnStatus) state;
            if (newStatus.equals(currentStatus))
            {
                newState = false;
            }
            switch (newStatus)
            {
            case Rolling:
                controller.setState(new RollingState(controller));
                break;

            case Playing:
                controller.setState(new PlayingState(controller));
                break;

            case Robbing:
                controller.setState(new RobbingState(controller));
                break;

            case Discarding:
                controller.setState(new DiscardingState(controller));
                break;

            case FirstRound:
                controller.setState(new SetupState(controller, TurnStatus.FirstRound));
                break;

            case SecondRound:
                controller.setState(new SetupState(controller, TurnStatus.SecondRound));
                break;
            default:
                break;
            }
            if (newState){
                showModal();
            }
        }

    }

}
