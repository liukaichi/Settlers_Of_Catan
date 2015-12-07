package server;

import client.data.GameInfo;
import client.data.PlayerInfo;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.TurnTracker;
import shared.model.bank.Bank;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resources;
import shared.model.map.CatanMap;
import shared.model.map.Hex;
import shared.model.message.Chat;
import shared.model.message.Log;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of the Model that is cached by the server. This has additional "do" methods that the client model
 * does not have.
 */
public class ServerModel extends ClientModel implements Serializable
{
    /**
     * Initializes a default copy of the Server's model.
     */
    public ServerModel()
    {
        super();
    }

    /**
     * Initializes a Model from Json. Typically for testing.
     *
     * @param json the Json to initialize from.
     */
    public ServerModel(String json)
    {
        super(json);
    }

    public ServerModel(GameInfo gameInfo, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
    {
        this();
        this.setGameInfo(gameInfo);
        populatePlayers(gameInfo.getPlayers());
        this.bank = new Bank(true, players);
        this.chat = new Chat();
        this.log = new Log();
        this.map = new CatanMap(randomTiles, randomNumbers, randomPorts);
        this.tradeOffer = null;
        this.turnTracker = new TurnTracker();
        this.version = 0;
        this.winner = PlayerIndex.NONE;
    }

    private void populatePlayers(List<PlayerInfo> players)
    {
        this.players = new ArrayList<>();
        for (PlayerInfo pInfo : players)
        {
            this.players.add(new Player(pInfo));
        }

    }

    public ClientModel sendChat(PlayerIndex playerIndex, String content)
    {
        this.getChat().addMessageLine(getPlayerName(playerIndex), content);
        increaseVersionNumber();
        return this;
    }

    private String getPlayerName(PlayerIndex index)
    {
        PlayerInfo player = getPlayerInfos().get(index.getIndex());
        return player.getName();
    }

    //TODO refactor to reduce dependency
    public ClientModel rollNumber(PlayerIndex playerIndex, int number)
    {
        for (Hex hex : getMap().getHexesByNumber(number))
        {
            List<PlayerIndex> players = getMap().getHexPlayersWithCity(hex.getLocation());
            getBank().awardPlayers(hex.getResourceType(), StructureType.CITY, players);
            players = getMap().getHexPlayersWithSettlement(hex.getLocation());
            getBank().awardPlayers(hex.getResourceType(), StructureType.SETTLEMENT, players);
        }
        String playerName = getPlayerName(playerIndex);
        getLog().addMessageLine(playerName, playerName + " just rolled a " + number);

        //set the turn according to the roll number
        if (number == 7)
        {
            boolean shouldDiscard = false;
            for (Player player : players)
            {
                if (player.getResourcesCount() > 7)
                {
                    shouldDiscard = true;
                }
            }
            if (shouldDiscard)
            {
                turnTracker.updateStatus(TurnStatus.Discarding);
            }
            else
            {
                turnTracker.updateStatus(TurnStatus.Robbing);
            }
        } else
        {
            turnTracker.updateStatus(TurnStatus.Playing);
        }
        increaseVersionNumber();
        return this;
    }

    public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location) throws CatanException
    {
        getMap().setRobberLocation(location);
        Player robberPlayer = getPlayer(playerIndex);

        String robberName = robberPlayer.getName();

        if (victim == PlayerIndex.NONE)
        {
            getLog().addMessageLine(robberPlayer.getName(), robberPlayer.getName() + " don't got no one to rob!");
        } else
        {
            Player victimPlayer = getPlayer(victim);
            ResourceType robbedType = victimPlayer.robPlayer();
            robberPlayer.increaseResource(robbedType, 1);
            String victimName = victimPlayer.getName();
            getLog().addMessageLine(robberName, robberName + " moved the robber and robbed " + victimName + ".");

        }
        turnTracker.updateStatus(TurnStatus.Playing);
        increaseVersionNumber();
        return this;
    }

    public ClientModel finishTurn(PlayerIndex playerIndex)
    {
        turnTracker.finishTurn(playerIndex);
        Player player = getPlayer(playerIndex);
        String playerName = player.getName();
        DevCards devCards = getPlayer(playerIndex).getBank().getDevCards();

        for(DevCard card : devCards.getDevCardList()){
            int unplayable = card.getAmount(DevCard.AmountType.UNPLAYABLE);
            int playable = card.getAmount(DevCard.AmountType.PLAYABLE);
            card.setAmountPlayable(playable + unplayable);
            card.setAmountUnplayable(0);
        }
        getLog().addMessageLine(playerName, playerName + "'s turn just ended.");
        increaseVersionNumber();
        if (player.getVictoryPoints() == 10)
        {
            setWinner(playerIndex);
        }
        return this;
    }

    public ClientModel buyDevCard(PlayerIndex playerIndex) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.buyDevCard();
        String playerName = player.getName();
        getLog().addMessageLine(playerName, playerName + " bought a Development Card.");
        increaseVersionNumber();
        return this;
    }

    public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.playDevCard(DevCardType.YEAR_OF_PLENTY);
        player.getResources().getResource(resource1).addResource(1);
        player.getResources().getResource(resource2).addResource(1);
        String playerName = player.getName();
        getLog().addMessageLine(playerName, playerName + " used a Year of Plenty and got a " + resource1 + "and a " + resource2 + ".");
        increaseVersionNumber();
        return this;
    }

    public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.playDevCard(DevCardType.ROAD_BUILD);
        getMap().placeRoad(playerIndex, spot1);
        getMap().placeRoad(playerIndex, spot2);
        getLog().addMessageLine(player.getName(), player.getName() + " built 2 roads.");
        increaseVersionNumber();
        return this;
    }

    public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.getBank().addKnights(1);
        player.playDevCard(DevCardType.SOLDIER, playerIndex, victimIndex, location);
        String playerName = player.getName();
        getLog().addMessageLine(playerName, playerName + "used a soldier.");
        robPlayer(playerIndex, victimIndex, location);
        updateLargestArmy();
        turnTracker.updateStatus(TurnStatus.Playing);
        increaseVersionNumber();
        return this;
    }

    public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.playDevCard(DevCardType.MONOPOLY);
        int total = 0;
        for (Player p : getPlayers())
        {
            total += p.getBank().amountOf(resource);
            p.getResources().setAmount(resource, 0);
        }
        player.getResources().setAmount(resource, total);
        String playerName = player.getName();
        getLog().addMessageLine(playerName, playerName + " stole everyone's " + resource);
        increaseVersionNumber();
        return this;
    }

    public ClientModel monument(PlayerIndex playerIndex) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.playDevCard(DevCardType.MONUMENT);
        player.getBank().addMonuments(1);

        getLog().addMessageLine(player.getName(), player.getName() + " built a monument and gained a victory point");
        player.incrementVictoryPoints();
        increaseVersionNumber();
        return this;
    }

    /**
     * Builds a city for the given player at the given location.
     *
     * @param playerIndex the player who is building the road.
     * @param location    the location where the road is being built.
     * @param isFree      whether or not the player should be charged.
     */
    public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation location, boolean isFree) throws CatanException
    {

        Player player = getPlayer(playerIndex);

        if (isFree)
        {
            getMap().placeRoad(playerIndex, location);
        } else if (canBuyRoad(getPlayerByIndex(playerIndex)))
        {
            getMap().placeRoad(playerIndex, location);
        }

        player.buyRoad(isFree);
        String playerName = player.getName();
        getLog().addMessageLine(playerName, playerName + " built a road.");

        updateLongestRoad();
        increaseVersionNumber();
        return this;
    }

    public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation location, boolean isFree) throws CatanException
    {
        location = location.getNormalizedLocation();
        Player player = getPlayer(playerIndex);
        if (canPlaceSettlement(playerIndex, location))
        {
            player.buySettlement(isFree);
            getMap().placeSettlement(playerIndex, location);
            if (turnTracker.getStatus() == TurnStatus.SecondRound)
            {
                for (Hex hex : getMap().getHexes().values())
                {
                    for (VertexLocation vertex : hex.getVertices())
                    {
                        if (vertex.getNormalizedLocation().equals(location))
                        {
                            player.getBank().earnResource(ResourceType.toResourceType(hex.getHexType()), 1);
                        }
                    }
                }
            }
        } else
        {
            throw new CatanException("can't build settlement");
        }
        String playerName = player.getName();
        getLog().addMessageLine(playerName, playerName + " built a settlement.");
        player.incrementVictoryPoints();
        increaseVersionNumber();
        return this;
    }

    public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation location) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        if (canPlaceCity(playerIndex, location) && canBuyCity(getPlayerByIndex(playerIndex)))
        {
            getMap().placeCity(playerIndex, location);
            player.buyCity();
        } else
        {
            throw new CatanException("can't build city");
        }
        String playerName = player.getName();
        getLog().addMessageLine(playerName, playerName + " built a city.");
        player.incrementVictoryPoints();
        increaseVersionNumber();
        return this;
    }

    public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver) throws CatanException
    {

        Player sender = getPlayer(playerIndex);
        if (sender.hasEnoughResources(offer.getOffer()))
        {
            this.setTradeOffer(offer);
        } else
        {
            throw new CatanException("Sender does not have enough resources to trade this.");
        }
        increaseVersionNumber();
        return this;
    }

    public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept) throws CatanException
    {

        if (!willAccept)
        {
            getLog().addMessageLine(getPlayerName(playerIndex), getPlayerName(playerIndex) + " did not accept the trade.");
        } else
        {
            TradeOffer offer = getTradeOffer();
            Player receiver = getPlayer(playerIndex);
            Player sender = getPlayer(offer.getSenderIndex());
            receiver.acceptOffer(offer);
            sender.sendOffer(offer);

            getLog().addMessageLine(getPlayerName(playerIndex), getPlayerName(playerIndex) + " accepted the trade.");
        }
        increaseVersionNumber();
        this.removeTradeOffer();
        return this;
    }

    public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource, ResourceType outputResource)
            throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.maritimeTrade(ratio, inputResource, outputResource);
        getBank().maritimeTrade(ratio, inputResource, outputResource);
        increaseVersionNumber();
        return this;
    }

    public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        Player playerToDiscard = getPlayer(playerIndex);
        playerToDiscard.discardCards(discardedCards);
        boolean shouldDiscard = false;
        for (Player player : players)
        {
            if (player.getResourcesCount() > 7)
            {
                shouldDiscard = true;
            }
        }
        if (shouldDiscard)
        {
            turnTracker.updateStatus(TurnStatus.Discarding);
        }
        else
        {
            turnTracker.updateStatus(TurnStatus.Robbing);
        }
        increaseVersionNumber();
        return this;
    }

    public Player getPlayer(PlayerIndex playerIndex)
    {
        return getPlayers().get(playerIndex.getIndex());
    }

    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    /**
     * Updates the currentTurn counter
     *
     * @param playerCurrentTurn the info of the player to update the current turn for.
     */
    public void updateCurrentTurn(PlayerInfo playerCurrentTurn)
    {
        getTurnTracker().updateCurrentTurn(playerCurrentTurn);
    }

    /**
     * Updates the longestRoad counter. A player has the longest road if he or
     * she has at least 5 roads
     */
    private void updateLongestRoad()
    {
        getTurnTracker().updateLongestRoad(getPlayers());
    }

    /**
     * Updates the largest army counter. A player has the largest army if he or
     * she has at least 3 knights
     */
    public void updateLargestArmy()
    {
        getTurnTracker().updateLargestArmy(getPlayers());
    }

    /**
     * Updates the status string based on the current phase of the player's turn
     *
     * @param playerTurnStatus the info of the player to update the status for.
     */
    public void updateStatus(TurnStatus playerTurnStatus)
    {
        getTurnTracker().updateStatus(playerTurnStatus);
    }

    public void setPlayerColor(CatanColor color, int playerID)
    {
        for (Player player : players)
        {
            if (player.getID() == playerID)
            {
                player.setColor(color);
            }
        }
    }

    @Override public String toString()
    {
        return super.toString();
    }

    public void addPlayer(PlayerInfo playerInfo)
    {
        this.players.add(new Player(playerInfo));
    }
}
