package shared.model.bank.structure;

import shared.definitions.StructureType;

/**
 * This class represents a list of Structure in the Catan game
 */
public class Structures
{
    private Structure road, settlement, city;

    public Structures(){
        initialize();
    }

    private void initialize(){
        road = new Structure(StructureType.ROAD);
        settlement = new Structure(StructureType.SETTLEMENT);
        city = new Structure(StructureType.CITY);
    }

    public Structure getStructure(StructureType type) {
        switch (type){
            case ROAD:
                return road;
            case SETTLEMENT:
                return settlement;
            case CITY:
                return city;
            default:
                return null;
        }
    }
}
