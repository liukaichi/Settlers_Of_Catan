package shared.definitions.exceptions;

/**
 * Exception thrown when unable to sign in
 * 
 * @author dtaylor
 *
 */
public class InvalidCredentialsException extends CatanException
{

    /**
     * @param string
     */
    public InvalidCredentialsException(String string)
    {
        super(string);
    }

    /**
     * 
     */
    public InvalidCredentialsException()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param string
     * @param e
     */
    public InvalidCredentialsException(String string, Exception e)
    {
        super(string, e);
    }

}
