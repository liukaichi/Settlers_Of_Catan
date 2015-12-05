import server.ServerModel;
import server.plugin.IGameAccess;

import java.rmi.ServerException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * SQL Database Access Object for Games.
 */
public class GameAccess implements IGameAccess, IAccess
{
    private SQLiteEngine engine;
    private final static Logger LOGGER = Logger.getLogger(GameAccess.class.getName());

    public GameAccess(SQLiteEngine engine)
    {
        this.engine = engine;
    }

    @Override
    public void updateModel(int gameID, ServerModel game) throws Exception
    {
        PreparedStatement stmt = null;
        String query = "UPDATE Game SET Model = (" + game.toString() + ") WHERE GameID = " + gameID;
        stmt = engine.getConnection().prepareStatement(query);
        if (stmt.executeUpdate() != 1) {
            throw new ServerException("Could not update ServerModel for gameID " + gameID);
        }
    }

    @Override public void addCommand(int gameID) throws Exception
    {
        PreparedStatement stmt = null;
        String query = "UPDATE Game SET CurrentCommandNo = (CurrentCommandNo + 1) WHERE GameID = " + gameID;
        engine.startTransaction();
        stmt = engine.getConnection().prepareStatement(query);
        if (stmt.executeUpdate() != 1) {
            throw new ServerException("Could not update ServerModel for gameID " + gameID);
        }
    }

    @Override
    public void addGame(ServerModel game, String gameName) throws Exception
    {
        PreparedStatement stmt = null;
        ResultSet keyRS = null;
        String query = "INSERT into Game (Model, CurrentCommandNo, Name) VALUES " +
                "(?,?,?)";
        stmt = engine.getConnection().prepareStatement(query);
        stmt.setBlob(1, (Blob) game);
        stmt.setInt(2, 0);
        stmt.setString(3, gameName);
        if (stmt.executeUpdate() == 1) {
            Statement keyStmt = engine.getConnection().createStatement();
            keyRS = keyStmt.executeQuery("select last_insert_rowid()");
            keyRS.next();
            int id = keyRS.getInt(1);
        }
        else {
            throw new ServerException("Query wasn't executed properly to add a game");
        }
    }

    @Override
    public ServerModel getGame(int gameID) throws Exception
    {
        ServerModel result = null;
        PreparedStatement stmt;
        ResultSet rs;
        String query = "SELECT Model FROM Game WHERE GameID = " + gameID;
        stmt = engine.getConnection().prepareStatement(query);

        rs = stmt.executeQuery();
        if (!rs.isBeforeFirst()){
            return null;
        }
        while (rs.next()) {
            String json = rs.getString(1);

            result = new ServerModel(json);
        }

        return result;
    }

    @Override
    public List<ServerModel> getAllGames() throws Exception
    {
        List<ServerModel> result = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT Model FROM Game";
        stmt = engine.getConnection().prepareStatement(query);

        rs = stmt.executeQuery();
        if (!rs.isBeforeFirst()){
            return null;
        }
        while (rs.next()) {
            String json = rs.getString(1);
            result.add(new ServerModel(json));
        }

        return result;
    }

    /**
     *
     * @param gameID
     * @return number of commands
     */
    @Override public int getNumberOfCommands(int gameID) throws Exception
    {
        int result = 0;
        PreparedStatement stmt;
        ResultSet rs;
        String query = "SELECT CurrentCommandNo FROM Game WHERE GameID = " + gameID;
        stmt = engine.getConnection().prepareStatement(query);

        rs = stmt.executeQuery();
        if (!rs.isBeforeFirst()){
            return result;
        }
        while (rs.next()) {
            result = rs.getInt(1);
        }

        return result;
    }

    @Override public void initializeTable()
    {

    }
}
