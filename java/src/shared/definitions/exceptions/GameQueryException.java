/**
 *
 */
package shared.definitions.exceptions;

/**
 * @author cstaheli
 */
public class GameQueryException extends CatanException
{

    /**
     * @param string the message to throw.
     */
    public GameQueryException(String string)
    {
        super(string);
    }

}
