package shared.model.bank.resource;

import com.google.gson.JsonElement;
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
    private ResourceType brick, ore, sheep, wheat, wood;

    public Resources(boolean isGameBank){
        initialize(isGameBank);
    }

    public Resources(String json){
        this(false);
        JsonElement jele = new JsonParser().parse(json);
        JsonObject jobj = jele.getAsJsonObject();

        this.brick.setAmount(jobj.get("brick").getAsInt());
        this.wheat.setAmount(jobj.get("wheat").getAsInt());
        this.wood.setAmount(jobj.get("wood").getAsInt());
        this.sheep.setAmount(jobj.get("sheep").getAsInt());
        this.ore.setAmount(jobj.get("ore").getAsInt());
    }

    private void initialize(boolean isGameBank){
        brick = new ResourceType(ResourceType.BRICK);
        ore = new ResourceType(ResourceType.ORE);
        sheep = new ResourceType(ResourceType.SHEEP);
        wheat = new ResourceType(ResourceType.WHEAT);
        wood = new ResourceType(ResourceType.WOOD);

        if(isGameBank){
            brick.addResource(19);
            ore.addResource(19);
            sheep.addResource(19);
            wheat.addResource(19);
            wood.addResource(19);
        }
    }

    public ResourceType getResource(ResourceType type){
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
        JsonObject resources = new JsonObject();
        {
            resources.addProperty("brick", brick.getAmount());
            resources.addProperty("ore", ore.getAmount());
            resources.addProperty("sheep", sheep.getAmount());
            resources.addProperty("wheat", wheat.getAmount());
            resources.addProperty("wood", wood.getAmount());
        }
        return resources.toString();
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
