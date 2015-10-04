package shared.model.bank.resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public Resources(String json){
        this(false);
        JsonParser parser = new JsonParser();
        JsonObject jobj = (JsonObject) parser.parse(json);

        this.brick.setAmount(jobj.getAsJsonPrimitive("brick").getAsInt());
        this.wheat.setAmount(jobj.getAsJsonPrimitive("wheat").getAsInt());
        this.wood.setAmount(jobj.get("wood").getAsInt());
        this.sheep.setAmount(jobj.get("sheep").getAsInt());
        this.ore.setAmount(jobj.get("ore").getAsInt());
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

    @Override
    public String toString() {
        JsonObject resources = toJsonObject();
        return resources.toString();
    }

    public JsonObject toJsonObject(){
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

    @Override public int hashCode()
    {
        return 0;
    }
    //
//    public static class ResourcesSerializer implements JsonSerializer<Resources> {
//
//        @Override
//        public JsonElement serialize(Resources src, Type type, JsonSerializationContext jsonSerializationContext) {
//            JsonObject resources = new JsonObject();
//            {
//                resources.addProperty("brick", src.getResource(ResourceType.BRICK).getAmount());
//                resources.addProperty("ore", src.getResource(ResourceType.ORE).getAmount());
//                resources.addProperty("sheep", src.getResource(ResourceType.SHEEP).getAmount());
//                resources.addProperty("wheat", src.getResource(ResourceType.WHEAT).getAmount());
//                resources.addProperty("wood", src.getResource(ResourceType.WOOD).getAmount());
//            }
//            return resources;
//        }
//    }
//
//    public static class ResourcesDeserializer implements JsonDeserializer<Resources> {
//
//        @Override
//        public Resources deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//            System.out.println(json);
//            JsonObject jobj = (JsonObject) json;
//            Resources list = new Resources(false);
//            list.wheat.setAmount(jobj.get("wheat").getAsInt());
//            list.brick.setAmount(jobj.get("brick").getAsInt());
//            list.ore.setAmount(jobj.get("ore").getAsInt());
//            list.sheep.setAmount(jobj.get("sheep").getAsInt());
//            list.wood.setAmount(jobj.get("wood").getAsInt());
//
//            return list;
//        }
//    }
}
