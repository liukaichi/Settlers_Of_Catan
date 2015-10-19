package client.join;

import client.base.*;
import client.data.GameInfo;
import client.facade.ClientFacade;
import client.misc.IMessageView;
import shared.communication.CreateGameRequest;
import shared.definitions.CatanColor;

/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController
{

    private INewGameView newGameView;
    private ISelectColorView selectColorView;
    private IMessageView messageView;
    private IAction joinAction;
    private ClientFacade facade;

    /**
     * JoinGameController constructor
     *
     * @param view
     *        Join game view
     * @param newGameView
     *        New game view
     * @param selectColorView
     *        Select color view
     * @param messageView
     *        Message view (used to display error messages that occur while the
     *        user is joining a game)
     */
    public JoinGameController(IJoinGameView view, INewGameView newGameView, ISelectColorView selectColorView,
            IMessageView messageView)
    {

        super(view);

        setNewGameView(newGameView);
        setSelectColorView(selectColorView);
        setMessageView(messageView);
    }

    public IJoinGameView getJoinGameView()
    {

        return (IJoinGameView) super.getView();
    }

    /**
     * Returns the action to be executed when the user joins a game
     *
     * @return The action to be executed when the user joins a game
     */
    public IAction getJoinAction()
    {

        return joinAction;
    }

    /**
     * Sets the action to be executed when the user joins a game
     *
     * @param value
     *        The action to be executed when the user joins a game
     */
    public void setJoinAction(IAction value)
    {

        joinAction = value;
    }

    public INewGameView getNewGameView()
    {

        return newGameView;
    }

    public void setNewGameView(INewGameView newGameView)
    {

        this.newGameView = newGameView;
    }

    public ISelectColorView getSelectColorView()
    {

        return selectColorView;
    }

    public void setSelectColorView(ISelectColorView selectColorView)
    {

        this.selectColorView = selectColorView;
    }

    public IMessageView getMessageView()
    {

        return messageView;
    }

    public void setMessageView(IMessageView messageView)
    {

        this.messageView = messageView;
    }

    /**
     * get list of games from server, save them into your pre-game model
     * JoinGameView().setGames(list of games, your player info) showModal
     */
    @Override
    public void start()
    {
        // ListGamesResponse response = facade.listGames();
        // getJoinGameView().setGames((GameInfo[])
        // response.getGames().toArray(),
        // ClientFacade.getInstance().getClientPlayer());
        getJoinGameView().showModal();
    }

    @Override
    public void startCreateNewGame()
    {

        getNewGameView().showModal();
    }

    @Override
    public void cancelCreateNewGame()
    {

        getNewGameView().closeModal();
    }

    /**
     * <ul>
     * <li>Create a new Game board based on the options in the View(Random or
     * not)
     * <li>Send create game request to server
     * <li>update Game List closeModal
     * </ul>
     */
    @Override
    public void createNewGame()
    {
        boolean randomTiles = getNewGameView().getRandomlyPlaceHexes();
        boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
        boolean randomPorts = getNewGameView().getUseRandomPorts();
        String name = getNewGameView().getTitle();

        facade.createNewGame(new CreateGameRequest(randomTiles, randomNumbers, randomPorts, name));
        getNewGameView().closeModal();
    }

    /**
     * <ul>
     * <li>Iterate through player in GameInfo and disable each color that has
     * already been used in ColorSelectView
     * <li>check if you are already in
     * <li>if so, call JoinGame with the color you had already picked
     * </ul>
     */
    @Override
    public void startJoinGame(GameInfo game)
    {

        getSelectColorView().showModal();
    }

    @Override
    public void cancelJoinGame()
    {

        getJoinGameView().closeModal();
    }

    /**
     * call join game on server
     */
    @Override
    public void joinGame(CatanColor color)
    {

        // If join succeeded
        getSelectColorView().closeModal();
        getJoinGameView().closeModal();
        joinAction.execute();
    }

}
