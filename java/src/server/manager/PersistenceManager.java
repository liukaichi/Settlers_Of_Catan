package server.manager;

import server.plugin.IPersistenceEngine;

/**
 * Created by liukaichi on 12/4/2015.
 */
public class PersistenceManager
{
    private static PersistenceManager _instance;
    private IPersistenceEngine persistenceEngine;

    private PersistenceManager()
    {
    }

    /**
     * Singleton pattern.
     *
     * @return the singleton instance of UserManager.
     */
    public static PersistenceManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new PersistenceManager();
        }
        return _instance;
    }

    public void setPersistenceEngine(IPersistenceEngine persistenceEngine)
    {
        this.persistenceEngine = persistenceEngine;
    }

}
