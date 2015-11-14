package client.base;

/**
 * Base class for controllers
 */
public abstract class Controller implements IController
{

    private IView view;

    protected Controller(IView view)
    {
        setView(view);
    }

    @Override public IView getView()
    {
        return this.view;
    }

    private void setView(IView view)
    {
        this.view = view;
    }

}
