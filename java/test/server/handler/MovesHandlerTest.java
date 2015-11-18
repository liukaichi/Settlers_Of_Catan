package server.handler;

import client.proxy.ServerProxy;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Server;
import shared.communication.Credentials;
import shared.communication.JoinGameRequest;
import shared.communication.moveCommands.FinishTurnCommand;
import shared.communication.moveCommands.RollNumberCommand;
import shared.communication.moveCommands.SendChatCommand;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.GameQueryException;
import shared.definitions.exceptions.InvalidCredentialsException;
import shared.model.ClientModel;

import static org.junit.Assert.*;

/**
 * Created by liukaichi on 11/17/2015.
 */
public class MovesHandlerTest
{
    static ServerProxy proxy = new ServerProxy();

    @BeforeClass public static void setupServer()
    {
        String args[] = {};
        Server.main(args);

    }

    @Test public void movesTests()
    {
        //without cookie tests
        noCookieTests();

        try
        {
            proxy.userLogin(new Credentials("Justin", "justin"));
            proxy.joinGame(new JoinGameRequest(0, CatanColor.PUCE));
        } catch (InvalidCredentialsException e)
        {
            System.err.println("Couldn't login for moves handler setup.");
            fail();
        } catch (GameQueryException e)
        {
            System.err.println("Couldn't join a game for setup");
            fail();
            e.printStackTrace();
        }
        sendChat();
    }

    private void noCookieTests()
    {
        try
        {
            proxy.finishTurn(new FinishTurnCommand(PlayerIndex.PLAYER_0));
            fail();
        }catch(Exception e)
        {
            //should throw errors
        }
    }

    public void sendChat()
    {
        proxy.sendChat(new SendChatCommand(PlayerIndex.PLAYER_0, "yo."));
    }
    public void rollNumber()
    {
        proxy.rollNumber(new RollNumberCommand(PlayerIndex.PLAYER_0, 12));
    }
    public void robPlayer()
    {

    }
    public void finishTurn()
    {

    }
    public void buyDevCard()
    {

    }
    public void Year_of_Plenty()
    {

    }
    public void Road_Building()
    {

    }
    public void Soldier()
    {

    }
    public void Monopoly()
    {

    }
    public void Monument()
    {

    }
    public void buildRoad()
    {

    }
    public void buildSettlement()
    {

    }
    public void buildCity()
    {

    }
    public void offerTrade()
    {

    }
    public void acceptTrade()
    {

    }
    public void maritimeTrade()
    {

    }
    public void discardCards()
    {

    }
}