/**
 * 
 */
package client;

import client.data.*;
import shared.communication.Credentials;
import shared.definitions.*;
import shared.locations.*;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

/**
 * @author cstaheli
 *
 */
public interface ICatanGameMethods
{

    /*
     * Chat Controller methods
     */
    /**
     * Sends a message to another player.
     * 
     * @param message
     *        the message to send.
     */
    void sendMessage(PlayerIndex player, String message);

    /*
     * Game History Controller
     */
    /**
     * Initializes the game history from a model.
     */
    void initHistoryFromModel(ClientModel model);

    /*
     * Dev Card Controller Methods
     */
    /**
     * Determines if the player can buy a dev card.
     * 
     * @return whether or not the player can buy a dev card.
     */
    boolean canBuyDevCard(PlayerIndex player);

    /**
     * Purchases a Development Card. This will take the card from the bank, and
     * adds it to the player' hand.
     */
    void buyDevCard(PlayerIndex player);

    /**
     * Plays a Monopoly Card.
     * 
     * @param resource
     *        the type of resource the player is getting the monopoly on.
     */
    void playMonopolyCard(PlayerIndex player, ResourceType resource);

    /**
     * Plays a Year of Plenty Card.
     * 
     * @param resource1
     *        The first resource.
     * @param resource2
     *        The second resource.
     */
    void playYearOfPlentyCard(PlayerIndex player, ResourceType resource1, ResourceType resource2);

    /**
     * Plays any other kind of Development Card.
     */
    void playOtherDevCard(PlayerIndex player, DevCardType type);

    /**
     * Discards the amount of resources set with the increase/decrease methods
     * 
     * @param discardedResources
     *        the list of resources to discard.
     */
    void discardResources(PlayerIndex player, Resources discardedResources);

    /**
     * Sends a trade offer to a player.
     */
    void sendTradeOffer(PlayerIndex player);

    /**
     * Used for accepting/rejecting a trade.
     * 
     * @param willAccept
     *        Whether or not the player will accept the trade.
     */
    void acceptTrade(PlayerIndex player, boolean willAccept);

    /*
     * Join Game Controller methods
     */
    /**
     * Creates a new game.
     */
    void createNewGame(GameInfo gameInfo);

    /**
     * Joins an already existent game.
     */
    void joinGame(GameInfo gameInfo);

    /*
     * player Waiting Controller methods
     */
    /**
     * Adds an AI to the game.
     * 
     * @param type
     *        the type of the AI (LARGEST_ARMY is the only supported value.)
     */
    void addAI(AIType type);

    /*
     * Login Controller methods
     */
    /**
     * Signs in the player with the given credentials.
     * 
     * @param credentials
     *        the player's credentials
     */
    void signInUser(Credentials credentials);

    /**
     * Registers the user with the given credentials.
     * 
     * @param credentials
     *        the credentials of the user registering.
     */
    void registerUser(Credentials credentials);

    /*
     * Map controller methods
     */
    /**
     * Checks to see if the player meets the conditions to place a road
     * 
     * @pre place road is called
     * @post place road continues
     * @param edgeLoc
     *        the location of the Road
     * @return boolean - true if the player has the required resources and the
     *         location is vacant and the player owns a settlement or city at a
     *         neighboring vertex location
     */
    boolean canPlaceRoad(PlayerIndex player, EdgeLocation edgeLoc);

    /**
     * Checks to see if the player meets the condition to place a settlement
     * 
     * @pre place settlement is called
     * @post place settlement continues
     * @param vertLoc
     *        the location of the Vertex
     * @return boolean - true if player has the required resources and the
     *         location is 2 edges or more from another settlement
     */
    boolean canPlaceSettlement(VertexLocation vertLoc);

    /**
     * Checks to see if the player meets the condition to place a city
     * 
     * @pre place city is called
     * @post place city continues
     * @param vertLoc
     *        the location of the Vertex
     * @return boolean - true if player has the required resources and the
     *         player owns the settlement at that location
     */
    boolean canPlaceCity(VertexLocation vertLoc);

    /**
     * Checks to see if player meets the condition to move the robber
     * 
     * @pre place robber is called
     * @post place city continues
     * @param hexLoc
     *        the location of the hex
     * @return true if a seven has been rolled and the location is viable
     */
    boolean canPlaceRobber(HexLocation hexLoc);

    /**
     * player purchases and places a road
     * 
     * @pre player clicks on a location to place road
     * @post player met conditions and road is on map
     * @param edgeLoc
     *        the location of the road
     */
    void placeRoad(PlayerIndex player, EdgeLocation edgeLoc, boolean isFree);

    /**
     * player purchases and places a settlement
     * 
     * @pre player clicks on a location to place a settlement
     * @post player met conditions and settlement is now on map
     * @param vertLoc
     *        the location of the Settlement
     */
    void placeSettlement(VertexLocation vertLoc);

    /**
     * player purchases and places a city at a location specified
     * 
     * @post a city is now owned by the player
     * @pre player clicks to build on a location
     * @param vertLoc
     *        the location of the City
     */
    void placeCity(VertexLocation vertLoc);

    /**
     * Changes the Robbers HexLocation
     * 
     * @post player robs player
     * @pre player rolls a 7
     * @param hexLoc
     *        the location of the Robber
     */
    void placeRobber(HexLocation hexLoc);

    /**
     * Robs a player, player receives one resource from the player being robbed
     * 
     * @pre robber is placed
     * @post player has an extra resource
     * @param victim
     *        the victim of the brutal armed robbery
     */
    void robPlayer(RobPlayerInfo victim);

    /*
     * Martitime Trade Controller methods
     */
    /**
     * Completes a maritime trade
     * 
     */
    void makeMaritimeTrade(TradeOffer offer);

    /*
     * Points Controller methods
     */
    /**
     * Updates the players victory points, getting the values from the model
     * 
     * @post players points reflect the values from the model
     * @pre model changed
     */
    void initPointsFromModel();

    /*
     * Roll Dice Controller methods
     */
    /**
     * Calls the roll method on the dice
     * 
     * @return Dice - Returns a dice object containing the values of two dice.
     * @post Value of dice is changed
     */
    Dice rollDice();

    /*
     * Turn tracker controller methods
     */
    /**
     * Ends the players turn
     *
     * @post The turn is ended
     */
    void endTurn();

    /**
     * Initializes the turn tracker using the model
     * 
     * @post the turn is now initialized
     */
    void initTurnFromModel();

}