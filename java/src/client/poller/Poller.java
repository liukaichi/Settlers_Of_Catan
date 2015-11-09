package client.poller;

import client.facade.ClientFacade;
import client.proxy.IProxy;
import shared.model.ClientModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will periodically pollTask the server to see if the model has been
 * changed.<br>
 * <br>
 * this will ensure that every player's Model is updated at all times. It will
 * use java.util.Timer, or java.util.TimerTask to periodically pollTask every 3-5
 * seconds. <br>
 * <br>
 * This Poller supports dependency injection with the IProxy interface.
 */
public class Poller
{

    public void setProxy(IProxy proxy)
    {
        this.proxy = proxy;
        setPollProxy(proxy);
    }

    private IProxy proxy;
    private PollTask pollTask;
    private int currentVersion = 0;
    Timer timer;

    public Poller(IProxy proxy)
    {
        this.proxy = proxy;
        ClientModel serverModel = proxy.getGameState(currentVersion);
        if (serverModel != null)
        {
            currentVersion = serverModel.getVersion();
        }
        pollTask = new PollTask(this.proxy);

        timer = new Timer();
        timer.schedule(pollTask, 1, 3000);
    }

    public void setPollProxy(IProxy newProxy)
    {
        pollTask.setProxy(newProxy);
    }

    public int getCurrentVersion()
    {
        return currentVersion;
    }

    private PollTask getPollTask()
    {
        return pollTask;
    }

    public void stopPoll()
    {
        timer.cancel();
    }


    /*---------POLL INNER CLASS----------*/

    /**
     * This class extends TimerTask. This will allow for Poller to be able to run RollTask() on regular intervals
     */
    public class PollTask extends TimerTask
    {

        private IProxy proxy;
        ClientFacade facade = ClientFacade.getInstance();

        PollTask(IProxy proxy)
        {
            this.proxy = proxy;
        }

        public void setProxy(IProxy proxy)
        {
            this.proxy = proxy;
        }

        /**
         * Goes through the Proxy to check for updates in the server's model.
         * Executes updateModel() if model has changed.
         *
         * @return returns true if model was updated. False if model stayed the same.
         * @pre When the model changes it must update the version number.
         * @post The ClientFacade will hold an updated model.
         */
        public boolean poll()
        {
            ClientModel serverModel = proxy.getGameState(currentVersion);
            if (serverModel != null)
            {
                updateModel(serverModel);
                return true;
            }
            return false;
        }

        /**
         * Replaces the old model with the given new one.
         *
         * @param newModel the new model received from the server.
         */

        public void updateModel(ClientModel newModel)
        {

            facade.getModel().updateModel(newModel);
            currentVersion = newModel.getVersion();
            facade.updateClientPlayer();
        }

        @Override public void run()
        {
            poll();
        }
    }

}
