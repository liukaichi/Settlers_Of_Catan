package client.state;

import client.base.ObserverController;
import client.data.PlayerInfo;
import client.domestic.DomesticTradeController;
import client.domestic.IAcceptTradeOverlay;
import client.domestic.IDomesticTradeOverlay;
import client.facade.ClientFacade;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resources;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 10/29/2015.
 */
public class DomesticTradeState extends GameplayState
{
    private static final Logger LOGGER = Logger.getLogger(DomesticTradeState.class.getName());
    private ClientFacade facade;
    private TradeOffer offer;
    private boolean accepted = false;
    Resources playerHand;
    DomesticTradeController control;
    IDomesticTradeOverlay trade;
    IAcceptTradeOverlay accept;
    Player player;

    public DomesticTradeState(ObserverController controller)
    {
        super(controller);
        if(controller instanceof DomesticTradeController)
        {
            facade = ClientFacade.getInstance();
            control = ((DomesticTradeController) controller);
            trade = control.getTradeOverlay();
            accept = control.getAcceptOverlay();
            player = facade.getPlayer();
            //currentTurnStatus = facade.getModel().getTurnTracker().getStatus();
            initializeTradeView();
            //updateTradeView();
        }
    }

    private void initializeTradeView()
    {
        ArrayList<PlayerInfo> infos = new ArrayList<>();
        infos.addAll(facade.getModel().getGameInfo().getPlayerInfos());
        infos.remove(player.getPlayerInfo());
        trade.setPlayers(infos.toArray(new PlayerInfo[infos.size()]));
        trade.reset();
    }

    private void updateAcceptView()
    {
        if(controller instanceof DomesticTradeController)
        {
            accept.reset();
            offer = facade.getModel().getTradeOffer();
            for(ResourceType type : ResourceType.values())
            {
                int value = offer.getOffer(type);
                if(value > 0)
                    accept.addGetResource(type, Math.abs(value));
                else if(value < 0)
                    accept.addGiveResource(type, value);
            }
            accept.setAcceptEnabled(true);
            accept.setPlayerName(facade.getPlayerByIndex(PlayerIndex.fromInt(offer.getSender())).getName());
        }
    }

    private void updateTradeView()
    {
        if(controller instanceof  DomesticTradeController)
        {
            if (offer == null)
            {
                offer = new TradeOffer(player.getPlayerIndex(), PlayerIndex.NONE, 0, 0, 0, 0, 0);
                playerHand = player.getResources();
            }

            //set resources
            for (ResourceType type : ResourceType.values())
            {
                trade.setResourceAmount(type, String.valueOf(Math.abs(offer.getOffer(type))));
                trade.setResourceAmountChangeEnabled(type, canIncrease(type), canDecrease(type));
            }

            //set buttons
            trade.setTradeEnabled(offer.isSending() && offer.isReceiving() && offer.getReceiver() != -1);
            trade.setStateMessage("trading");
            trade.setCancelEnabled(true);
            trade.setResourceSelectionEnabled(true);
        }
    }

    @Override public void updateView()
    {
        super.updateView();
        if(controller instanceof DomesticTradeController)
        {
            offer = facade.getModel().getTradeOffer();
            DomesticTradeController control = ((DomesticTradeController)controller);
            int currentPlayer = facade.getClientPlayer().getNormalizedPlayerIndex();
            if(offer.getReceiver() == currentPlayer)
            {
                if(!control.getAcceptOverlay().isModalShowing() && !accepted)
                {
                    updateAcceptView();
                    control.getAcceptOverlay().showModal();
                }
                else if(accepted)
                {
                    control.getAcceptOverlay().closeModal();
                }
            }
            else if(offer.getSender() == currentPlayer)
            {
                if(!control.getWaitOverlay().isModalShowing())
                {
                    control.getWaitOverlay().showModal();
                }
                else if(accepted)
                {
                    control.getWaitOverlay().closeModal();
                }
            }
            else
            {
                //do nothing
                /*if(!control.getWaitOverlay().isModalShowing())
                {
                    control.getWaitOverlay().showModal();
                }
                else if(accepted)
                {
                    control.getWaitOverlay().closeModal();
                }*/
            }

        }
    }

    private boolean canIncrease(ResourceType type)
    {
        int playerAmount = playerHand.getAmount(type);
        int offerAmount = offer.getOffer(type);
        switch(offer.getResourceHand(type))
        {
        case send:
            if(playerAmount - offerAmount > 0)
                return true;
            break;
        case receive:
            if(offerAmount > -19)
                return true;
            break;
        }
        return false;
    }

    private boolean canDecrease(ResourceType type)
    {
        int playerAmount = playerHand.getAmount(type);
        int offerAmount = offer.getOffer(type);
        switch(offer.getResourceHand(type))
        {
        case send:
            if(offerAmount > 0)
                return true;
            break;
        case receive:
            if(offerAmount < 0)
                return true;
            break;
        }
        return false;
    }

    private boolean isSending(ResourceType type)
    {
        return offer.getResourceHand(type).equals(TradeOffer.Hand.send);
    }

    private boolean isReceiving(ResourceType type)
    {
        return offer.getResourceHand(type).equals(TradeOffer.Hand.receive);
    }


    @Override
    public void startTrade()
    {
        updateTradeView();
    }

    @Override
    public void cancelTrade()
    {
        controller.setState(new PlayingState(controller));
    }

    @Override
    public void increaseAmount(ResourceType resource)
    {
        switch(offer.getResourceHand(resource))
        {
        case receive:
            offer.subFromOffer(resource, 1);
            break;
        case send:
            offer.addToOffer(resource, 1);
            break;
        }
        updateTradeView();
    }

    @Override
    public void decreaseAmount(ResourceType resource)
    {
        switch(offer.getResourceHand(resource))
        {
        case receive:
            offer.addToOffer(resource, 1);
            break;
        case send:
            offer.subFromOffer(resource, 1);
            break;
        }
        updateTradeView();
    }

    @Override
    public void unsetResource(ResourceType resource)
    {
        offer.setResourceHand(resource, TradeOffer.Hand.none);
        updateTradeView();
    }

    @Override
    public void setResourceToSend(ResourceType resource)
    {
        offer.setResourceHand(resource, TradeOffer.Hand.send);
        updateTradeView();
    }

    @Override
    public void setResourceToReceive(ResourceType resource)
    {
        offer.setResourceHand(resource, TradeOffer.Hand.receive);
        updateTradeView();
    }

    @Override
    public void setPlayerToTradeWith(int playerIndex)
    {
        offer.setReceiver(playerIndex);
        updateTradeView();
    }

    @Override public void sendTradeOffer()
    {
        super.sendTradeOffer();
        trade.closeModal();
        accepted = true;
        facade.sendTradeOffer(PlayerIndex.fromInt(offer.getReceiver()), offer.getOffer(ResourceType.BRICK),
                offer.getOffer(ResourceType.ORE),offer.getOffer(ResourceType.SHEEP),
                offer.getOffer(ResourceType.WHEAT),offer.getOffer(ResourceType.WOOD));
    }

    @Override public void acceptTrade(boolean willAccept)
    {
        super.acceptTrade(willAccept);
        accepted = true;
        facade.acceptTrade(willAccept);
    }


}
