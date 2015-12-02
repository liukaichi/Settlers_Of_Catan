package server.plugin;

import server.facade.AbstractServerFacade;
import shared.communication.CatanCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

/**
 * Creates a factory from a plugin string. To register a new plugin with the Plugin manager, you must either use the method provided or modify the
 * configuration file.
 */
public class PluginManager
{
    /**
     * Creates a new Factory from the specified type.
     *
     * @param factoryType the type of plugin that is specified in the configuration file.
     * @return the factory from the plugin associated with this factoryType.
     * @throws FileNotFoundException If there is no such plugin registered.
     */
    public IPersistenceFactory createFactory(String factoryType)
            throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InstantiationException, InvocationTargetException, MalformedURLException
    {
        //find the factory type and get the class info
        File config = new File("java\\lib\\plugins\\config.txt");

        //make the class from the jar
        URL url =  new File("java\\lib\\plugins\\SQLiteEngine.jar")
                .toURI().toURL();
        URLClassLoader child = new URLClassLoader(new URL[]{url},
                this.getClass().getClassLoader());
        Class classToLoad = Class.forName("SQLiteFactory", true, child);

        //***weird method stuff?
        //Method method = classToLoad.getDeclaredMethod("myMethod");
        //Object instance = classToLoad.newInstance();
        //Object result = method.invoke (instance);

        Constructor c = classToLoad.getConstructor();
        return (IPersistenceFactory) c.newInstance();
    }

    /**
     * Registers a new plugin with the configuration file.
     *
     * @param factoryType the factory type to be associated with the plugin.
     * @param jar         the jar that the plugin is located in.
     * @throws InvalidClassException if the Jar is invalid, or does not contain a factory as required.
     */
    public static void registerPlugin(String factoryType, JarFile jar) throws InvalidClassException
    {
        throw new InvalidClassException(
                "The Jar file provided is invalid. Please ensure that the jar contains all of the necessary pieces for a plugin, including a factory. ");
    }
}
