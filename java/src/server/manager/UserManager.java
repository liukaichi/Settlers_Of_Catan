package server.manager;

import server.plugin.IPersistenceEngine;
import shared.communication.Credentials;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.InvalidCredentialsException;
import shared.definitions.exceptions.SignInException;

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
    private Map<Integer, Credentials> credentials;
    private IPersistenceEngine persistenceEngine;

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
            credentials.put(0, new Credentials("Sam", "sam"));
            credentials.put(1, new Credentials("Brooke", "brooke"));
            credentials.put(8, new Credentials("sheila", "parker"));
            credentials.put(10, new Credentials("Pete", "pete"));
            credentials.put(11, new Credentials("Mark", "mark"));
            credentials.put(12, new Credentials("Cache", "cache"));
            credentials.put(13, new Credentials("Amanda", "amanda"));
            credentials.put(14, new Credentials("Justin", "justin"));
            credentials.put(15, new Credentials("David", "david"));
            credentials.put(16, new Credentials("Adrian", "adrian"));
        } catch (SignInException e)
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
        User user = persistenceEngine.getUser(credentials);
        if (user != null)
        {
            return user;
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
        User user = getUserFromCredentials(credentials);
        if(user == null)
        {
            int userID = persistenceEngine.registerUser(credentials);
            if (userID == -1)
            {
                throw new ExistingRegistrationException("Failed to register - someone already has that username.");
            } else
            {
                // playerID is the new size of the credentials
                this.credentials.put(userID, credentials);
                return new User(credentials, userID);
            }
        }
        else
        {
            return user;
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
        if (this.credentials.containsKey(id))
        {
            return new User(this.credentials.get(id), id);
        }
        return null;
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

    public void setPersistenceEngine(IPersistenceEngine persistenceEngine)
    {
        this.persistenceEngine = persistenceEngine;

    }

    public void loadUsers()
    {
        credentials = persistenceEngine.getAllUsers();
    }
}
