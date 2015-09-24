package client.poller;

import server.proxy.IProxy;
import shared.model.ClientModel;

/**
 * This class will periodically poll the server to see if the model has been
 * changed.<br>
 * <br>
 * this will ensure that every player's Model is updated at all times. It will
 * use java.util.Timer, or java.util.TimerTask to periodically poll every 3-5
 * seconds. <br>
 * <br>
 * This Poller supports dependency injection with the IProxy interface.
 */
public class Poller
{

    private IProxy proxy;
    private int currentVersion;

    Poller(IProxy proxy)
    {
        this.proxy = proxy;
    }

    public void setProxy(IProxy proxy)
    {

    }

    /**
     * Goes through the Proxy to check for updates in the server's model.
     * Executes updateModel() if model has changed.
     */
    public void poll()
    {

    }

    /**
     * Replaces the old model with the given new one.
     *
     * @param newModel
     *        the new model received from the server.
     */
    public void updateModel(ClientModel newModel)
    {

    }

}
