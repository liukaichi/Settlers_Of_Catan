package serialization;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.model.ClientModel;

/**
 * @author dtaylor
 *
 */
public class ClientModelSerializer implements JsonSerializer<ClientModel>
{

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
     * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
     */
    @Override
    public JsonElement serialize(ClientModel src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject object = new JsonObject();

        // TODO Auto-generated method stub
        return null;
    }

}
