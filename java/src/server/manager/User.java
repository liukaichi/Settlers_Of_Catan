package server.manager;

import shared.communication.Credentials;

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
     * @param credentials the credentials of the user.
     * @param playerID the id of the player.
     */
    public User(Credentials credentials, int playerID)
    {
        this.credentials = credentials;
        this.playerID = playerID;
    }

    /**
     * Assigns an id to the User.
     * @param id the id to assign.
     */
    public void assignUserID(int id)
    {
        this.playerID = id;
    }
}