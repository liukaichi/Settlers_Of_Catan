/**
 *
 */
package shared.definitions.exceptions;

/**
 * @author cstaheli
 */
public class AddAIException extends CatanException
{

    private static final long serialVersionUID = 5978534234916699265L;

    /**
     * @param string the message to associate with the exception.
     */
    public AddAIException(String string)
    {
        super(string);
    }

}
