package shared.communication;

import shared.definitions.exceptions.SignInException;

import java.util.HashSet;

/**
 * Object that holds the player's password for the Catan game
 * 
 * @author amandafisher
 *
 */
public class Password
{
    private String password;
    private HashSet<String> allowedInvalidPasswords;

    public Password(String password) throws SignInException
    {
        buildAllowedPasswords();
        setPassword(password);
    }

    private void buildAllowedPasswords()
    {
        allowedInvalidPasswords = new HashSet<>();
        addAllowedPassword("sam");
        addAllowedPassword("pete");
        addAllowedPassword("mark");
        addAllowedPassword("ken");
    }

    public void addAllowedPassword(String password)
    {
        allowedInvalidPasswords.add(password);
    }
    public void setPassword(String password) throws SignInException
    {
        validatePassword(password);
        this.password = password;
    }

    public void validatePassword(String password) throws SignInException
    {
        if (allowedInvalidPasswords.contains(password))
            return;

        final int MIN_PASS_LENGTH = 5;

        if (password.length() < MIN_PASS_LENGTH)
        {
            throw new SignInException("Password must be at least 5 characters.");
        } else
        {
            for (char c : password.toCharArray())
            {
                if (!Character.isLetterOrDigit(c) && c != '_' && c != '-')
                {
                    throw new SignInException("Password consists of invalid characters.");
                }
            }
        }
    }

    /**
     * Returns the password.
     * 
     * @return the password.
     */
    public String getPasswordPlainText()
    {
        return password;
    }
}
