package server.manager;

import shared.communication.Credentials;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.InvalidCredentialsException;

import java.util.*;

/**
 * A Manager that keeps track of all of the users that have connections with the server. When a new user registers, the
 * user will be assigned a unique id. This id will be used to associate them with a certain game when they join it.
 *
 * @see User
 * @see Credentials
 * @see GameManager
 */
public class UserManager
{
    private static UserManager _instance;
    private HashMap<Integer, Credentials> credentials;

    private UserManager()
    {
        this.credentials = new HashMap<>();
        addDefaultUsers();

    }

    /**
     * Singleton pattern.
     *
     * @return the singleton instance of UserManager.
     */
    public static UserManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new UserManager();
        }
        return _instance;
    }

    private User getUserFromCredentials(Credentials credentials)
    {
        User user = new User(credentials, -1);
        for (Map.Entry<Integer, Credentials> entry : this.credentials.entrySet())
        {
            if (Objects.equals(credentials, entry.getValue()))
            {
                user.assignUserID(entry.getKey());
                break;
            }
        }
        return user;
    }

    private void addDefaultUsers()
    {
        try
        {
            credentials.put(1, new Credentials("Cache", "cache"));
            credentials.put(2, new Credentials("Amanda", "amanda"));
            credentials.put(3, new Credentials("Justin", "justin"));
            credentials.put(4, new Credentials("David", "david"));
            credentials.put(5, new Credentials("Adrian", "adrian"));
            credentials.put(6, new Credentials("Sam", "sam"));
            credentials.put(7, new Credentials("Pete", "pete"));
        } catch (InvalidCredentialsException e)
        {
            //Do nothing.
        }
    }

    /**
     * Finds the user in the system that matches the credentials provided and returns the information necessary to set
     * the client's cookie.
     *
     * @param credentials the login credentials.
     * @return the User that will be used to set the client's cookie.
     * @throws InvalidCredentialsException if the credentials do not match any registered users.
     */
    public User userLogin(Credentials credentials) throws InvalidCredentialsException
    {
        if (this.credentials.containsValue(credentials))
        {
            return getUserFromCredentials(credentials);
        }
        throw new InvalidCredentialsException("Failed to login - bad username or password.");
    }

    /**
     * Registers a new user in the system and gives them a unique id.
     *
     * @param credentials the registration credentials.
     * @return the User that will be used to set the client's cookie.
     */
    public User userRegister(Credentials credentials) throws ExistingRegistrationException
    {

        if (this.credentials.containsValue(credentials))
        {
            throw new ExistingRegistrationException("Failed to register - someone already has that username.");
        } else
        {
            // playerID is the new size of the credentials
            this.credentials.put(this.credentials.size() + 1, credentials);
            return new User(credentials, this.credentials.size());
        }
    }

    /**
     * Gets the user with the specified id.
     *
     * @param id the id of the player.
     * @return the user associated with the player. Returns null if the user doesn't exist.
     */
    public User getUser(int id)
    {
        return new User(this.credentials.get(id), id);
    }

    /**
     * Gets the list of registered users.
     *
     * @return the list of registered users.
     */
    public List<User> getUsers()
    {
        List<User> users = new ArrayList<>();
        for (Map.Entry<Integer, Credentials> entry : this.credentials.entrySet())
        {
            users.add(new User(entry.getValue(), entry.getKey()));
        }
        return users;
    }
}
