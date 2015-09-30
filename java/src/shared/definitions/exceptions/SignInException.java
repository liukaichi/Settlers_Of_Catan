package shared.definitions.exceptions;

/**
 * Exception thrown when unable to sign in
 * 
 * @author dtaylor
 *
 */
public class SignInException extends CatanException
{

    /**
     * @param string
     */
    public SignInException(String string)
    {
        super(string);
    }

    /**
     * 
     */
    public SignInException()
    {
        // TODO Auto-generated constructor stub
    }

}
