package server.plugin;

import server.facade.AbstractServerFacade;

import java.io.FileNotFoundException;
import java.io.InvalidClassException;
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

        URL url = new URL("C:\\Users\\liukaichi\\Programming\\RealSettlers\\java\\lib\\plugins\\SQLiteEngine.jar");

        URLClassLoader child = new URLClassLoader(new URL[]{url},
                this.getClass().getClassLoader());
        Class classToLoad = Class.forName("com.SQLiteFactory", true, child);
        Method method = classToLoad.getDeclaredMethod("myMethod");
        Object instance = classToLoad.newInstance();
        Object result = method.invoke (instance);

        return null;
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
