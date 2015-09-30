package shared.model.bank.structure;

import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;

/**
 * This class represents a list of Structure in the Catan game
 */
public class Structures
{
    private Structure road, settlement, city;

    public Structures() throws CatanException{
        initialize();
    }

    private void initialize() throws CatanException{
        road = new Structure(StructureType.ROAD);
        settlement = new Structure(StructureType.SETTLEMENT);
        city = new Structure(StructureType.CITY);
    }

    public Structure getStructure(StructureType type) throws CatanException {
        switch (type){
            case ROAD:
                return road;
            case SETTLEMENT:
                return settlement;
            case CITY:
                return city;
        }

        throw new CatanException();
    }
}
