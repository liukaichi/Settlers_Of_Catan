package shared.model;

import client.utils.BufferedReaderParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test the ClientModel.
 */
public class ClientModelTest
{

    @Test public void testDeserializer()
    {
        File file = new File("sample/complexJSONModel.json");
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String json = BufferedReaderParser.parse(reader);
            ClientModel model = new ClientModel(json);

            assertNotNull(model.getBank());
            assertNotNull(model.getMap());
            assertNotNull(model.getChat());
            assertNotNull(model.getPlayers());
            assertNotNull(model.getLog());
            assertNotNull(model.getTurnTracker());
            assertNotNull(model.getWinner());

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Test public void testToString() throws Exception
    {
        String jsonModel = new String(Files.readAllBytes(Paths.get("sample/complexJSONModel.json")));
        ClientModel model1 = new ClientModel(jsonModel);
        String model1Json = model1.toString();
        ClientModel model2 = new ClientModel(model1Json);
        ClientModel model3 = new ClientModel(jsonModel);
        assertTrue(model1.equals(model2));
        assertTrue(model2.equals(model1));
        assertTrue(model1.equals(model3));
        assertTrue(model3.equals(model1));
    }
}