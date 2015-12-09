package shared.definitions.exceptions;

/**
 * Exception thrown when unable to sign in
 *
 * @author dtaylor
 */
public class InvalidCredentialsException extends SignInException
{

    private static final long serialVersionUID = 6754638489139466638L;

    /**
     * Creates a new exception with a message.
     *
     * @param message the message to be thrown.
     */
    public InvalidCredentialsException(String message)
    {
        super(message);
    }

    /**
     * Creates a new exception with a message and stack trace/exception.
     *
     * @param message the message to be thrown.
     * @param e       the stack trace/exception.
     */
    public InvalidCredentialsException(String message, Exception e)
    {
        super(message, e);
    }

}
