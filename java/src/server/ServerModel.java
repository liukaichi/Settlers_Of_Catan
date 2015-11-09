package server;

import client.data.PlayerInfo;
import server.facade.IMovesFacade;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.StructureType;
import shared.definitions.TradeRatio;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.PlacementException;
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
 * Created by cstaheli on 11/5/2015.
 */
public class ServerModel extends ClientModel implements IMovesFacade
{
    @Override public ClientModel sendChat(PlayerIndex playerIndex, String content)
    {
        this.getChat().addMessageLine(getPlayerName(playerIndex.getIndex()), content);
        this.setChanged();
        return this;
    }
    private String getPlayerName(int index)
    {
        PlayerInfo player = getGameInfo().getPlayerInfos().get(index);
        return player.getName();
    }
    @Override public ClientModel rollNumber(PlayerIndex playerIndex, int number) //TODO refactor to reduce dependency
    {
        for(Hex hex : getMap().getHexesByNumber(number))
        {
            Set<PlayerIndex> players = getMap().getHexPlayersWithCity(hex.getLocation());
            getBank().awardPlayers(hex.getResourceType(), StructureType.CITY, players);
            players = getMap().getHexPlayersWithSettlement(hex.getLocation());
            getBank().awardPlayers(hex.getResourceType(), StructureType.SETTLEMENT, players);
        }
        this.setChanged();
        return this;
    }
    @Override public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location)
    {
        return null;
    }
    @Override public ClientModel finishTurn(PlayerIndex playerIndex)
    {
        this.getTurnTracker().finishTurn(playerIndex);
        this.setChanged();
        return this;
    }
    @Override public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        return null;
    }
    @Override public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        return null;
    }
    @Override public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        try
        {
            getMap().placeRoad(playerIndex, spot1);
            getMap().placeRoad(playerIndex, spot2);
            this.setChanged();
        } catch (PlacementException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    @Override public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        return null;
    }
    @Override public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource)
    {
        return null;
    }
    @Override public ClientModel monument(PlayerIndex playerIndex)
    {
        return null;
    }
    @Override public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation location, boolean isFree)
    {
        try
        {
            Player player = getGameInfo().getPlayers().get(playerIndex.getIndex());
            player.buyRoad(isFree);
            getMap().placeRoad(playerIndex, location);
            this.setChanged();
        }
        catch(CatanException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    /**
     * Builds a settlement for the given player at the given location.
     * @param playerIndex the player who is building the settlement.
     * @param location the location where the settlement is being built.
     * @param isFree whether or not to charge the player.
     */
    @Override public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation location, boolean isFree)
    {
        try
        {
            Player player = getGameInfo().getPlayers().get(playerIndex.getIndex());
            player.buySettlement(isFree);
            getMap().placeSettlement(playerIndex, location);
            this.setChanged();
        }
        catch(CatanException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    /**
     * Builds a city for the given player at the given location.
     * @param playerIndex the player who is building the city.
     * @param location the location where the city is being built.
     * @param isFree whether or not to charge the player.
     */
    @Override public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation location, boolean isFree)
    {
        try
        {
            Player player = getGameInfo().getPlayers().get(playerIndex.getIndex());
            if(isFree)
                player.buyCity();
            getMap().placeCity(playerIndex, location);
            this.setChanged();
        }
        catch(CatanException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    @Override public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        tradeOffer = offer;
        this.setChanged();
        return this;
    }
    @Override public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }
    @Override public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource)
    {
        return null;
    }

    @Override public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        return null;
    }
}
