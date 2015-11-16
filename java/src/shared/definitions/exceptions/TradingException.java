package shared.definitions.exceptions;

/**
 * Exception thrown when unable to trade
 *
 * @author dtaylor
 */
public class TradingException extends CatanException
{

    /**
     * @param string the message to throw.
     */
    public TradingException(String string)
    {
        super(string);
    }
}
