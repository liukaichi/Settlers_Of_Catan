package shared.definitions.exceptions;

/**
 * Exception thrown when unable to trade
 *
 * @author dtaylor
 */
public class TradingException extends CatanException
{

    private static final long serialVersionUID = 816871976938935244L;

    /**
     * @param string the message to throw.
     */
    public TradingException(String string)
    {
        super(string);
    }
}
