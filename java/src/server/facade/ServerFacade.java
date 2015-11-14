package server.facade;

import server.ServerModel;
import server.manager.UserManager;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

/**
 * The actual Facade that interacts for real.
 */
public class ServerFacade extends AbstractServerFacade
{
    private ServerModel model;
    private UserManager userMan;
    public ServerFacade(){
        userMan = UserManager.getInstance();
    }

    @Override public ClientModel getGameState(int version)
    {
        if(model.getVersion() != version){
            return model;
        }
        return null;
    }

    @Override
    public ClientModel getGameState() {
        return model;
    }

    @Override public ClientModel sendChat(PlayerIndex sender, String content)
    {
        return null;
    }

    @Override public ClientModel rollNumber(PlayerIndex playerIndex, int number)
    {
        return null;
    }

    @Override public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location)
    {
        return null;
    }

    @Override public ClientModel finishTurn(PlayerIndex playerIndex)
    {
    	model.finishTurn(); 
        return null;
    }

    @Override public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        //model.buyDevCard();
        return null;
    }

    @Override public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        return null;
    }

    @Override public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        return null;
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

    @Override public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        model.buildRoad(null, null);
        return null;
    }

    @Override public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free)
    {
        //model.buildSettlement(null, null);
        return null;
    }

    @Override public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation vertexLocation)
    {
        //model.buildCity(null, null);
        return null;
    }

    @Override public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return null;
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
