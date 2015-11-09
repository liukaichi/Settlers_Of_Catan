package shared.communication;

import java.lang.reflect.Type;

import com.google.gson.*;
import server.facade.AbstractServerFacade;
import shared.definitions.exceptions.SignInException;

/**
 * Credentials class holds the login information for each player. This validates
 * that the user can login and begin game play.
 *
 * @author amandafisher
 *
 */
public class Credentials implements JsonSerializer<Credentials>, CatanCommand
{

    private Username username;
    private Password password;

    private Credentials()
    {
        try
        {
            this.username = new Username("user");
            this.password = new Password("password");
        } catch (SignInException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Creates credentials with the given username and password.
     * @param username the username.
     * @param password the password.
     * @throws SignInException if either the username or password are invalid.
     */
    public Credentials(String username, String password) throws SignInException
    {
        this();
        setUsername(username);
        setPassword(password);
    }
    AbstractServerFacade facade;
    public Credentials(String json, AbstractServerFacade facade)
    {
        this(json);
        this.facade = facade;
    }

    /**
     * Instantiate a Credentials object from JSON.
     * @param json JSON of the Credentials
     */
    public Credentials(String json)
    {
        this();
        JsonParser parser = new JsonParser();
        JsonObject credentialsObject = (JsonObject) parser.parse(json);
        try
        {
            this.setUsername(credentialsObject.getAsJsonPrimitive("username").getAsString());
        }
        catch (SignInException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getUsername()
    {
        return username.getUsername();
    }

    public void setUsername(String username) throws SignInException
    {
        this.username.setUsername(username);
    }

    public Password getPassword()
    {
        return password;
    }

    public void setPassword(String password) throws SignInException
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
        JsonObject credentials = new JsonObject();
        credentials.addProperty("username", src.getUsername());
        credentials.addProperty("password", src.password.getPasswordPlainText());
        return credentials;
    }

    @Override public String execute(int gameID)
    {
        try
        {
            facade.signInUser(this);
            return "SUCCESS";
        } catch (SignInException e)
        {
            return e.getMessage();
        }
    }
}
