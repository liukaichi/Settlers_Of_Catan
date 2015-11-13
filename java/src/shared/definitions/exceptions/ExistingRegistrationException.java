package shared.definitions.exceptions;

/**
 * Exception thrown when registration is invalid because the person has already registered.
 */
public class ExistingRegistrationException extends SignInException
{
    public ExistingRegistrationException(String string)
    {
        super(string);
    }
}
