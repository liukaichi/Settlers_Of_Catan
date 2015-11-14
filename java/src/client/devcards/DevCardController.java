package client.devcards;

import client.base.IAction;
import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.ClientModel;
import shared.model.bank.card.DevCards;
import shared.model.player.Player;

import java.util.Observable;

/**
 * "Dev card" controller implementation
 */
public class DevCardController extends ObserverController implements IDevCardController
{

    private IBuyDevCardView buyCardView;
    private IAction soldierAction;
    private IAction roadAction;
    private ClientFacade facade;

    /**
     * DevCardController constructor
     *
     * @param view          "Play dev card" view
     * @param buyCardView   "Buy dev card" view
     * @param soldierAction Action to be executed when the user plays a soldier card. It calls
     *                      "mapController.playSoldierCard()".
     * @param roadAction    Action to be executed when the user plays a road building card. It
     *                      calls "mapController.playRoadBuildingCard()".
     */
    public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, IAction soldierAction,
            IAction roadAction)
    {

        super(view);

        this.buyCardView = buyCardView;
        this.soldierAction = soldierAction;
        this.roadAction = roadAction;
        facade = ClientFacade.getInstance();
    }

    public IPlayDevCardView getPlayCardView()
    {
        return (IPlayDevCardView) super.getView();
    }

    public IBuyDevCardView getBuyCardView()
    {
        return buyCardView;
    }

    public void updateView()
    {
        Player player = facade.getPlayer();
        DevCards devCards = player.getBank().getDevCards();

        setCards(devCards);

        disableCards();
        //getPlayCardView().setCardAmount(DevCardType.MONUMENT,10);
        //getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
    }

    private void setCards(DevCards devCards)
    {
        getPlayCardView().setCardAmount(DevCardType.MONOPOLY, devCards.getCard(DevCardType.MONOPOLY).getAmountInHand());
        getPlayCardView().setCardAmount(DevCardType.MONUMENT, devCards.getCard(DevCardType.MONUMENT).getAmountInHand());
        getPlayCardView()
                .setCardAmount(DevCardType.ROAD_BUILD, devCards.getCard(DevCardType.ROAD_BUILD).getAmountInHand());
        getPlayCardView().setCardAmount(DevCardType.SOLDIER, devCards.getCard(DevCardType.SOLDIER).getAmountInHand());
        getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY,
                devCards.getCard(DevCardType.YEAR_OF_PLENTY).getAmountInHand());
    }

    private void disableCards()
    {
        getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, state.canPlayDevCard(DevCardType.MONOPOLY));
        getPlayCardView().setCardEnabled(DevCardType.MONUMENT, state.canPlayDevCard(DevCardType.MONUMENT));
        getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, state.canPlayDevCard(DevCardType.ROAD_BUILD));
        getPlayCardView().setCardEnabled(DevCardType.SOLDIER, state.canPlayDevCard(DevCardType.SOLDIER));
        getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, state.canPlayDevCard(DevCardType.YEAR_OF_PLENTY));
    }

    @Override public void startBuyCard()
    {
        if (getBuyCardView().isModalShowing())
            getBuyCardView().closeModal();
        getBuyCardView().showModal();
    }

    @Override public void cancelBuyCard()
    {
        if (getBuyCardView().isModalShowing())
            getBuyCardView().closeModal();
    }

    @Override public void buyCard()
    {
        state.buyDevCard();
        if (getBuyCardView().isModalShowing())
            getBuyCardView().closeModal();

    }

    @Override public void startPlayCard()
    {
        if (getPlayCardView().isModalShowing())
            getPlayCardView().closeModal();
        getPlayCardView().showModal();
    }

    @Override public void cancelPlayCard()
    {
        if (getPlayCardView().isModalShowing())
            getPlayCardView().closeModal();
    }

    @Override public void playMonopolyCard(ResourceType resource)
    {
        state.playMonopolyCard(resource);
    }

    @Override public void playMonumentCard()
    {
        state.playMonumentCard();
    }

    @Override public void playRoadBuildCard()
    {
        roadAction.execute();
    }

    @Override public void playSoldierCard()
    {
        soldierAction.execute();
    }

    @Override public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
    {
        state.playYearOfPlentyCard(resource1, resource2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override public void update(Observable o, Object arg)
    {
        //Does nothing at the moment.
        ClientModel model = (ClientModel) o;
        if (facade.getClientPlayer().getNormalizedPlayerIndex() != -1)
        {
            state.update(this, model, arg);
            updateView();
        }

    }

}
