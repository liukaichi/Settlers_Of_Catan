import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import shared.communication.moveCommands.BuyDevCardCommand;
import shared.communication.moveCommands.MonopolyCommand;
import shared.communication.moveCommands.MonumentCommand;
import shared.communication.moveCommands.MoveCommand;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cstaheli on 12/4/2015.
 */
public class CommandAccessTest extends BaseTest
{
    CommandAccess dao;
    List<MoveCommand> cmdlist3, cmdlist111;

    @Before public void init() throws Exception
    {
        dao = new CommandAccess(engine);
    }

    public void populateGame3Commands() throws Exception {
        cmdlist3 = new ArrayList<>();
        cmdlist3.add(new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.WOOD));
        cmdlist3.add(new MonopolyCommand(PlayerIndex.PLAYER_2, ResourceType.WHEAT));
        cmdlist3.add(new MonopolyCommand(PlayerIndex.PLAYER_1, ResourceType.SHEEP));
        cmdlist3.add(new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.ORE));
        cmdlist3.add(new MonumentCommand(PlayerIndex.PLAYER_2));
        cmdlist3.add(new BuyDevCardCommand(PlayerIndex.PLAYER_1));

        engine.startTransaction();
        for (MoveCommand cmd : cmdlist3) {
                dao.saveCommand(3, cmd);
        }
        engine.endTransaction(true);
    }

    public void populateGame111Commands() throws Exception {
        cmdlist111 = new ArrayList<>();
        cmdlist111.add(new MonopolyCommand(PlayerIndex.PLAYER_2, ResourceType.WHEAT));
        cmdlist111.add(new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.ORE));
        cmdlist111.add(new BuyDevCardCommand(PlayerIndex.PLAYER_1));
        cmdlist111.add(new MonumentCommand(PlayerIndex.PLAYER_2));
        cmdlist111.add(new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.WOOD));
        cmdlist111.add(new MonopolyCommand(PlayerIndex.PLAYER_1, ResourceType.SHEEP));


        engine.startTransaction();
        for (MoveCommand cmd : cmdlist111) {
            dao.saveCommand(111, cmd);
        }
        engine.endTransaction(true);
    }

    @Test public void testSaveCommand() throws Exception
    {
//        int gameId = 3;
//        populateGame3Commands();
//
//        engine.startTransaction();
//        dao.saveCommand(gameId, new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.WOOD));
//        engine.endTransaction(true);
//
//        engine.startTransaction();
//        dao.
    }

    @Test public void testGetNumberOfCommandsInGame() throws Exception
    {
        populateGame3Commands();

        engine.startTransaction();
        int num = dao.getNumberOfCommandsInGame(3);
        engine.endTransaction(false);

        assertEquals(6, num);
    }

    @Test public void testGetAllCommands() throws Exception
    {
        List<MoveCommand> list;
        populateGame3Commands();

        engine.startTransaction();
        list = dao.getAllCommands(3);
        engine.endTransaction(false);

        assertEquals(6, list.size());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(cmdlist3.get(i).getClass(), list.get(i).getClass());
            assertEquals(cmdlist3.get(i).getPlayerIndex(), list.get(i).getPlayerIndex());
        }
    }

    @Test public void testGetAllCommandsAfter() throws Exception
    {

    }

    @Test public void testGetCommand() throws Exception
    {
        populateGame3Commands();
        populateGame111Commands();

        engine.startTransaction();
        MoveCommand cmd = dao.getCommand(3, 1);
        engine.endTransaction(false);

        assertEquals(cmdlist3.get(0).getPlayerIndex(), cmd.getPlayerIndex());
        assertEquals(cmdlist3.get(0).getClass(), cmd.getClass());


        engine.startTransaction();
        MoveCommand cmd2 = dao.getCommand(111, 1);
        engine.endTransaction(false);

        assertEquals(cmdlist111.get(0).getClass(), cmd2.getClass());
        assertEquals(cmdlist111.get(0).getPlayerIndex(), cmd2.getPlayerIndex());

    }
}