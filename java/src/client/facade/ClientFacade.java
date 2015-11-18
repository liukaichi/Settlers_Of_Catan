package client.facade;

import client.base.ObserverController;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.poller.Poller;
import client.proxy.IProxy;
import client.proxy.ServerProxy;
import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.*;
import shared.definitions.exceptions.AddAIException;
import shared.definitions.exceptions.GameQueryException;
import shared.definitions.exceptions.InvalidCredentialsException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.Bank;
import shared.model.bank.resource.Resources;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Facade for the Controllers/Views interacting with the Model classes. The
 * only class to directly touch the Model classes will be the Facade. Everything
 * else will call the Facade to do actions. This class contains all available
 * communications with the model.
 */
public class ClientFacade
{
    private final static Logger LOGGER = Logger.getLogger(ClientFacade.class.getName());
    private static ClientFacade _instance = null;
    private ClientModel model;
    private IProxy proxy;
    private PlayerInfo clientPlayer;
    private Poller poller;

    private ClientFacade()
    {
        model = new ClientModel();
        proxy = new ServerProxy();
    }

    /**
     * Singleton Pattern to have a single instance of the Facade, since it
     * contains the models.
     *
     * @return the static instance of the Client Facade.
     */
    public static ClientFacade getInstance()
    {
        if (_instance == null)
        {
            LOGGER.info("Creating new instance of ClientFacade");
            _instance = new ClientFacade();
        }
        return _instance;
    }

    /**
     * Resets the instance of the facade to null. It saves the proxy so that the client still has their cookie set.
     */
    public static void resetInstance()
    {
        IProxy proxy = _instance.getProxy();
        _instance = new ClientFacade();
        _instance.setProxy(proxy);
    }

    public PlayerInfo getClientPlayer()
    {
        return clientPlayer;
    }

    public void setClientPlayer(PlayerInfo clientPlayer)
    {
        this.clientPlayer = clientPlayer;
    }

    /**
     * Used to fill in the clientPlayer's info from the list of PlayerInfos.
     *
     * @param players the info of each of the players. Typically, this is supplied by GameInfo#getPlayerInfos().
     * @pre The clientPlayer has already been initialized with a name and an ID.
     */
    public void buildClientPlayerFromPlayerInfos(List<PlayerInfo> players)
    {
        for (PlayerInfo playerInfo : players)
        {
            if (playerInfo.getId() == clientPlayer.getId())
            {
                clientPlayer = playerInfo;
            }

        }
    }

    /**
     * Gets the player that has the given name.
     *
     * @param name the name to search for.
     * @return the player that matches the name.
     */
    public Player getPlayerByName(String name)
    {
        for (Player player : getPlayers())
        {
            if (player.getName().matches(name))
            {
                return player;
            }
        }
        LOGGER.log(Level.WARNING, "Player's name could not be found in the ClientFacade.");
        return null;
    }

    /**
     * Gets the color of the player with the given name.
     *
     * @param name the name of the player to get the color for.
     * @return the color of the player with the given name.
     */
    public CatanColor getColorByName(String name)
    {
        return getPlayerByName(name).getPlayerColor();
    }

    /**
     * Gets the clientPlayer's Player Object, which holds their bank, as well as other things.
     *
     * @return the Client Player's Player object.
     */
    public Player getPlayer()
    {
        if (clientPlayer != null)
        {
            return getPlayer(clientPlayer.getPlayerIndex());
        }
        return null;
    }

    /*
     * Chat Controller methods
     */

    /**
     * Gets the player with the given index.
     *
     * @param player the player's index.
     * @return the  player with the given index.
     */
    public Player getPlayer(PlayerIndex player)
    {
        if (getPlayers() == null)
        {
            return null;
        }
        return getPlayers().get(player.getIndex());
    }

    /*
     * Dev Card Controller Methods
     */

    /**
     * Sends a message to another player.
     *
     * @param message the message to send.
     */

    public void sendMessage(String message)
    {
        model.updateModel(proxy.sendChat(new SendChatCommand(clientPlayer.getPlayerIndex(), message)));
    }

    /**
     * Determines if the player can buy a dev card.
     *
     * @return whether or not the player can buy a dev card.
     */

    public boolean canBuyDevCard()
    {
        return (getPlayer().canBuyDevCard() && model.canBuyDevCard());
    }

    /**
     * Checks whether the client player can play the given dev card type.
     *
     * @param type the type of Dev card.
     * @return true if the player can play the card type, false otherwise.
     */
    public boolean canPlayDevCard(DevCardType type)
    {
        return getPlayer().canPlayDevCard(type);
    }

    /**
     * Purchases a Development Card. This will take the card from the bank, and
     * adds it to the player' hand.
     */

    public void buyDevCard()
    {
        model.updateModel(proxy.buyDevCard(new BuyDevCardCommand(clientPlayer.getPlayerIndex())));
    }

    /**
     * Plays a Monopoly Card.
     *
     * @param resource the type of resource the player is getting the monopoly on.
     */
    public void playMonopolyCard(ResourceType resource)
    {
        model.updateModel(proxy.monopoly(new MonopolyCommand(clientPlayer.getPlayerIndex(), resource)));
    }

    /**
     * Plays a Year of Plenty Card.
     *
     * @param resource1 The first resource.
     * @param resource2 The second resource.
     */
    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
    {
        model.updateModel(
                proxy.yearOfPlenty(new Year_of_PlentyCommand(clientPlayer.getPlayerIndex(), resource1, resource2)));
    }

    /**
     * Plays a Monument Card.
     */
    public void playMonumentCard()
    {
        model.updateModel(proxy.monument(new MonumentCommand(clientPlayer.getPlayerIndex())));
    }

    /**
     * Plays a Road Building Card.
     *
     * @param edge1 the location of the first road.
     * @param edge2 the location of the second road.
     */
    public void playRoadBuildingCard(EdgeLocation edge1, EdgeLocation edge2)
    {
        model.updateModel(proxy.roadBuilding(new Road_BuildingCommand(clientPlayer.getPlayerIndex(), edge1, edge2)));
    }

    /*
     * Discard Controller methods
     */

    /**
     * Plays a Soldier Card.
     *
     * @param info     the info about the player being robbed.
     * @param location the location that the robber is being placed.
     */
    public void playSoldierCard(RobPlayerInfo info, HexLocation location)
    {
        model.updateModel(
                proxy.soldier(new SoldierCommand(clientPlayer.getPlayerIndex(), info.getPlayerIndex(), location)));
    }

    /**
     * Discards the amount of resources set in the Resources object.
     *
     * @param discardedResources the list of resources to discard.
     * @see Resources
     */
    public void discardResources(Resources discardedResources)
    {
        model.updateModel(
                proxy.discardCards(new DiscardCardsCommand(clientPlayer.getPlayerIndex(), discardedResources)));
    }

    /*
     * Domestic trade controller methods
     */

    /**
     * Discards the amount of resources specified.
     *
     * @param brick the amount of brick
     * @param ore   the amount of ore
     * @param sheep the amount of sheep
     * @param wheat the amount of wheat
     * @param wood  the amount of wood
     */
    public void discardResources(int brick, int ore, int sheep, int wheat, int wood)
    {
        model.updateModel(proxy.discardCards(
                new DiscardCardsCommand(clientPlayer.getPlayerIndex(), brick, ore, sheep, wheat, wood)));
    }

    /**
     * Sends a trade offer to a player.
     *
     * @param receiver the index of the player receiving the offer.
     * @param brick    the amount of brick.
     * @param ore      the amount of ore.
     * @param sheep    the amount of sheep.
     * @param wheat    the amount of wheat.
     * @param wood     the amount of wood.
     */
    public void sendTradeOffer(PlayerIndex receiver, int brick, int ore, int sheep, int wheat, int wood)
    {
        model.updateModel(proxy.offerTrade(
                new OfferTradeCommand(clientPlayer.getPlayerIndex(), receiver, brick, wood, sheep, wheat, ore)));
    }

    /*
     * Join Game Controller methods
     */

    /**
     * Used for accepting/rejecting a trade.
     *
     * @param willAccept Whether or not the player will accept the trade.
     */

    public void acceptTrade(boolean willAccept)
    {
        model.updateModel(proxy.acceptTrade(new AcceptTradeCommand(clientPlayer.getPlayerIndex(), willAccept)));
    }

    /**
     * Gets the list of games available on the server.
     *
     * @return the list of game available on the server.
     */
    public ListGamesResponse listGames()
    {
        return proxy.listGames();
    }

    /**
     * Creates a new game.
     *
     * @param request the information about the game to be created
     */

    public void createNewGame(CreateGameRequest request)
    {
        CreateGameResponse response = proxy.createGame(request);
        model.setGameInfo(response.getGameInfo());
        joinGame(response.getGameID(), CatanColor.RED);

    }

    /*
     * player Waiting Controller methods
     */

    /**
     * Joins an already existent game.
     *
     * @param id    the Id of the game to join.
     * @param color the color for the client player to join with.
     */

    public void joinGame(int id, CatanColor color)
    {
        try
        {
            proxy.joinGame(new JoinGameRequest(id, color));
        } catch (GameQueryException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to Join Game", e);
        }
    }

    /**
     * Gets the state of the game given a version.
     *
     * @param version the version to check for. -1 grabs the game state regardless.
     * @return the representation of the game.
     */
    public ClientModel getGameState(int version)
    {
        ClientModel model = proxy.getGameState(version);
        List<PlayerInfo> players = model.getPlayerInfos();
        buildClientPlayerFromPlayerInfos(players);
        if (version != -1 || players.size() == 4)
        {
            this.model.updateModel(model);
        }

        return model;
    }

    /**
     * Starts the ServerPoller.
     *
     * @see Poller
     */
    public void startPoller()
    {
        poller = new Poller(proxy);
    }

    public boolean pollerStarted()
    {
        return poller != null;
    }

    /**
     * Adds an AI to the game.
     *
     * @param type the type of the AI (LARGEST_ARMY is the only supported value.)
     */

    public void addAI(AIType type)
    {
        try
        {
            proxy.addAI(type);
        } catch (IllegalArgumentException | GameQueryException | AddAIException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * Login Controller methods
     */

    /**
     * Lists the AITypes available to add to the currently joined game.
     *
     * @return the list of AITypes available to add to the currently joined game.
     */
    public List<AIType> listAI()
    {
        ListAIResponse response = proxy.listAI();
        return response.getAITypes();
    }

    /**
     * Signs in the player with the given credentials.
     *
     * @param credentials the player's credentials
     * @throws InvalidCredentialsException if the user doesn't exist in the server.
     */

    public void signInUser(Credentials credentials) throws InvalidCredentialsException
    {
        try
        {
            setClientPlayer(proxy.userLogin(credentials));
        } catch (InvalidCredentialsException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to Login", e);
            throw e;
        }
    }

    /*
     * Map controller methods
     */

    /**
     * Registers the user with the given credentials.
     *
     * @param credentials the credentials of the user registering.
     * @throws InvalidCredentialsException if the registration fails (username already taken).
     */

    public void registerUser(Credentials credentials) throws InvalidCredentialsException
    {
        try
        {
            setClientPlayer(proxy.userRegister(credentials));
        } catch (InvalidCredentialsException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to Login", e);
            throw e;
        }
    }

    /**
     * Checks to see if the player meets the conditions to place a road
     *
     * @param edgeLoc           the location of the Road
     * @param allowDisconnected whether or not the road is being placed in the setup round.
     * @return boolean - true if the player has the required resources and the
     * location is vacant and the player owns a settlement or city at a
     * neighboring vertex location
     * @pre place road is called
     * @post place road continues
     */

    public boolean canPlaceRoad(EdgeLocation edgeLoc, boolean allowDisconnected)
    {
        return model.canPlaceRoad(clientPlayer.getPlayerIndex(), edgeLoc, allowDisconnected);
    }

    /**
     * Determines if the client player can buy a road.
     *
     * @return true if the player can buy a road, false otherwise.
     */
    public boolean canBuyRoad()
    {
        return getPlayer().canBuyRoad();
    }

    /**
     * Checks to see if the player meets the condition to place a settlement
     *
     * @param vertexLocation    the location of the Vertex
     * @param allowDisconnected whether or not the settlement is being placed in the setup round.
     * @return boolean - true if player has the required resources and the
     * location is 2 edges or more from another settlement
     * @pre place settlement is called
     * @post place settlement continues
     */

    public boolean canPlaceSettlement(VertexLocation vertexLocation, boolean allowDisconnected)
    {
        return model.canPlaceSettlement(clientPlayer.getPlayerIndex(), vertexLocation);
    }

    /**
     * Checks to see if the player meets the condition to buy a settlement.
     *
     * @return if the player meets the requirements, false otherwise.
     */
    public boolean canBuySettlement()
    {
        return getPlayer().canBuySettlement();
    }

    /**
     * Checks to see if the player meets the condition to place a city
     *
     * @param location the location of the City
     * @return boolean - true if player has the required resources and the
     * player owns the settlement at that location
     * @pre place city is called
     * @post place city continues
     */

    public boolean canPlaceCity(VertexLocation location)
    {
        return model.canPlaceCity(clientPlayer.getPlayerIndex(), location);
    }

    /**
     * Checks to see if the player meets the condition to buy a city.
     *
     * @return true if the player meets all the conditions to buy a city, false otherwise.
     */
    public boolean canBuyCity()
    {
        return getPlayer().canBuyCity();
    }

    /**
     * Checks to see if player meets the condition to move the robber
     *
     * @param location the location of the hex
     * @return true if a seven has been rolled and the location is viable
     * @pre place robber is called
     * @post place city continues
     */

    public boolean canPlaceRobber(HexLocation location)
    {
        return model.canMoveRobber(clientPlayer.getPlayerIndex(), location);
    }

    /**
     * player purchases and places a road
     *
     * @param location the location of the road
     * @param isFree   whether or not the road is free to place.
     * @pre player clicks on a location to place road
     * @post player met conditions and road is on map
     */

    public void placeRoad(EdgeLocation location, boolean isFree)
    {
        model.updateModel(proxy.buildRoad(new BuildRoadCommand(clientPlayer.getPlayerIndex(), location, isFree)));
    }

    /**
     * player purchases and places a settlement
     *
     * @param location the location of the Settlement
     * @param isFree   whether or not the settlement is free to place.
     * @pre player clicks on a location to place a settlement
     * @post player met conditions and settlement is now on map
     */

    public void placeSettlement(VertexLocation location, boolean isFree)
    {
        model.updateModel(
                proxy.buildSettlement(new BuildSettlementCommand(clientPlayer.getPlayerIndex(), location, isFree)));
    }

    /**
     * player purchases and places a city at a location specified
     *
     * @param location the location of the City
     * @post a city is now owned by the player
     * @pre player clicks to build on a location
     */

    public void placeCity(VertexLocation location)
    {
        model.updateModel(proxy.buildCity(new BuildCityCommand(clientPlayer.getPlayerIndex(), location)));
    }

    /*
     * Martitime Trade Controller methods
     */

    /**
     * Robs a player, player receives one resource from the player being robbed
     *
     * @param victim   the victim of the brutal armed robbery
     * @param location the new location of the robber.
     * @pre robber is placed
     * @post player has an extra resource
     */

    public void robPlayer(RobPlayerInfo victim, HexLocation location)
    {
        model.updateModel(proxy.robPlayer(
                new RobPlayerCommand(clientPlayer.getPlayerIndex(), victim.getPlayerIndex(), location)));
    }


    /*
     * Roll Dice Controller methods
     */

    /**
     * Completes a maritime trade
     *
     * @param ratio          the ratio to trade at.
     * @param inputResource  the resource the client player is trading away.
     * @param outputResource the resourec the client player is receiving.
     * @see TradeRatio
     */

    public void makeMaritimeTrade(TradeRatio ratio, ResourceType inputResource, ResourceType outputResource)
    {
        model.updateModel(proxy.maritimeTrade(
                new MaritimeTradeCommand(clientPlayer.getPlayerIndex(), ratio, inputResource, outputResource)));
    }

    /*
     * Turn tracker controller methods
     */

    /**
     * Calls the roll method on the dice
     *
     * @param diceRollResult the amount rolled.
     * @post Value of dice is changed
     */

    public void rollDice(int diceRollResult)
    {
        model.updateModel(proxy.rollNumber(new RollNumberCommand(clientPlayer.getPlayerIndex(), diceRollResult)));
    }

    /**
     * Ends the players turn
     *
     * @post The turn is ended
     */

    public void endTurn()
    {
        model.updateModel(proxy.finishTurn(new FinishTurnCommand(clientPlayer.getPlayerIndex())));
    }

    public ClientModel getModel()
    {
        return model;
    }

    public void setModel(ClientModel newModel)
    {
        model = newModel;
    }

    /**
     * Sets the host and port of the proxy to send commands to.
     *
     * @param host the host to run the proxy on.
     * @param port the port to run the proxy on.
     */
    public void setProxy(String host, String port)
    {
        proxy = new ServerProxy(host, port);
        /*
        Add this new proxy to the poller if it is running.
         */
        if (poller != null)
            poller.setProxy(proxy);
    }

    /**
     * Adds an observer to the model to receive it's notifications whenever model.updateModel() is called.
     *
     * @param observerController the Observer to add.
     * @see java.util.Observable
     * @see java.util.Observer
     */
    public void addObserver(ObserverController observerController)
    {
        model.addObserver(observerController);
    }

    public List<Player> getPlayers()
    {
        return model.getPlayers();
    }

    /**
     * Gets the points of a player by their index.
     *
     * @param index the index of the player.
     * @return the victory points that the player has.
     */
    public int getPlayerPoints(PlayerIndex index)
    {
        Player player = getPlayer(index);
        if (player != null)
            return player.getBank().getVictoryPoints();
        LOGGER.warning("Couldn't find that player index's points");
        return -1;
    }

    public HexLocation getRobberLocation()
    {
        return getModel().getMap().getRobberLocation();
    }

    /**
     * Gets the rob player info associated with the given hex.
     *
     * @param hexLocation the location of the hex to check.
     * @return a list of RobPlayerInfo associated with the given hex.
     */
    public RobPlayerInfo[] getRobPlayerInfo(HexLocation hexLocation)
    {
        List<RobPlayerInfo> robPlayerInfos = new ArrayList<>();
        PlayerIndex clientIndex = getClientPlayer().getPlayerIndex();
        for (PlayerIndex index : model.getMap().getHexPlayers(hexLocation))
        {
            if (!clientIndex.equals(index))
            {
                robPlayerInfos.add(new RobPlayerInfo(index, getPlayerByIndex(index).getResourceCount()));
            }
        }
        if (robPlayerInfos.size() == 0)
        {
            robPlayerInfos.add(new RobPlayerInfo(PlayerIndex.NONE, 0));
        }

        return robPlayerInfos.toArray(new RobPlayerInfo[robPlayerInfos.size()]);
    }

    /**
     * Gets the Player object based on the given index.
     *
     * @param index the index of the player.
     * @return the Player object based on the given index.
     * @see Player
     */
    public Player getPlayerByIndex(PlayerIndex index)
    {
        return model.getPlayerByIndex(index);
    }

    /**
     * Determines if it is the client player's turn.
     *
     * @return true if it is client player's turn, false otherwise.
     */
    public boolean isMyTurn()
    {
        PlayerIndex currentTurn = model.getTurnTracker().getCurrentTurn();
        PlayerIndex currentPlayerIndex = getClientPlayer().getPlayerIndex();
        return currentTurn.equals(currentPlayerIndex);
    }

    public int getClientPlayerRoadCount()
    {
        return getPlayer().getStructureCount(StructureType.ROAD);
    }

    public int getClientPlayerSettlementCount()
    {
        return getPlayer().getStructureCount(StructureType.SETTLEMENT);
    }

    public Bank getBank()
    {
        return model.getBank();
    }

    /**
     * Updates the client player. This is called periodically to ensure that the clientPlayer is up to date.
     */
    public void updateClientPlayer()
    {
        for (PlayerInfo playerInfo : model.getPlayerInfos())
        {
            if (playerInfo.getId() == clientPlayer.getId())
            {
                setClientPlayer(playerInfo);
                return;
            }
        }
    }

    /**
     * Stops the PollerTask
     */
    public void stopPoller()
    {
        if (poller != null)
        {
            poller.stopPoll();
        }
    }

    public IProxy getProxy()
    {
        return proxy;
    }

    private void setProxy(IProxy proxy)
    {
        this.proxy = proxy;
    }

    /**
     * Sends a trade offer based off of the TradeOffer object, which contains all of the important information.
     *
     * @param offer the trade offer.
     */
    public void sendTradeOffer(TradeOffer offer)
    {
        sendTradeOffer(PlayerIndex.fromInt(offer.getReceiver()), offer.getOffer(ResourceType.BRICK),
                offer.getOffer(ResourceType.ORE), offer.getOffer(ResourceType.SHEEP),
                offer.getOffer(ResourceType.WHEAT), offer.getOffer(ResourceType.WOOD));
    }
}
