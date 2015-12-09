package shared.definitions.exceptions;

/**
 * Exception that is thrown when the player tries to buy a structure but the
 * space or edge is already occupied by another player
 *
 * @author amandafisher
 */
public class PlacementException extends CatanException
{

    private static final long serialVersionUID = -7708829423472119646L;

    /**
     * @param string the message to throw.
     */
    public PlacementException(String string)
    {
        super(string);
    }


}
