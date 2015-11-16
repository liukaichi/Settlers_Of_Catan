package shared.model.bank.structure;

import shared.definitions.StructureType;

/**
 * This class represents a list of BankStructure in the Catan game
 */
public class Structures
{
    private BankStructure road, settlement, city;

    public Structures()
    {
        initialize();
    }

    private void initialize()
    {
        road = new BankStructure(StructureType.ROAD);
        settlement = new BankStructure(StructureType.SETTLEMENT);
        city = new BankStructure(StructureType.CITY);
    }

    public BankStructure getStructure(StructureType type)
    {
        switch (type)
        {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Structures that = (Structures) o;

        if (road != null ? !road.equals(that.road) : that.road != null) return false;
        if (settlement != null ? !settlement.equals(that.settlement) : that.settlement != null) return false;
        return !(city != null ? !city.equals(that.city) : that.city != null);

    }

}
