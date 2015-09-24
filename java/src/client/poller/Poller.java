package client.poller;

import server.proxy.IProxy;
import shared.model.ClientModel;

/**
 * Created by liukaichi on 9/19/2015.
 */
public class Poller  {

    private IProxy proxy;
    private int currentVersion;


    Poller(IProxy proxy)
    {
        this.proxy = proxy;
    }

    public void setProxy(IProxy proxy)
    {

    }

    /** Goes through the Proxy to check for updates in the server's model.
     * Executes updateModel() if model has changed.
     */
    public void poll()
    {

    }


    /** Replaces the old model with the given new one.
     *
     * @param newModel the new model received from the server.
     */
    public void updateModel(ClientModel newModel)
    {

    }

}
