package shared.definitions.exceptions;

/**
 * Exception that is thrown when the player tries to buy a structure but the
 * space or edge is already occupied by another player
 *
 * @author amandafisher
 */
public class PlacementException extends CatanException
{

    /**
     * @param string
     */
    public PlacementException(String string)
    {
        super(string);
        // TODO Auto-generated constructor stub
    }

    /**
     *
     */
    public PlacementException()
    {
        // TODO Auto-generated constructor stub
    }

}
