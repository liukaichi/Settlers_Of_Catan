package client.state;

import client.base.ObserverController;
import client.data.PlayerInfo;
import client.domestic.DomesticTradeController;
import client.domestic.IAcceptTradeOverlay;
import client.domestic.IDomesticTradeOverlay;
import client.facade.ClientFacade;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resources;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.util.ArrayList;

/**
 * Representative of the actions the user can take during a domestic trade.
 */
public class DomesticTradeState extends GameplayState
{
    private ClientFacade facade;
    private TradeOffer offer;
    private boolean accepted = false;
    private Resources playerHand;
    private DomesticTradeController control;
    private IDomesticTradeOverlay trade;
    private IAcceptTradeOverlay accept;
    private Player player;

    /**
     * Creates a new Domestic trade state with the given controller.
     *
     * @param controller the controller.
     */
    public DomesticTradeState(ObserverController controller)
    {
        super(controller);
        if (controller instanceof DomesticTradeController)
        {
            facade = ClientFacade.getInstance();
            control = ((DomesticTradeController) controller);
            trade = control.getTradeOverlay();
            accept = control.getAcceptOverlay();
            player = facade.getPlayer();
            initializeTradeView();
        }
    }

    private void initializeTradeView()
    {
        ArrayList<PlayerInfo> infos = new ArrayList<>();
        infos.addAll(facade.getModel().getPlayerInfos());
        infos.remove(player.getPlayerInfo());
        trade.setPlayers(infos.toArray(new PlayerInfo[infos.size()]));
        trade.reset();
    }

    private void updateAcceptView()
    {
        if (controller instanceof DomesticTradeController)
        {
            accept.reset();
            offer = facade.getModel().getTradeOffer();
            Resources giveResources = new Resources();
            for (ResourceType type : ResourceType.values())
            {
                int value = offer.getOffer(type);
                if (value > 0)
                {
                    int getAmount = Math.abs(value);
                    accept.addGetResource(type, getAmount);
                } else if (value < 0)
                {
                    int giveAmount = Math.abs(value);
                    accept.addGiveResource(type, giveAmount);
                    giveResources.setAmount(type, giveAmount);
                }
            }
            Player receiver = facade.getPlayer(PlayerIndex.fromInt(offer.getReceiver()));
            Player sender = facade.getPlayerByIndex(PlayerIndex.fromInt(offer.getSender()));

            boolean canAccept = receiver.hasEnoughResources(giveResources);
            accept.setAcceptEnabled(canAccept);
            accept.setPlayerName(sender.getName());
        }
    }

    private void updateTradeView()
    {
        if (controller instanceof DomesticTradeController)
        {
            if (offer == null)
            {
                offer = new TradeOffer(player.getPlayerIndex(), PlayerIndex.NONE);
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
        if (controller instanceof DomesticTradeController)
        {
            offer = facade.getModel().getTradeOffer();
            DomesticTradeController control = ((DomesticTradeController) controller);
            int currentPlayer = facade.getClientPlayer().getNormalizedPlayerIndex();
            if (offer.getReceiver() == currentPlayer)
            {
                if (!control.getAcceptOverlay().isModalShowing() && !accepted)
                {
                    updateAcceptView();
                    if (accept.isModalShowing())
                        accept.closeModal();
                    accept.showModal();
                } else if (accepted)
                {
                    if (accept.isModalShowing())
                        accept.closeModal();
                    control.getAcceptOverlay().closeModal();
                }
            } else if (offer.getSender() == currentPlayer)
            {
                if (!control.getWaitOverlay().isModalShowing())
                {
                    control.getWaitOverlay().showModal();
                } else if (accepted)
                {
                    control.getWaitOverlay().closeModal();
                }
            }

        }
    }

    private boolean canIncrease(ResourceType type)
    {
        int playerAmount = playerHand.getAmount(type);
        int offerAmount = offer.getOffer(type);
        switch (offer.getResourceHand(type))
        {
        case send:
            if (playerAmount - offerAmount > 0)
                return true;
            break;
        case receive:
            if (offerAmount > -19)
                return true;
            break;
        }
        return false;
    }

    private boolean canDecrease(ResourceType type)
    {
        int offerAmount = offer.getOffer(type);
        switch (offer.getResourceHand(type))
        {
        case send:
            if (offerAmount > 0)
                return true;
            break;
        case receive:
            if (offerAmount < 0)
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

    @Override public void startTrade()
    {
        updateTradeView();
        if (trade.isModalShowing())
            trade.closeModal();
        trade.showModal();
    }

    @Override public void cancelTrade()
    {
        if (trade.isModalShowing())
            trade.closeModal();
        controller.setState(new PlayingState(controller));
    }

    @Override public void increaseAmount(ResourceType resource)
    {
        switch (offer.getResourceHand(resource))
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

    @Override public void decreaseAmount(ResourceType resource)
    {
        switch (offer.getResourceHand(resource))
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

    @Override public void unsetResource(ResourceType resource)
    {
        offer.setResourceHand(resource, TradeOffer.Hand.none);
        updateTradeView();
    }

    @Override public void setResourceToSend(ResourceType resource)
    {
        offer.setResourceHand(resource, TradeOffer.Hand.send);
        updateTradeView();
    }

    @Override public void setResourceToReceive(ResourceType resource)
    {
        offer.setResourceHand(resource, TradeOffer.Hand.receive);
        updateTradeView();
    }

    @Override public void setPlayerToTradeWith(int playerIndex)
    {
        offer.setReceiver(playerIndex);
        updateTradeView();
    }

    @Override public void sendTradeOffer()
    {
        if (trade.isModalShowing())
            trade.closeModal();
        accepted = true;
        facade.sendTradeOffer(offer);
    }

    @Override public void acceptTrade(boolean willAccept)
    {
        accepted = true;
        facade.acceptTrade(willAccept);
        if (accept.isModalShowing())
        {
            accept.closeModal();
        }
    }

}
