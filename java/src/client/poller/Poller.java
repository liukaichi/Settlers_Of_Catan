package client.poller;

import com.google.gson.JsonElement;
import server.proxy.IProxy;
import shared.model.ClientModel;

import java.util.TimerTask;

/**
 * Created by liukaichi on 9/19/2015.
 */
public class Poller extends TimerTask implements IPoller {

    private IProxy proxy;
    private int currentVersion;


    Poller(IProxy proxy){
        this.proxy = proxy;
    }

    @Override
    public void setProxy(IProxy proxy) {

    }

    /** Goes through the Proxy to check for updates in the server's model.
     * Executes updateModel() if model has changed.
     */
    @Override
    public void poll() {

    }


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

    /** Implements TimerTask's abstract run method.
     */
    @Override
    public void run() {

    }


    /**
     * Uses Gson to deserialize Json to a Client Model
     * @param element Json element of a Client Model
     * @return returns the deserialized ClientModel
     */
    private ClientModel deserialize(JsonElement element){
        return null;
    }
}
