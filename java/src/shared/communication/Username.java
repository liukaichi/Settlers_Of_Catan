package shared.communication;

import shared.definitions.exceptions.SignInException;

/**
 * Object that represents a string holding the username of the player.
 * 
 * @author amandafisher
 *
 */
public class Username
{

    private String username;

    private Username()
    {

    }

    public Username(String username) throws SignInException
    {
        this();
        setUsername(username);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username) throws SignInException
    {
        validateUsername(username);
        this.username = username;
    }

    private void validateUsername(String username) throws SignInException
    {
        final int MIN_UNAME_LENGTH = 3;
        final int MAX_UNAME_LENGTH = 7;

        if (username.length() < MIN_UNAME_LENGTH || username.length() > MAX_UNAME_LENGTH)
        {
            throw new SignInException("Username must be between 3 and 7 characters.");
        }
        else
        {
            for (char c : username.toCharArray())
            {
                if (!Character.isLetterOrDigit(c) && c != '_' && c != '-')
                {
                    throw new SignInException("Username consists of invalid characters!");
                }
            }
        }
    }

}
