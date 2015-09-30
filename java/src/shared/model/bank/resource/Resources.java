package shared.model.bank.resource;

import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;
import shared.model.bank.structure.Structure;
import sun.security.tools.keytool.Resources_it;

/**
 * List of the resource objects
 * 
 * @author amandafisher
 *
 */
public class Resources
{
    private Resource brick, ore, sheep, wheat, wood;

    public Resources(){
        initialize();
    }

    private void initialize(){
        brick = new Resource(ResourceType.BRICK);
        ore = new Resource(ResourceType.ORE);
        sheep = new Resource(ResourceType.SHEEP);
        wheat = new Resource(ResourceType.WHEAT);
        wood = new Resource(ResourceType.WOOD);
    }

    public Resource getResource(ResourceType type) throws CatanException{
        switch (type){
            case BRICK:
                return brick;
            case ORE:
                return ore;
            case SHEEP:
                return sheep;
            case WHEAT:
                return wheat;
            case WOOD:
                return wood;
        }

        throw new CatanException();
    }
}
