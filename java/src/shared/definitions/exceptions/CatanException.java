package shared.definitions.exceptions;

/**
 * General Catan game exception
 *
 * @author amandafisher
 */
public class CatanException extends Exception
{

    private static final long serialVersionUID = 2001295725549176680L;

    /**
     * @param string
     */
    public CatanException(String string)
    {
        super(string);
    }

    public CatanException()
    {

    }

    /**
     * @param string
     * @param e
     */
    public CatanException(String string, Exception e)
    {
        super(string, e);
    }

}
