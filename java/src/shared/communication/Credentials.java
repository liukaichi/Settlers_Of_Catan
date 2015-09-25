package shared.communication;

/**
 * Credentials class holds the login information for each player. This validates
 * that the user can login and begin game play.
 * @author amandafisher
 *
 */
public class Credentials
{

    private Username username;
    private Password password;

    public Credentials()
    {

    }

    public Username getUsername()
    {
        return username;
    }

    public void setUsername(Username username)
    {
        this.username = username;
    }

    public Password getPassword()
    {
        return password;
    }

    public void setPassword(Password password)
    {
        this.password = password;
    }

}
