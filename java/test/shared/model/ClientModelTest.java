package shared.model;

import client.utils.BufferedReaderParser;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by liukaichi on 10/3/2015.
 */
public class ClientModelTest
{

    @Test
    public void testDeserializer(){
        File file = new File("sample/complexJSONModel.json");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String json = BufferedReaderParser.parse(reader);
            ClientModel model = new ClientModel(json);

            assert(model.getBank() != null);
            assert(model.getMap() != null);
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

    @Test public void testToString() throws Exception
    {
        ClientModel model1 = new ClientModel(new String(Files.readAllBytes(Paths.get("sample/complexJSONModel.json"))));
        ClientModel model2 = new ClientModel(model1.toString());
        assertTrue(model1.equals(model2));
        assertTrue(model2.equals(model1));
    }
}