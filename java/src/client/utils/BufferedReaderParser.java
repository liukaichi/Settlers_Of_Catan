package client.utils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by liukaichi on 10/1/2015.
 */
public class BufferedReaderParser
{
    /**
     * Takes in a BufferedReader and will return the string contained in the reader.
     * @param reader the BufferedReader to be parsed.
     * @return
     */
    public static String parse(BufferedReader reader){
        String response = "";
        try
        {
            StringBuilder builder = new StringBuilder();
            String string;
            while ((string = reader.readLine()) != null)
            {
                builder.append(string);
            }

            response = builder.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }
}
