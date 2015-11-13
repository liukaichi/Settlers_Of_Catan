package shared.definitions.exceptions;

/**
 * Thrown whenever a Sign-in problem happens.
 */
public class SignInException extends CatanException
{
    public SignInException(String s)
    {
        super(s);
    }

    public SignInException()
    {
        super();
    }

    public SignInException(String string, Exception e)
    {
        super(string, e);
    }
}
