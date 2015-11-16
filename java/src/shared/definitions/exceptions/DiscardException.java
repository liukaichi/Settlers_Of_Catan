package shared.definitions.exceptions;

/**
 * Exception thrown when unable to discard
 *
 * @author dtaylor
 */
public class DiscardException extends CatanException
{

    /**
     * @param string the message to throw.
     */
    public DiscardException(String string)
    {
        super(string);
    }
}
