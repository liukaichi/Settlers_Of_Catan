package shared.model.bank.resource;

import com.google.gson.*;
import shared.definitions.ResourceType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * List of the resource objects
 *
 * @author amandafisher
 */
public class Resources implements JsonSerializer<Resources>
{
    private Resource brick, wood, sheep, wheat, ore;

    private static final Logger LOGGER = Logger.getLogger(Resource.class.getName());

    public Resources(boolean isGameBank)
    {
        initialize(isGameBank);
    }

    public Resources(String json)
    {
        this(false);
        JsonParser parser = new JsonParser();
        JsonObject jobj = (JsonObject) parser.parse(json);

        this.brick.setAmount(jobj.get("brick").getAsInt());
        this.wood.setAmount(jobj.get("wood").getAsInt());
        this.sheep.setAmount(jobj.get("sheep").getAsInt());
        this.wheat.setAmount(jobj.get("wheat").getAsInt());
        this.ore.setAmount(jobj.get("ore").getAsInt());
    }

    public Resources(int brick, int wood, int sheep, int wheat, int ore)
    {
        this(false);
        this.brick.setAmount(brick);
        this.wood.setAmount(wood);
        this.sheep.setAmount(sheep);
        this.wheat.setAmount(wheat);
        this.ore.setAmount(ore);
    }

    public Resources()
    {
        this(0, 0, 0, 0, 0);
    }

    private void initialize(boolean isGameBank)
    {
        brick = new Resource(ResourceType.BRICK);
        ore = new Resource(ResourceType.ORE);
        sheep = new Resource(ResourceType.SHEEP);
        wheat = new Resource(ResourceType.WHEAT);
        wood = new Resource(ResourceType.WOOD);

        if (isGameBank)
        {
            brick.addResource(19);
            ore.addResource(19);
            sheep.addResource(19);
            wheat.addResource(19);
            wood.addResource(19);
        }
    }

    public Resource getResource(ResourceType type)
    {
        switch (type)
        {
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

    public List<Resource> getAllResources()
    {
        ArrayList<Resource> resourceList = new ArrayList<>();
        resourceList.add(brick);
        resourceList.add(wood);
        resourceList.add(sheep);
        resourceList.add(wheat);
        resourceList.add(ore);
        return resourceList;
    }

    public void setAmount(ResourceType type, int amount)
    {
        getResource(type).setAmount(amount);
    }

    public int getAmount(ResourceType type)
    {
        return getResource(type).getAmount();
    }

    public int totalResources()
    {
        return brick.getAmount() + ore.getAmount() + sheep.getAmount() + wheat.getAmount() + wood.getAmount();
    }

    @Override public String toString()
    {
        JsonObject resources = toJsonObject();
        LOGGER.fine("JSON-izing resources\n" + resources.toString());
        return resources.toString();
    }

    public JsonObject toJsonObject()
    {
        JsonObject resources = new JsonObject();
        {
            resources.addProperty("brick", brick.getAmount());
            resources.addProperty("ore", ore.getAmount());
            resources.addProperty("sheep", sheep.getAmount());
            resources.addProperty("wheat", wheat.getAmount());
            resources.addProperty("wood", wood.getAmount());
        }

        return resources;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override public JsonElement serialize(Resources src, Type srcType, JsonSerializationContext context)
    {
        JsonObject jsonOffer = new JsonObject();
        {
            jsonOffer.addProperty("brick", src.brick.getAmount());
            jsonOffer.addProperty("ore", src.ore.getAmount());
            jsonOffer.addProperty("sheep", src.sheep.getAmount());
            jsonOffer.addProperty("wheat", src.wheat.getAmount());
            jsonOffer.addProperty("wood", src.wood.getAmount());
        }
        return jsonOffer;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Resources resources = (Resources) o;

        if (brick != null ? !brick.equals(resources.brick) : resources.brick != null)
            return false;
        if (ore != null ? !ore.equals(resources.ore) : resources.ore != null)
            return false;
        if (sheep != null ? !sheep.equals(resources.sheep) : resources.sheep != null)
            return false;
        if (wheat != null ? !wheat.equals(resources.wheat) : resources.wheat != null)
            return false;
        return !(wood != null ? !wood.equals(resources.wood) : resources.wood != null);

    }

    /**
     * Increases the specified resource in this collection by 1. This behaves identically to increase(type, 1).
     *
     * @param type the resource type to increase.
     */
    public void increase(ResourceType type)
    {
        this.increase(type, 1);
    }

    public void increase(ResourceType type, int amount)
    {
        getResource(type).addResource(amount);
    }

    /**
     * Decreases the specified resource in this collection by 1
     *
     * @param type the resource type to decrease.
     */
    public void decrease(ResourceType type)
    {
        this.decrease(type, 1);
    }

    public void decrease(ResourceType type, int amount)
    {
        getResource(type).subResource(amount);
    }
}
