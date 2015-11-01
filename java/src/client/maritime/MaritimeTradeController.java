package client.maritime;

import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.model.ClientModel;

import java.util.Observable;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends ObserverController implements IMaritimeTradeController
{
    ClientFacade facade = ClientFacade.getInstance();
    private IMaritimeTradeOverlay tradeOverlay;
    private static final Logger LOGGER = Logger.getLogger(MaritimeTradeController.class.getName());

    ResourceType resourceToGive = null;
    ResourceType resourceToReceive = null;
    int tradingRatio = -1;

    public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay)
    {

        super(tradeView);

        setTradeOverlay(tradeOverlay);
    }

    public IMaritimeTradeView getTradeView()
    {

        return (IMaritimeTradeView) super.getView();
    }

    public IMaritimeTradeOverlay getTradeOverlay()
    {
        return tradeOverlay;
    }

    public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay)
    {
        this.tradeOverlay = tradeOverlay;
    }

    /**
     * Gets called by the view when the roll dice button is pressed.<br>
     * To Do (not necessarily in this class):
     * <ul>
     * <li>Create and array of possible resource trade options (Resource types of a sufficent amount to trade)
     * <li>and pass them into the overlay in .showGiveOptions(resurce[]).
     * <li>disable trade button in view.
     * <li>updateView on tradeOverlay.
     * </ul>
     */
    @Override public void startTrade()
    {
        //TODO do the things above.

        getTradeOverlay().reset();
        TreeSet<ResourceType> tradeableResources = new TreeSet<>();
        for (ResourceType type : ResourceType.values())
        {
            if (facade.getPlayer().canTradeResource(type)){
                tradeableResources.add(type);
            }
        }

        tradeOverlay.showGiveOptions(tradeableResources.toArray(new ResourceType[tradeableResources.size()]));

        getTradeOverlay().setTradeEnabled(false);
        getTradeOverlay().showModal();

    }

    /**
     * <ul>
     * <li>Verify there is a resource to give and recieve
     * <li>Send request to server
     * <li>closeModal
     * </ul>
     */
    @Override public void makeTrade()
    {
        if (tradingRatio == -1 || resourceToGive == null || resourceToReceive == null)
        {
            LOGGER.warning("Something went wrong with Maritime trade. Shouldn't have gotten here");
        }
        facade.makeMaritimeTrade(TradeRatio.fromInt(tradingRatio), resourceToGive, resourceToReceive);
        getTradeOverlay().hideGiveOptions();
        getTradeOverlay().closeModal();
        tradingRatio = -1;
        resourceToGive = null;
        resourceToReceive = null;
    }

    /**
     * closeModal()
     */
    @Override public void cancelTrade()
    {
        getTradeOverlay().closeModal();
        tradingRatio = -1;
        resourceToGive = null;
        resourceToReceive = null;
    }

    /**
     * <ul>
     * <li>check to make sure bank has the resource
     * <li>call selectGetOption() in tradeOverlay
     * <li>enable/disable trade button based on validity
     * </ul>
     */
    @Override public void setGetResource(ResourceType resource)
    {
        if (facade.getModel().getBank().getResources().getAmount(resource) > 0)
        {
            resourceToReceive = resource;
            tradeOverlay.selectGetOption(resource,1);
            tradeOverlay.setTradeEnabled(true);
        } else
        {
            tradeOverlay.setTradeEnabled(false);
        }

    }

    /**
     * <ul>
     * <li>find out trade ratio for resource to give
     * <li>call selectGiveOption() in tradeOverlay
     * </ul>
     */
    @Override public void setGiveResource(ResourceType resource)
    {
        int tradeRatio = facade.getPlayer().getTradeRatio(resource);
        tradeOverlay.selectGiveOption(resource, tradeRatio);
        resourceToGive = resource;
        tradingRatio= tradeRatio;

        TreeSet<ResourceType> tradeableResources = new TreeSet<>();
        for (ResourceType type : ResourceType.values())
        {
            if (facade.getBank().getResources().getAmount(type) > 0 && type != resource){
                tradeableResources.add(type);
            }
        }
        tradeOverlay.showGetOptions(tradeableResources.toArray(new ResourceType[tradeableResources.size()]));

    }

    /**
     * <ul>
     * <li>disable trade button
     * <li>redisplay the trade Options for getting resource
     * </ul>
     */
    @Override public void unsetGetValue()
    {
        getTradeOverlay().setTradeEnabled(false);
        resourceToReceive = null;
        TreeSet<ResourceType> tradeableResources = new TreeSet<>();
        for (ResourceType type : ResourceType.values())
        {
            if (facade.getBank().getResources().getAmount(type) > 0){
                tradeableResources.add(type);
            }
        }
        tradeOverlay.showGetOptions(tradeableResources.toArray(new ResourceType[tradeableResources.size()]));

    }

    /**
     * <ul>
     * <li>disable trade button
     * <li>redisplay the trade Options for getting resource
     * </ul>
     */
    @Override public void unsetGiveValue()
    {

        tradeOverlay.setTradeEnabled(false);
        resourceToGive = null;
        resourceToReceive = null;

        TreeSet<ResourceType> tradeableResources = new TreeSet<>();
        for (ResourceType type : ResourceType.values())
        {
            if (facade.getPlayer().canTradeResource(type)){
                tradeableResources.add(type);
            }
        }

        tradeOverlay.showGiveOptions(tradeableResources.toArray(new ResourceType[tradeableResources.size()]));
    }



    @Override public void update(Observable o, Object arg)
    {
        state.update(this, (ClientModel) o, arg);
    }
}
