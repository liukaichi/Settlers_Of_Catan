package client.communication;

import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.model.ClientModel;
import shared.model.message.MessageLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Game history controller implementation
 */
public class GameHistoryController extends ObserverController implements IGameHistoryController
{
    public GameHistoryController(IGameHistoryView view)
    {

        super(view);

        //initFromModel();
    }

    @Override
    public IGameHistoryView getView()
    {

        return (IGameHistoryView) super.getView();
    }

    private void initFromModel(ClientModel model)
    {
        ClientFacade facade = ClientFacade.getInstance();
        // <temp>
        List<MessageLine> messages =  model.getLog().getMessages();
        ArrayList<LogEntry> entries = new ArrayList<>();
        for (MessageLine messageLine : messages){
            entries.add(new LogEntry(facade.getColorByName(messageLine.getSourceName()), messageLine.getMessage()));
        }
/*

        List<LogEntry> entries = new ArrayList<LogEntry>();
        entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
        entries.add(new LogEntry(CatanColor.ORANGE,
                "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
        entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
        entries.add(new LogEntry(CatanColor.ORANGE,
                "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
        entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
        entries.add(new LogEntry(CatanColor.ORANGE,
                "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
        entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
        entries.add(new LogEntry(CatanColor.ORANGE,
                "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
*/

        getView().setEntries(entries);

        // </temp>
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;
        this.initFromModel(model);

    }

}
