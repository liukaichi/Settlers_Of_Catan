package shared.communication;

import java.lang.reflect.Type;

import com.google.gson.*;

/**
 * Credentials class holds the login information for each player. This validates
 * that the user can login and begin game play.
 * 
 * @author amandafisher
 *
 */
public class Credentials implements JsonSerializer<Credentials>
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

    public String getUsername()
    {
        return username.getUsername();
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
        return "{\"username\":\"" + username.getUsername() + "\",\"password\":\"" + password.getPasswordPlainText()
                + "\"}";
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
        }
        else if (!password.equals(other.password))
            return false;
        if (username == null)
        {
            if (other.username != null)
                return false;
        }
        else if (!username.equals(other.username))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(Credentials src, Type srcType, JsonSerializationContext context)
    {
        JsonObject creds = new JsonObject();
        creds.addProperty("username", src.getUsername());
        creds.addProperty("password", src.password.getPasswordPlainText());
        return creds;
    }

}