package shared.model.bank.structure;

import shared.definitions.StructureType;

/**
 * This class represents a list of BankStructure in the Catan game
 */
public class Structures
{
    private BankStructure road, settlement, city;

    public Structures(){
        initialize();
    }

    private void initialize(){
        road = new BankStructure(StructureType.ROAD);
        settlement = new BankStructure(StructureType.SETTLEMENT);
        city = new BankStructure(StructureType.CITY);
    }

    public BankStructure getStructure(StructureType type) {
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

    public int getAmountRemaining(StructureType type)
    {
        return getStructure(type).getAmountRemaining();
    }

}
