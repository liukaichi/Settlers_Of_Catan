package server;

import client.data.PlayerInfo;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.map.Hex;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.util.Set;

/**
 * Representation of the Model that is cached by the server. This has additional "do" methods that the client model
 * does not have.
 */
public class ServerModel extends ClientModel
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

    public ClientModel sendChat(PlayerIndex playerIndex, String content)
    {
        this.getChat().addMessageLine(getPlayerName(playerIndex), content);
        this.setChanged();
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
            Set<PlayerIndex> players = getMap().getHexPlayersWithCity(hex.getLocation());
            getBank().awardPlayers(hex.getResourceType(), StructureType.CITY, players);
            players = getMap().getHexPlayersWithSettlement(hex.getLocation());
            getBank().awardPlayers(hex.getResourceType(), StructureType.SETTLEMENT, players);
        }
        this.setChanged();
        return this;
    }

    public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location)
    {
        return null;
    }

    public ClientModel finishTurn(PlayerIndex playerIndex)
    {
        this.getTurnTracker().finishTurn(playerIndex);
        this.setChanged();
        return this;
    }

    public ClientModel buyDevCard(PlayerIndex playerIndex) throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.buyDevCard();
        this.setChanged();
        return this;
    }

    public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
            throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.playDevCard(DevCardType.YEAR_OF_PLENTY);
        player.getResources().getResource(resource1).addResource(1);
        player.getResources().getResource(resource2).addResource(1);
        this.setChanged();
        return this;
    }

    public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
            throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.playDevCard(DevCardType.ROAD_BUILD);
        getMap().placeRoad(playerIndex, spot1);
        getMap().placeRoad(playerIndex, spot2);
        this.setChanged();

        return this;
    }

    public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
            throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.getBank().addKnights(1);
        player.playDevCard(DevCardType.SOLDIER, playerIndex, victimIndex, location);
        robPlayer(playerIndex, victimIndex, location);
        updateLargestArmy();

        return this;
    }

    public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource) throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.playDevCard(DevCardType.MONOPOLY);
        int total = 0;
        for (Player p : getPlayers())
        {
            total += p.getBank().amountOf(resource);
            p.getResources().setAmount(resource, 0);
        }
        player.getResources().setAmount(resource, total);

        return this;
    }

    public ClientModel monument(PlayerIndex playerIndex) throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.playDevCard(DevCardType.MONUMENT);
        player.getBank().addMonuments(1);

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
        Player player = getPlayers().get(playerIndex.getIndex());
        getMap().placeRoad(playerIndex, location);
        updateLongestRoad();

        if(isFree)
            getMap().placeRoad(playerIndex, location);
        else if(canBuyRoad(getPlayerByIndex(playerIndex)))
            getMap().placeRoad(playerIndex, location);
            player.buyRoad(isFree);
        this.setChanged();
        return this;
    }

    public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation location, boolean isFree)
            throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        if(canPlaceSettlement(playerIndex, location) && canBuySettlement(getPlayerByIndex(playerIndex)))
        {
            getMap().placeSettlement(playerIndex, location);
            player.buySettlement(isFree);
        }
        else
        {
            throw new CatanException("can't build settlement");
        }
        this.setChanged();
        return this;
    }

    public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation location) throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        if(canPlaceCity(playerIndex, location) && canBuyCity(getPlayerByIndex(playerIndex)))
        {
            getMap().placeCity(playerIndex, location);
            player.buyCity();
        }
        else
        {
            throw new CatanException("can't build city");
        }
        this.setChanged();
        return this;
    }

    public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver) throws CatanException
    {

        Player sender = getPlayers().get(playerIndex.getIndex());
        if (sender.hasEnoughResources(offer.getOffer()))
        {
            this.setTradeOffer(offer);
        } else
        {
            throw new CatanException("Sender does not have enough resources to trade this.");
        }
        return this;
    }

    public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept) throws CatanException
    {
        if (!willAccept)
        {
            getLog().addMessageLine(getPlayerName(playerIndex),
                    getPlayerName(playerIndex) + " did not accept the trade.");
            this.removeTradeOffer();
        } else
        {
            TradeOffer offer = getTradeOffer();
            Player receiver = getPlayer(playerIndex);
            Player sender = getPlayer(PlayerIndex.fromInt(offer.getSender()));
            receiver.acceptOffer(offer);
            sender.sendOffer(offer);

            getLog().addMessageLine(getPlayerName(playerIndex), getPlayerName(playerIndex) + " accepted the trade.");
        }
        return this;
    }

    public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource) throws CatanException
    {
        Player player = getPlayer(playerIndex);
        player.maritimeTrade(ratio, inputResource, outputResource);
        getBank().maritimeTrade(ratio, inputResource, outputResource);
        return this;
    }

    public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        Player player = getPlayer(playerIndex);
        player.discardCards(discardedCards);
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
     * Updates the largest army counter A player has the largest army if he or
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

}
