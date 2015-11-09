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

    /**
     * Creates a password from te given string.
     * @param password the password.
     * @throws SignInException if the password is not a valid password.
     */
    public Password(String password) throws SignInException
    {
        buildAllowedPasswords();
        setPassword(password);
    }

    /**
     * Several passwords of people already existing in the games have short passwords that would be disallowed otherwise.
     */
    private void buildAllowedPasswords()
    {
        allowedInvalidPasswords = new HashSet<>();
        addAllowedPassword("sam");
        addAllowedPassword("pete");
        addAllowedPassword("mark");
        addAllowedPassword("ken");
    }

    /**
     * Adds an invalid password to the list of accepted passwords.
     * @param password the password to add.
     */
    public void addAllowedPassword(String password)
    {
        allowedInvalidPasswords.add(password);
    }

    /**
     * Inputs a password to be validated and added.
     * @param password the password.
     * @throws SignInException if the password is invalid.
     */
    public void setPassword(String password) throws SignInException
    {
        validatePassword(password);
        this.password = password;
    }

    /**
     * Validates a password.
     * @param password the password to validate.
     * @throws SignInException if the password is invalid.
     */
    private void validatePassword(String password) throws SignInException
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
     * Returns the password as plain text.
     * 
     * @return the password as plain text.
     */
    public String getPasswordPlainText()
    {
        return password;
    }
}
