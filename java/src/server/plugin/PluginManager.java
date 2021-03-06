package server.plugin;

import shared.communication.Credentials;
import shared.definitions.exceptions.InvalidCredentialsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/**
 * Creates a factory from a plugin string. To register a new plugin with the Plugin manager, you must either use the method provided or modify the
 * configuration file.
 */
public class PluginManager
{
    private Map<String, FactoryConfig> config;

    public PluginManager()
    {
        parseConfig();
    }

    /**
     * Registers a new plugin with the configuration file.
     *
     * @param factoryType the factory type to be associated with the plugin.
     * @param jar         the jar that the plugin is located in.
     * @throws InvalidClassException if the Jar is invalid, or does not contain a factory as required.
     */
    public static void registerPlugin(String factoryType, JarFile jar) throws InvalidClassException {
        throw new InvalidClassException(
                "The Jar file provided is invalid. Please ensure that the jar contains all of the necessary pieces for a plugin, including a factory. ");
    }

    public static void main(String[] args) {
        try {
            PluginManager manager = new PluginManager();
            IPersistenceFactory factory = manager.createFactory("sqlite");
            try {
                int response = factory.createPersistenceEngine(10).registerUser(new Credentials("David", "david"));
                Logger.getAnonymousLogger().info(String.valueOf(response));
            } catch (InvalidCredentialsException e) {
                e.printStackTrace();
            }
        } catch (FactoryTypeException e) {

        }
    }

    private void parseConfig()
    {
        try
        {
            config = new HashMap<>();
            //find the factory type and get the class info
            File configFile = new File("plugins" + File.separator + "config.txt");
            if (configFile.exists())
            {
                List<String> lines = Files.readAllLines(configFile.toPath());
                for (String line : lines)
                {
                    String[] factoryConfig = line.trim().split(" ");
                    String type = factoryConfig[0];
                    String path = Paths.get("plugins", factoryConfig[1]).toString();
                    String className = factoryConfig[2];
                    config.put(type, new FactoryConfig(type, path, className));
                }
            } else
            {
                throw new Exception("File does not exist");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new Factory from the specified type.
     *
     * @param factoryType the type of plugin that is specified in the configuration file.
     * @return the factory from the plugin associated with this factoryType.
     * @throws FileNotFoundException If there is no such plugin registered.
     */
    public IPersistenceFactory createFactory(String factoryType) throws FactoryTypeException
    {
        FactoryConfig factoryConfig = config.get(factoryType);
        //make the class from the jar
        if (factoryConfig != null)
            return createFactoryFromJar(factoryConfig.path, factoryConfig.className);
        else
            throw new FactoryTypeException("Can't find " + factoryType);
    }

    public IPersistenceFactory createFactoryFromJar(String jarPath, String className) throws FactoryTypeException
    {
        URLClassLoader loader = null;
        try
        {
            loader = new URLClassLoader(new URL[] { new File(jarPath).toURI().toURL() }, this.getClass().getClassLoader());
            Constructor classConstructor = Class.forName(className, true, loader).getConstructor();
            IPersistenceFactory instance = (IPersistenceFactory) classConstructor.newInstance();
            return instance;
        } catch (Exception e)
        {
            throw new FactoryTypeException("Failed to create Factory", e);
        }
    }

    private class FactoryConfig
    {
        public String type;
        public String path;
        public String className;

        public FactoryConfig(String type, String path, String className)
        {
            this.type = type;
            this.path = path;
            this.className = className;
        }
    }

    private class FactoryTypeException extends Exception
    {
        private static final long serialVersionUID = 8433573830897107523L;

        public FactoryTypeException(String message)
        {
            super(message);
        }

        public FactoryTypeException(String message, Exception e)
        {
            super(message, e);
        }
    }
}
