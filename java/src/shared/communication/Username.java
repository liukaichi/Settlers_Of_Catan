package shared.communication;

/**
 * Object that represents a string holding the username of the player.
 * @author amandafisher
 *
 */
public class Username
{

    String username;

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
        this.username = username;
    }

    private Username()
    {
        setUsername("username");
    }
}
