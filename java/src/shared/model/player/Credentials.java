package shared.model.player;

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
