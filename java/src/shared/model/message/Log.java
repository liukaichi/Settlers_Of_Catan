package shared.model.message;

/**
 * Represents the log of messages in the chat.
 *
 * @see MessageLine
 */
public class Log extends MessageList
{
    public Log()
    {
        super();
    }
    /**
     * Initializes the Log from Json.
     *
     * @param json the Json to initialize from.
     */
    public Log(String json)
    {
        super(json);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMessages() == null) ? 0 : getMessages().hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Log other = (Log) obj;
        if (getMessages() == null)
        {
            if (other.getMessages().toString() != null)
                return false;
        } else if (!(getMessages().toString()).equals(other.getMessages().toString()))
            return false;
        return true;
    }
}