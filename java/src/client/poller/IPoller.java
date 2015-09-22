package client.poller;

import server.proxy.IProxy;
import shared.model.ClientModel;

/**
 *
 * The Server Poller should be fairly simple to design and implement. It should
 * call the Server Proxy to retrieve the current model state, and then update
 * the local model’s state with the returned JSON data. Design your Server
 * Poller to use dependency injection so that it can be easily configured to use
 * either the “real” or “mock” Server Proxy implementation. This means that
 * rather than calling “new” internally to create a proxy object, the poller
 * should instead have a constructor parameter or setter that can be used to
 * pass in the proxy it should use
 */
public interface IPoller
{

    void setProxy(IProxy proxy);

    /**
     * Goes through the Proxy to check for updates in the server's model.
     * Executes updateModel() if model has changed.
     */
    void poll();

    /**
     * Uses a ScheduledExecutorService to continuously run poll() in short
     * intervals.
     *
     */
    void runPoll();

    /**
     * Replaces the old model with the given new one.
     *
     * @param newModel
     *        the new model received from the server.
     */
    void updateModel(ClientModel newModel);

}
