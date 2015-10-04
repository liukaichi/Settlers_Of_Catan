package client.model;

import client.utils.BufferedReaderParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.model.ClientModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * ClientModel Tester.
 *
 * @author <Authors name>
 * @since <pre>Oct 3, 2015</pre>
 * @version 1.0
 */
public class ClientModelTester {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testDeserializer(){
        File file = new File("sample/complexJSONModel.json");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String json = BufferedReaderParser.parse(reader);
            ClientModel model = new ClientModel(json);

            assert(model.getBank() != null);
            assert(model.getChat() != null);
            assert(model.getGameInfo() != null);
            assert(model.getLog() != null);
            assert(model.getTurnTracker() != null);
            assert(model.getWinner() != null);



        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * Method: getGameInfo()
     *
     */
    @Test
    public void testGetGameInfo() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: setGameInfo(GameInfo gameInfo)
     *
     */
    @Test
    public void testSetGameInfo() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getBank()
     *
     */
    @Test
    public void testGetBank() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getChat()
     *
     */
    @Test
    public void testGetChat() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getLog()
     *
     */
    @Test
    public void testGetLog() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getMap()
     *
     */
    @Test
    public void testGetMap() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getTradeOffer()
     *
     */
    @Test
    public void testGetTradeOffer() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getTurnTracker()
     *
     */
    @Test
    public void testGetTurnTracker() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getVersion()
     *
     */
    @Test
    public void testGetVersion() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: getWinner()
     *
     */
    @Test
    public void testGetWinner() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: setWinner(PlayerIndex winner)
     *
     */
    @Test
    public void testSetWinner() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: setVersion(int version)
     *
     */
    @Test
    public void testSetVersion() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: canPlaceSettlement(PlayerIndex player, VertexLocation location)
     *
     */
    @Test
    public void testCanPlaceSettlement() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: canPlaceCity(PlayerIndex player, VertexLocation location)
     *
     */
    @Test
    public void testCanPlaceCity() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: canPlaceRoad(PlayerIndex player, EdgeLocation location)
     *
     */
    @Test
    public void testCanPlaceRoad() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: canMoveRobber(PlayerIndex player, HexLocation location)
     *
     */
    @Test
    public void testCanMoveRobber() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: placeRoad(PlayerIndex player, EdgeLocation location)
     *
     */
    @Test
    public void testPlaceRoad() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: placeSettlement(PlayerIndex player, VertexLocation location)
     *
     */
    @Test
    public void testPlaceSettlement() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: placeCity(PlayerIndex player, VertexLocation location)
     *
     */
    @Test
    public void testPlaceCity() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: moveRobber(PlayerIndex player, HexLocation location)
     *
     */
    @Test
    public void testMoveRobber() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: toString()
     *
     */
    @Test
    public void testToString() throws Exception {

    }


} 
