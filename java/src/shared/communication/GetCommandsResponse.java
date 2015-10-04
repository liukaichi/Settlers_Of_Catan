/**
 * 
 */
package shared.communication;

import java.util.*;

import shared.communication.moveCommands.MoveCommand;

/**
 * @author cstaheli
 *
 */
public class GetCommandsResponse
{
    private List<MoveCommand> commands;

    /**
     * 
     */
    public GetCommandsResponse()
    {
        commands = new ArrayList<>();
    }

    /**
     * @param commands
     */
    public GetCommandsResponse(List<MoveCommand> commands)
    {
        this();
        this.commands = commands;
    }

}
