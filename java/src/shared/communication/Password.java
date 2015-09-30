package shared.communication;

/**
 * Object that holds the player's password for the Catan game
 * 
 * @author amandafisher
 *
 */
public class Password
{
    private String password;

    public Password(String password)
    {
        setPassword(password);
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
