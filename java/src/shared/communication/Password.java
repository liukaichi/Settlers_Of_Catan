package shared.communication;

import shared.definitions.StructureType;
import shared.definitions.exceptions.InvalidCredentialsException;

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
     * @throws InvalidCredentialsException if the password is not a valid password.
     */
    public Password(String password) throws InvalidCredentialsException
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
     * @throws InvalidCredentialsException if the password is invalid.
     */
    public void setPassword(String password) throws InvalidCredentialsException
    {
        validatePassword(password);
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password1 = (Password) o;

        return !(password != null ? !password.equals(password1.password) : password1.password != null);

    }

    @Override
    public int hashCode() {
        return password != null ? password.hashCode() : 0;
    }

    /**
     * Validates a password.
     * @param password the password to validate.
     * @throws InvalidCredentialsException if the password is invalid.
     */
    private void validatePassword(String password) throws InvalidCredentialsException
    {
        if (allowedInvalidPasswords.contains(password))
            return;

        final int MIN_PASS_LENGTH = 5;

        if (password.length() < MIN_PASS_LENGTH)
        {
            throw new InvalidCredentialsException("Password must be at least 5 characters.");
        } else
        {
            for (char c : password.toCharArray())
            {
                if (!Character.isLetterOrDigit(c) && c != '_' && c != '-')
                {
                    throw new InvalidCredentialsException("Password consists of invalid characters.");
                }
            }
        }
    }

    public String toString(){
        return password;
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
