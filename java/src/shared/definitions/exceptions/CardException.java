package shared.definitions.exceptions;

/**
 * Exception that is thrown when involving a card
 *
 * @author dtaylor
 */
public class CardException extends CatanException
{

    private static final long serialVersionUID = -5169694020436609708L;

    /**
     * @param string the message to accompany the exception.
     */
    public CardException(String string)
    {
        super(string);
    }
}
