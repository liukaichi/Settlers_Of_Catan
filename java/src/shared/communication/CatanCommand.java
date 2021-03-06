package shared.communication;

import shared.definitions.exceptions.CatanException;

/**
 * This is the Super Class for all commands sent across this server. It is intended that ServerHandlers will be able to
 * take server contexts and create classes from the context name, and call Object.execute() on them, and the
 * appropriate action will be taken, based on the object.<br><br>
 * For example, suppose that a context is moves/buildRoad. The MovesHandler will look at the last part of that,
 * capitalize the first letter, and append Command to it. Then, the handler would then only have to call
 * Class.forName(BuildRoadCommand).execute()
 *
 * @see <a href="http://stackoverflow.com/questions/5658182/initializing-a-class-with-class-forname-and-which-have-a-constructor-which-tak">
 * Using Constructors with Class.forName()</a>
 */
public abstract class CatanCommand
{
    /**
     * Executes a command on the server side. <br>
     * This is to be implemented in different ways depending on the type of command.
     *
     * @param gameID the ID of the game for which to execute the command.
     * @return the Json response for this command. Typically, this will be a serialized form of the ClientModel, but
     * some commands return some other response object.
     * @see shared.model.ClientModel
     */
    public abstract String execute(int gameID) throws CatanException;
}
