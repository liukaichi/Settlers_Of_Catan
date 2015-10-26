package client.maritime;

import java.util.Observable;

import client.base.ObserverController;
import shared.definitions.ResourceType;

/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends ObserverController implements IMaritimeTradeController
{

    private IMaritimeTradeOverlay tradeOverlay;

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
     *Gets called by the view when the roll dice button is pressed.<br>
     *To Do (not necessarily in this class):
     * <ul>
     * <li>Create and array of possible resource trade options (Resource types of a sufficent amount to trade)
     * <li>and pass them into the overlay in .showGiveOptions(resurce[]).
     * <li>disable trade button in view.
     * <li>showModal on tradeOverlay.
     * </ul>
     */
    @Override
    public void startTrade()
    {
        //TODO do the things above.
        getTradeOverlay().showModal();
    }

    /**
     * <ul>
     * <li>Verify there is a resource to give and recieve
     * <li>Send request to server
     * <li>closeModal
     * </ul>
     * */
    @Override
    public void makeTrade()
    {
        state.sendTradeOffer(); //TODO pass in parameters
        getTradeOverlay().closeModal();
    }

    /**
     * closeModal()
     */
    @Override
    public void cancelTrade()
    {

        getTradeOverlay().closeModal();
    }

    /**
     * <ul>
     * <li>check to make sure bank has the resource
     * <li>call selectGetOption() in tradeOverlay
     * <li>enable/disable trade button based on validity
     * </ul>
     */
    @Override
    public void setGetResource(ResourceType resource)
    {

    }

    /**
     * <ul>
     * <li>find out trade ratio for resource to give
     * <li>call selectGiveOption() in tradeOverlay
     * </ul>
     */
    @Override
    public void setGiveResource(ResourceType resource)
    {

    }

    /**
     * <ul>
     * <li>disable trade button
     * <li>redisplay the trade Options for getting resource
     * </ul>
     */
    @Override
    public void unsetGetValue()
    {

    }

    /**
     * <ul>
     * <li>disable trade button
     * <li>redisplay the trade Options for getting resource
     * </ul>
     */
    @Override
    public void unsetGiveValue()
    {

    }

    @Override
    public void update(Observable o, Object arg)
    {

    }
}
