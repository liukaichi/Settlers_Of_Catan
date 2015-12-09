/**
 *
 */
package shared.definitions.exceptions;

/**
 * @author cstaheli
 */
public class GameQueryException extends CatanException
{

    private static final long serialVersionUID = 1135927085600568451L;

    /**
     * @param string the message to throw.
     */
    public GameQueryException(String string)
    {
        super(string);
    }

}
