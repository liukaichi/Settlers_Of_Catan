package server.util;

import client.data.GameInfo;
import client.utils.BufferedReaderParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import server.ServerModel;
import shared.model.map.CatanMap;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Various File Utilities.
 *
 * @see <a href="http://stackoverflow.com/questions/4246360/java-loading-binary-files">Loading Binary Files </a>
 */
public class FileUtils
{ //
    private static final Map<String, String> MIMETYPES = makeMimeTypes();

    private static Map<String, String> makeMimeTypes()
    {
        Map<String, String> mimeTypes = new HashMap<>();
        mimeTypes.put(".js", "application/javascript");
        mimeTypes.put(".css", "text/css");
        mimeTypes.put(".html", "text/html");
        return mimeTypes;
    }

    public static byte[] readFile(String path) throws IOException
    {
        // Make a file object from the path name
        File file = new File(path);
        // Find the size
        int size = (int) file.length();
        // Create a buffer big enough to hold the file
        byte[] contents = new byte[size];
        // Create an input stream from the file object
        FileInputStream in = new FileInputStream(file);
        // Read it all
        in.read(contents);
        // Close the file
        in.close();
        return contents;
    }

    public static String getMimeType(String path)
    {
        String ending = path.substring(path.lastIndexOf('.'), path.length());
        if (MIMETYPES.get(ending) != null)
        {
            return MIMETYPES.get(ending);
        } else
        {
            return "";
        }
    }

    /**
     * Instantiates a Server Model from Json.
     *
     * @param filePath      the path to the file. If null, set to "sample/mockServerJsons/";
     * @param fileName      the name of the file, without file extension.
     * @return a Model initialized from the Json in the file.
     */
    public static ServerModel getModelFromFile(String filePath, String fileName)
    {
        if (filePath == null)
        {
            filePath = "sample/mockServerJsons/";
        }
        File file = new File(filePath + fileName + ".json");
        BufferedReader reader;
        ServerModel model = new ServerModel();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String json = BufferedReaderParser.parse(reader);
            model = new ServerModel(json);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

        return model;
    }

    /**
     * Creates a GameInfo from a file.
     *
     * @param filePath      the path to the file. If null, set to "sample/serverDefaults/";
     * @param fileName      the name of the file, without file extension.
     * @return a GameInfo initialized from the Json in the file.
     */
    public static GameInfo getGameInfoFromFile(String filePath, String fileName)
    {
        if (filePath == null)
        {
            filePath = "sample/mockServerJsons/";
        }

        File file = new File(filePath + fileName + ".json");
        BufferedReader reader;
        GameInfo game = new GameInfo();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String json = BufferedReaderParser.parse(reader);
            game = new GameInfo(json);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return game;
    }

    public static CatanMap getCatanMapFromFile(String filePath, String fileName)
    {
        if (filePath == null)
        {
            filePath = "sample/serverDefaults/";
        }

        File file = new File(filePath + fileName + ".json");
        BufferedReader reader;
        CatanMap catanMap = new CatanMap();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String json = BufferedReaderParser.parse(reader);
            JsonParser parser = new JsonParser();
            JsonObject map = (JsonObject) parser.parse(BufferedReaderParser.parse(reader));
            catanMap = new CatanMap(map.getAsJsonObject("map").toString());
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return catanMap;
    }
}