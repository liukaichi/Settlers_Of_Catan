package shared.definitions.exceptions;

/**
 * Created by Adrian on 11/12/2015.
 */
public class ExistingRegistrationException extends CatanException {
    public ExistingRegistrationException(String string){
        super(string);
    }

    public ExistingRegistrationException(){}
}
