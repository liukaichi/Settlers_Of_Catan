package server.manager;

import shared.communication.Credentials;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.InvalidCredentialsException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Manager that keeps track of all of the users that have connections with the server. When a new user registers, the
 * user will be assigned a unique id. This id will be used to associate them with a certain game when they join it.
 * @see User
 * @see Credentials
 * @see GameManager
 */
public class UserManager
{
    private static UserManager _instance;
    private HashMap<Integer, Credentials> creds;
    private List<User> users;

    private Map<Integer, User> idToUser;

    private Map<Credentials, User> credentialsToUser;

    private UserManager()
    {
        creds = new HashMap<>();
        users = new ArrayList<>();
//        addDefaultUsers();
        try{
            creds.put(1, new Credentials("Sam", "sam"));
        }
        catch (InvalidCredentialsException e){
            e.printStackTrace();
        }
    }
    
    private void addDefaultUsers()
    {
        idToUser = new HashMap<>();
        credentialsToUser = new HashMap<>();
        idToUser.put(1, new User("Cache", "cache", 1));
        idToUser.put(2, new User("Amanda", "amanda", 2));
        idToUser.put(3, new User("Justin", "justin", 3));
        idToUser.put(4, new User("David", "david", 4));
        idToUser.put(5, new User("Adrian", "adrian", 5));
        idToUser.put(6, new User("Sam", "sam", 6));
        idToUser.put(7, new User("Pete", "pete", 7));
    }

    /**
     * Singleton pattern.
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

    /**
     * Finds the user in the system that matches the credentials provided and returns the information necessary to set
     * the client's cookie.
     * @param credentials the login credentials.
     * @return the User that will be used to set the client's cookie.
     */
    public User userLogin(Credentials credentials)
    {
        return null;
    }

    /**
     * Registers a new user in the system and gives them a unique id.
     * @param credentials the registration credentials.
     * @return the User that will be used to set the client's cookie.
     */
    public User userRegister(Credentials credentials) throws ExistingRegistrationException
    {

        if(creds.containsValue(credentials)){
            throw new ExistingRegistrationException();
        }
        else {
            // playerID is the new size of the creds
            creds.put(creds.size() + 1, credentials);
            User user  = new User(credentials, creds.size());
            users.add(user);
            return user;
        }
    }

    /**
     * Gets the user with the specified id.
     * @param id the id of the player.
     * @return the user associated with the player. Returns null if the user doesn't exist.
     */
    public User getUser(int id)
    {
        if(id < users.size()){
            return users.get(id - 1);
        }
        //TODO this could throw an exception instead of returning null.
        return null;
    }

    public List<User> getUserList(){
        return users;
    }
}
