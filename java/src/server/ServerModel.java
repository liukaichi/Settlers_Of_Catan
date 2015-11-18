package server;

import client.data.PlayerInfo;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.definitions.exceptions.PlacementException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.PlayerBank;
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
        this.getChat().addMessageLine(getPlayerName(playerIndex.getIndex()), content);
        this.setChanged();
        return this;
    }

    private String getPlayerName(int index)
    {
        PlayerInfo player = getPlayerInfos().get(index);
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

    public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim,
            HexLocation location)
    {
        return null;
    }

    public ClientModel finishTurn(PlayerIndex playerIndex)
    {
        this.getTurnTracker().finishTurn(playerIndex);
        this.setChanged();
        return this;
    }

    public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        try {
            Player player = getPlayers().get(playerIndex.getIndex());
            player.buyDevCard();
            this.setChanged();
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1,
            ResourceType resource2)
    {
        try {
            Player player = getPlayers().get(playerIndex.getIndex());
            player.playDevCard(DevCardType.YEAR_OF_PLENTY);
            player.getResources().getResource(resource1).addResource(1);
            player.getResources().getResource(resource2).addResource(1);
            this.setChanged();
        } catch (CatanException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1,
            EdgeLocation spot2)
    {
        try
        {
            Player player = getPlayers().get(playerIndex.getIndex());
            player.playDevCard(DevCardType.ROAD_BUILD);
            getMap().placeRoad(playerIndex, spot1);
            getMap().placeRoad(playerIndex, spot2);
            this.setChanged();
        } catch (PlacementException e)
        {
            e.printStackTrace();
        } catch (CatanException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex,
            HexLocation location)
    {

        try {
            Player player = getPlayers().get(playerIndex.getIndex());
            player.getBank().addKnights(1);
            player.playDevCard(DevCardType.SOLDIER, playerIndex, victimIndex, location);
            robPlayer(playerIndex, victimIndex, location);
            this.setChanged();
        } catch (CatanException e) {
            e.printStackTrace();
        }

        return this;
    }

    public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource)
    {
        try {
            Player player = getPlayers().get(playerIndex.getIndex());
            player.playDevCard(DevCardType.MONOPOLY);
            int total = 0;
            for(Player p : getPlayers()){
                total += p.getBank().amountOf(resource);
                p.getResources().setAmount(resource, 0);
            }
            player.getResources().setAmount(resource, total);
            this.setChanged();
        } catch (CatanException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ClientModel monument(PlayerIndex playerIndex)
    {
        try {
            Player player = getPlayers().get(playerIndex.getIndex());
            player.playDevCard(DevCardType.MONUMENT);
            player.getBank().addMonuments(1);
            this.setChanged();
        } catch (CatanException e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * Builds a city for the given player at the given location.
     *
     * @param playerIndex   the player who is building the road.
     * @param location the location where the road is being built.
     * @param isFree whether or not the player should be charged.
     */
    public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation location, boolean isFree) throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.buyRoad(isFree);
        getMap().placeRoad(playerIndex, location);
        this.setChanged();
        return this;
    }

    public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation location, boolean isFree) throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.buySettlement(isFree);
        getMap().placeSettlement(playerIndex, location);
        this.setChanged();
        return this;
    }

    public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation location) throws CatanException
    {
        Player player = getPlayers().get(playerIndex.getIndex());
        player.buyCity();
        getMap().placeCity(playerIndex, location);
        this.setChanged();
        return this;
    }

    public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        tradeOffer = offer;
        this.setChanged();
        return this;
    }

    public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }

    public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio,
            ResourceType inputResource, ResourceType outputResource)
    {
        return null;
    }

    public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        return null;
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
     *
     * @param playerLongestRoad the info of the player to update the longest road for.
     */
    public void updateLongestRoad(PlayerBank playerLongestRoad)
    {
        getTurnTracker().updateLongestRoad(playerLongestRoad);
    }

    /**
     * Updates the largest army counter A player has the largest army if he or
     * she has at least 3 knights
     *
     * @param playerLargestArmy the info of the player to update the largest army for.
     */
    public void updateLargestArmy(PlayerBank playerLargestArmy)
    {
        getTurnTracker().updateLargestArmy(playerLargestArmy);
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
