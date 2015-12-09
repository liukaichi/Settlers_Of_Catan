package shared.definitions.exceptions;

/**
 * Exception thrown when unable to discard
 *
 * @author dtaylor
 */
public class DiscardException extends CatanException
{

    private static final long serialVersionUID = 3274594918017942511L;

    /**
     * @param string the message to throw.
     */
    public DiscardException(String string)
    {
        super(string);
    }
}
