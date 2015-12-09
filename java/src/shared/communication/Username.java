package shared.communication;

import shared.definitions.exceptions.InvalidCredentialsException;

import java.io.Serializable;

/**
 * Object that represents a string holding the username of the player.
 *
 * @author amandafisher
 */
public class Username implements Serializable
{

    private static final long serialVersionUID = -8198397907446071356L;
    private String username;

    private Username()
    {

    }

    public Username(String username) throws InvalidCredentialsException
    {
        this();
        setUsername(username);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username) throws InvalidCredentialsException
    {
        validateUsername(username);
        this.username = username;
    }

    private void validateUsername(String username) throws InvalidCredentialsException
    {
        final int MIN_UNAME_LENGTH = 3;
        final int MAX_UNAME_LENGTH = 7;

        if (username.length() < MIN_UNAME_LENGTH || username.length() > MAX_UNAME_LENGTH)
        {
            throw new InvalidCredentialsException("Username must be between 3 and 7 characters.");
        } else
        {
            for (char c : username.toCharArray())
            {
                if (!Character.isLetterOrDigit(c) && c != '_' && c != '-')
                {
                    throw new InvalidCredentialsException("Username consists of invalid characters!");
                }
            }
        }
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Username username1 = (Username) o;

        return !(username != null ? !username.equals(username1.username) : username1.username != null);

    }

    @Override public int hashCode()
    {
        return username != null ? username.hashCode() : 0;
    }
}
