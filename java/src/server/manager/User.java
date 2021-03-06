package server.manager;

import shared.communication.Credentials;
import shared.definitions.exceptions.InvalidCredentialsException;

/**
 * Contains the server-side representation of a user.
 */
public class User
{
    private Credentials credentials;
    private int playerID;

    private User()
    {
        playerID = -1;
    }

    /**
     * Initializes a player with credentials and an id.
     *
     * @param credentials the credentials of the user.
     * @param playerID    the id of the player.
     */
    public User(Credentials credentials, int playerID)
    {
        this();
        this.credentials = credentials;
        this.playerID = playerID;
    }

    public User(String username, String password, int id)
    {
        this();
        try
        {
            this.credentials = new Credentials(username, password);
            this.playerID = id;
        } catch (InvalidCredentialsException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Assigns an id to the User.
     *
     * @param id the id to assign.
     */
    public void assignUserID(int id)
    {
        this.playerID = id;
    }

    public String toString()
    {
        return String.format("{\"name\":\"%s\", \"password\": \"%s\", \"playerID\": %d}", credentials.getUsername(),
                credentials.getPassword(), playerID);
    }

    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user.
     */
    public String getUserName()
    {
        return credentials.getUsername();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (credentials == null)
        {
            if (other.credentials != null)
                return false;
        } else if (!credentials.equals(other.credentials))
            return false;
        if (playerID != other.playerID)
            return false;
        return true;
    }
}
