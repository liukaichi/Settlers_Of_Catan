package shared.definitions.exceptions;

/**
 * Exception thrown when registration is invalid because the person has already registered.
 */
public class ExistingRegistrationException extends SignInException
{
    private static final long serialVersionUID = 627977341872169085L;

    public ExistingRegistrationException(String string)
    {
        super(string);
    }

    public ExistingRegistrationException()
    {
        super();
    }
}