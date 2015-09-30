package shared.definitions.exceptions;

/**
 * Exception thrown when a player attempts to buy a structure or development
 * card but does not have sufficient resources
 * 
 * @author amandafisher
 *
 */
public class InsufficientResourcesException extends CatanException
{

    /**
     * @param string
     */
    public InsufficientResourcesException(String string)
    {
        super(string);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    public InsufficientResourcesException()
    {
        // TODO Auto-generated constructor stub
    }

}
