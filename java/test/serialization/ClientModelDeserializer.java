/**
 * 
 */
package serialization;

import java.lang.reflect.Type;

import com.google.gson.*;

import shared.model.ClientModel;

/**
 * @author cstaheli
 *
 */
public class ClientModelDeserializer implements JsonDeserializer<ClientModel>
{

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public ClientModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        // String
        // TODO Auto-generated method stub
        return null;
    }

}
