package shared.communication;

/**
 * Object that represents a string holding the username of the player.
 * 
 * @author amandafisher
 *
 */
public class Username
{

    private String username;

    private Username()
    {

    }

    public Username(String username)
    {
        this();
        setUsername(username);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        validateUsername();
        this.username = username;
    }

    private void validateUsername()
    {

    }

}
