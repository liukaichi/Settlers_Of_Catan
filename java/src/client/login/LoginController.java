package client.login;

import javax.xml.bind.ValidationException;

import client.base.*;
import client.facade.ClientFacade;
import client.misc.IMessageView;
import shared.communication.Credentials;
import shared.definitions.exceptions.SignInException;

/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController
{

    private IMessageView messageView;
    private IAction loginAction;

    /**
     * LoginController constructor
     *
     * @param view
     *        Login view
     * @param messageView
     *        Message view (used to display error messages that occur during the
     *        login process)
     */
    public LoginController(ILoginView view, IMessageView messageView)
    {

        super(view);

        this.messageView = messageView;
    }

    public ILoginView getLoginView()
    {

        return (ILoginView) super.getView();
    }

    public IMessageView getMessageView()
    {

        return messageView;
    }

    /**
     * Sets the action to be executed when the user logs in
     *
     * @param value
     *        The action to be executed when the user logs in
     */
    public void setLoginAction(IAction value)
    {

        loginAction = value;
    }

    /**
     * Returns the action to be executed when the user logs in
     *
     * @return The action to be executed when the user logs in
     */
    public IAction getLoginAction()
    {

        return loginAction;
    }

    @Override
    public void start()
    {

        getLoginView().showModal();
    }

    /**
     * get username and password from the view. Send verification request to
     * server if it works: Create any data objects you need for a player in the
     * pre-game state close theModal, loginAction.execute else show a dialogue
     * to reprompt user for info
     */
    @Override
    public void signIn()
    {
        String username = getLoginView().getLoginUsername();
        String password = getLoginView().getLoginPassword();
        Credentials credentials = new Credentials(username, password);
        try
        {
            ClientFacade.getInstance().signInUser(credentials);
            // If log in succeeded
            getLoginView().closeModal();
            loginAction.execute();
        }
        catch (SignInException e)
        {
            getMessageView().setTitle("Error Yo!");
            getMessageView().setMessage("Yo Homie, that login shiz there aint gonna work!");
            getMessageView().showModal();
        }

    }

    /**
     * pretty much the same as above but with different error message
     */
    @Override
    public void register()
    {

        try
        {
            String username = getLoginView().getRegisterUsername();
            if (username.equals(""))
            {
                throw new ValidationException("Invalid Username/Password");
            }
            String password = getLoginView().getRegisterPassword();
            Credentials credentials = new Credentials(username, password);
            ClientFacade.getInstance().registerUser(credentials);
            // If register succeeded
            getLoginView().closeModal();
            loginAction.execute();
        }
        catch (SignInException e)
        {
            getMessageView().setTitle("Error Homes!");
            getMessageView().setMessage("Yo Homie, that register shiz there aint gonna work!");
            getMessageView().showModal();
        }
        catch (ValidationException e1)
        {
            getMessageView().setTitle("Invalid!");
            getMessageView().setMessage("Yo Homie, that name shiz is invalid!");
            getMessageView().showModal();
        }
    }
}
