/**
 * 
 */
package shared.communication;

import java.util.List;

import shared.communication.moveCommands.MoveCommand;

/**
 * @author cstaheli
 *
 */
public class PostCommandsRequest
{
    private List<MoveCommand> commands;

    /**
     * 
     */
    public PostCommandsRequest()
    {

    }

    /**
     * @param commands
     */
    public PostCommandsRequest(List<MoveCommand> commands)
    {
        this();
        this.commands = commands;
    }

}
