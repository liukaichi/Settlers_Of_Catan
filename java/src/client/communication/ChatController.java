package client.communication;

import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.model.ClientModel;
import shared.model.message.MessageLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Chat controller implementation
 */
public class ChatController extends ObserverController implements IChatController
{

    public ChatController(IChatView view)
    {

        super(view);
    }

    @Override public IChatView getView()
    {
        return (IChatView) super.getView();
    }

    @Override public void sendMessage(String message)
    {
        ClientFacade.getInstance().sendMessage(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;

        List<MessageLine> messages = model.getChat().getMessages();
        ArrayList<LogEntry> entries = new ArrayList<>();
        for (MessageLine messageLine : messages)
        {
            entries.add(new LogEntry(
                    ClientFacade.getInstance().getPlayerByName(messageLine.getSourceName()).getPlayerColor(),
                    messageLine.getMessage()));
        }
        getView().setEntries(entries);

    }

}
