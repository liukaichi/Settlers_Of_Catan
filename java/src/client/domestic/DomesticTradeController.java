package client.domestic;

import java.util.Observable;

import client.base.ObserverController;
import client.misc.IWaitView;
import client.state.DomesticTradeState;
import shared.definitions.ResourceType;
import shared.model.ClientModel;

/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends ObserverController implements IDomesticTradeController
{

    private IDomesticTradeOverlay tradeOverlay;
    private IWaitView waitOverlay;
    private IAcceptTradeOverlay acceptOverlay;

    /**
     * DomesticTradeController constructor
     * 
     * @param tradeView
     *        Domestic trade view (i.e., view that contains the "Domestic Trade"
     *        button)
     * @param tradeOverlay
     *        Domestic trade overlay (i.e., view that lets the user propose a
     *        domestic trade)
     * @param waitOverlay
     *        Wait overlay used to notify the user they are waiting for another
     *        player to accept a trade
     * @param acceptOverlay
     *        Accept trade overlay which lets the user accept or reject a
     *        proposed trade
     */
    public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
            IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay)
    {

        super(tradeView);

        setTradeOverlay(tradeOverlay);
        setWaitOverlay(waitOverlay);
        setAcceptOverlay(acceptOverlay);
    }

    public IDomesticTradeView getTradeView()
    {

        return (IDomesticTradeView) super.getView();
    }

    public IDomesticTradeOverlay getTradeOverlay()
    {
        return tradeOverlay;
    }

    public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay)
    {
        this.tradeOverlay = tradeOverlay;
    }

    public IWaitView getWaitOverlay()
    {
        return waitOverlay;
    }

    public void setWaitOverlay(IWaitView waitView)
    {
        this.waitOverlay = waitView;
    }

    public IAcceptTradeOverlay getAcceptOverlay()
    {
        return acceptOverlay;
    }

    public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay)
    {
        this.acceptOverlay = acceptOverlay;
    }

    @Override
    public void startTrade()
    {
        state = new DomesticTradeState(this);
        state.startTrade();
        getTradeOverlay().showModal();
    }

    @Override
    public void decreaseResourceAmount(ResourceType resource)
    {
        state.decreaseAmount(resource);
    }

    @Override
    public void increaseResourceAmount(ResourceType resource)
    {
        state.increaseAmount(resource);
    }

    @Override
    public void sendTradeOffer()
    {
        state.sendTradeOffer();
        getTradeOverlay().closeModal();
        // getWaitOverlay().updateView();
    }

    @Override
    public void setPlayerToTradeWith(int playerIndex)
    {
        state.setPlayerToTradeWith(playerIndex);
    }

    @Override
    public void setResourceToReceive(ResourceType resource)
    {
        state.setResourceToReceive(resource);
    }

    @Override
    public void setResourceToSend(ResourceType resource)
    {
        state.setResourceToSend(resource);
    }

    @Override
    public void unsetResource(ResourceType resource)
    {
        state.unsetResource(resource);
    }

    @Override
    public void cancelTrade()
    {
        state.cancelTrade();
        getTradeOverlay().closeModal();
    }

    @Override
    public void acceptTrade(boolean willAccept)
    {
        state.acceptTrade(willAccept);
        getAcceptOverlay().closeModal();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        state.update(this,(ClientModel)o,arg);
        /*ClientModel model = ((ClientModel)o);
        if(model.hasTradeOffer())
        {
            getAcceptOverlay().showModal();
        }
        else if(getTradeOverlay().isModalShowing())
        {
            getTradeOverlay().closeModal();
        }*/
    }

}
