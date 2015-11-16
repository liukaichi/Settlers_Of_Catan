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
 * Representation of the Model that is cached by the server. This has additional "do" methods that the client model
 * does not have.
 */
public class ServerModel extends ClientModel implements IMovesFacade
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

    @Override public ClientModel sendChat(int gameID, PlayerIndex playerIndex, String content)
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

    @Override public ClientModel rollNumber(int gameID, PlayerIndex playerIndex,
            int number) //TODO refactor to reduce dependency
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

    @Override public ClientModel robPlayer(int gameID, PlayerIndex playerIndex, PlayerIndex victim,
            HexLocation location)
    {
        return null;
    }

    @Override public ClientModel finishTurn(int gameID, PlayerIndex playerIndex)
    {
        this.getTurnTracker().finishTurn(playerIndex);
        this.setChanged();
        return this;
    }

    @Override public ClientModel buyDevCard(int gameID, PlayerIndex playerIndex)
    {
        return null;
    }

    @Override public ClientModel yearOfPlenty(int gameID, PlayerIndex playerIndex, ResourceType resource1,
            ResourceType resource2)
    {
        return null;
    }

    @Override public ClientModel roadBuilding(int gameID, PlayerIndex playerIndex, EdgeLocation spot1,
            EdgeLocation spot2)
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

    @Override public ClientModel soldier(int gameID, PlayerIndex playerIndex, PlayerIndex victimIndex,
            HexLocation location)
    {
        return null;
    }

    @Override public ClientModel monopoly(int gameID, PlayerIndex playerIndex, ResourceType resource)
    {
        return null;
    }

    @Override public ClientModel monument(int gameID, PlayerIndex playerIndex)
    {
        return null;
    }

    @Override public ClientModel buildRoad(int gameID, PlayerIndex playerIndex, EdgeLocation location, boolean isFree)
    {
        try
        {
            Player player = getGameInfo().getPlayers().get(playerIndex.getIndex());
            player.buyRoad(isFree);
            getMap().placeRoad(playerIndex, location);
            this.setChanged();
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    @Override public ClientModel buildSettlement(int gameID, PlayerIndex playerIndex, VertexLocation location,
            boolean isFree)
    {
        try
        {
            Player player = getGameInfo().getPlayers().get(playerIndex.getIndex());
            player.buySettlement(isFree);
            getMap().placeSettlement(playerIndex, location);
            this.setChanged();
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    @Override public ClientModel buildCity(int gameID, PlayerIndex playerIndex, VertexLocation location)
    {
        try
        {
            Player player = getGameInfo().getPlayers().get(playerIndex.getIndex());
            player.buyCity();
            getMap().placeCity(playerIndex, location);
            this.setChanged();
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    @Override public ClientModel offerTrade(int gameID, PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        tradeOffer = offer;
        this.setChanged();
        return this;
    }

    @Override public ClientModel acceptTrade(int gameID, PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }

    @Override public ClientModel maritimeTrade(int gameID, PlayerIndex playerIndex, TradeRatio ratio,
            ResourceType inputResource, ResourceType outputResource)
    {
        return null;
    }

    @Override public ClientModel discardCards(int gameID, PlayerIndex playerIndex, Resources discardedCards)
    {
        return null;
    }

    @Override public boolean equals(Object o)
    {
        return super.equals(o);
    }

}
