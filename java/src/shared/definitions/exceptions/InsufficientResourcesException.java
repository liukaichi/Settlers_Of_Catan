package shared.definitions.exceptions;

/**
 * Exception thrown when a player attempts to buy a structure or development
 * card but does not have sufficient resources
 *
 * @author amandafisher
 */
public class InsufficientResourcesException extends CatanException
{

    private static final long serialVersionUID = -3727116331200872216L;

    /**
     * @param string the message to throw.
     */
    public InsufficientResourcesException(String string)
    {
        super(string);
    }


}
