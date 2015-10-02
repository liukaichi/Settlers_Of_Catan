package shared.model.bank.resource;

import shared.definitions.ResourceType;

/**
 * List of the resource objects
 * 
 * @author amandafisher
 *
 */
public class Resources
{
    private Resource brick, ore, sheep, wheat, wood;

    public Resources(boolean isGameBank){
        initialize(isGameBank);
    }

    private void initialize(boolean isGameBank){
        brick = new Resource(ResourceType.BRICK);
        ore = new Resource(ResourceType.ORE);
        sheep = new Resource(ResourceType.SHEEP);
        wheat = new Resource(ResourceType.WHEAT);
        wood = new Resource(ResourceType.WOOD);

        if(isGameBank){
            brick.addResource(19);
            ore.addResource(19);
            sheep.addResource(19);
            wheat.addResource(19);
            wood.addResource(19);
        }
    }

    public Resource getResource(ResourceType type){
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

        return null;
    }

    public int totalResources() {
        return brick.getAmount()
                + ore.getAmount()
                + sheep.getAmount()
                + wheat.getAmount()
                + wood.getAmount();
    }
}
