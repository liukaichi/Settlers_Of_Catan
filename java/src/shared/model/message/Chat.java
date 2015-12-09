package shared.model.message;

import java.io.Serializable;

/**
 * Represents the list of messages in the chat.
 *
 * @see MessageLine
 */
public class Chat extends MessageList
{

    private static final long serialVersionUID = 8868107196153454667L;

    public Chat()
    {
        super();
    }
    /**
     * Initializes the Chat log from Json.
     *
     * @param json the Json to initialize from.
     */
    public Chat(String json)
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

        Chat other = (Chat) obj;
        if (getMessages() == null)
        {
            if (other.getMessages().toString() != null)
                return false;
        } else if (!(getMessages().toString()).equals(other.getMessages().toString()))
            return false;
        return true;
    }
}
