package shared.model;

import client.utils.BufferedReaderParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

    @Test public void testSerializer() throws Exception
    {
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


            ClientModel serializedModel = new ClientModel(model.toString());
            assert(model.equals(serializedModel));

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}