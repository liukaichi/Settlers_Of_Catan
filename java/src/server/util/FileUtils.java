package server.util;

import client.data.GameInfo;
import client.utils.BufferedReaderParser;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import server.ServerModel;

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
     * @param fileExtension the file extension to look for. If null, ".json" is used.
     * @return a Model initialized from the Json in the file.
     */
    public static ServerModel getModelFromFile(@Nullable String filePath, @NotNull String fileName,
            @Nullable String fileExtension)
    {
        if (filePath == null)
        {
            filePath = "sample/mockServerJsons/";
        }
        if (fileExtension == null)
        {
            fileExtension = ".json";
        }
        File file = new File(filePath + fileName + fileExtension);
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
        }

        return model;
    }

    /**
     * Creates a GameInfo from a file.
     *
     * @param filePath      the path to the file. If null, set to "sample/serverDefaults/";
     * @param fileName      the name of the file, without file extension.
     * @param fileExtension the file ending type to look for. If null, ".json" is used.
     * @return a GameInfo initialized from the Json in the file.
     */
    public static GameInfo getGameInfoFromFile(@Nullable String filePath, @NotNull String fileName,
            @Nullable String fileExtension)
    {
        if (filePath == null)
        {
            filePath = "sample/mockServerJsons/";
        }
        if (fileExtension == null)
        {
            fileExtension = ".json";
        }
        File file = new File(filePath + fileName + fileExtension);
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
}