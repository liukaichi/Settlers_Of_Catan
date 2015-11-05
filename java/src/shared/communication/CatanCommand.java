package shared.communication;

import server.facade.IServerFacade;

/**
 * This is the Super Class for all commands sent across this server. It is intended that ServerHandlers will be able to
 * take server contexts and create classes from the context name, and call Object.execute() on them, and the
 * appropriate action will be taken, based on the object.<br><br>
 * For example, suppose that a context is moves/buildRoad. The MovesHandler will look at the last part of that,
 * capitalize the first letter, and append Command to it. Then, the handler would then only have to call
 * Class.forName(BuildRoadCommand).execute()
 * @see <a href="http://stackoverflow.com/questions/5658182/initializing-a-class-with-class-forname-and-which-have-a-constructor-which-tak">
 *     Using Constructors with Class.forName()</a>
 */
public interface CatanCommand
{
    /**
     * Executes a command on the server side. <br>
     * This is to be implemented in different ways depending on the type of command.
     * @return the Json response for this command. Typically, this will be a serialized form of the CatanModel, but
     * some commands return some other response object.
     * @param facade
     */
    String execute(IServerFacade facade);
}
