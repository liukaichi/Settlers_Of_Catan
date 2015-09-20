package client.poller;

import server.proxy.IProxy;
import shared.model.ClientModel;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by liukaichi on 9/19/2015.
 */
public class Poller implements IPoller {

    private IProxy proxy;
    private ClientModel model;
    private ScheduledExecutorService scheduler;

    @Override
    public void setProxy(IProxy proxy) {

    }

    /** Goes through the Proxy to check for updates in the server's model.
     * Executes updateModel() if model has changed.
     */
    @Override
    public void poll() {

    }

    /** Uses the scheduler attribute to continuously run poll() in short intervals.
     */
    @Override
    public void runPoll() {

    }

    /** Replaces the old model with the given new one.
     *
     * @param newModel the new model received from the server.
     */
    @Override
    public void updateModel(ClientModel newModel) {

    }
}
