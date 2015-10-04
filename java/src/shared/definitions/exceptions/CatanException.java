package shared.definitions.exceptions;

/**
 * General Catan game exception
 * 
 * @author amandafisher
 *
 */
public class CatanException extends Exception
{

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

}
