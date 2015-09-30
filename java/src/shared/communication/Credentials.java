package shared.communication;

/**
 * Credentials class holds the login information for each player. This validates
 * that the user can login and begin game play.
 * 
 * @author amandafisher
 *
 */
public class Credentials
{

    private Username username;
    private Password password;

    public Credentials()
    {
        username = new Username("");
        password = new Password("");
    }

    public Credentials(String username, String password)
    {
        this();
        setUsername(username);
        setPassword(password);
    }

    public Username getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username.setUsername(username);
    }

    public Password getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password.setPassword(password);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credentials other = (Credentials) obj;
        if (password == null)
        {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null)
        {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}
