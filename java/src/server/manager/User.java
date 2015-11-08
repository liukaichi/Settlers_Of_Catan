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

    public User(Credentials credentials, int playerID)
    {
        this.credentials = credentials;
        this.playerID = playerID;
    }

    public void assignUserID(int id)
    {
        this.playerID = id;
    }
}
