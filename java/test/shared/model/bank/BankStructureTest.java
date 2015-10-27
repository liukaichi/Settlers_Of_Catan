package shared.model.bank;

import static org.junit.Assert.*;

import org.junit.Test;
import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;
import shared.model.bank.structure.BankStructure;

/**
 * Created by Adrian on 9/28/2015.
 */
public class BankStructureTest
{

    private BankStructure setCity() throws Exception{
        return new BankStructure(StructureType.CITY);
    }

    private BankStructure setSettlement() throws Exception{
        return new BankStructure(StructureType.SETTLEMENT);
    }

    private BankStructure setRoad() throws Exception{
        return new BankStructure(StructureType.ROAD);
    }

    @Test
    public void testGetType() throws Exception {
        BankStructure test = setCity();
        assertEquals(test.getType(), StructureType.CITY);
    }

    @Test
    public void testGetAmount() throws Exception {
        BankStructure test = setCity();
        assertEquals(test.getAmount(BankStructure.AmountType.BUILT), 0);
        assertEquals(test.getAmount(BankStructure.AmountType.MAX), 4);

        test = setSettlement();
        assertEquals(test.getAmount(BankStructure.AmountType.BUILT), 0);
        assertEquals(test.getAmount(BankStructure.AmountType.MAX), 5);

        test = setRoad();
        assertEquals(test.getAmount(BankStructure.AmountType.BUILT), 0);
        assertEquals(test.getAmount(BankStructure.AmountType.MAX), 15);
    }

    @Test
    public void testAddAmount() throws Exception{
        BankStructure test = setCity();
        test.addAmountBuilt(4);
        assertEquals(test.getAmount(BankStructure.AmountType.BUILT), 4);
    }

    @Test(expected = Exception.class)
    public void testAddAmountException() throws Exception {
        BankStructure test = setCity();
        test.addAmountBuilt(9);
    }

    @Test
    public void testSubAmount() throws Exception {
        BankStructure test = setSettlement();
        test.addAmountBuilt(3);
        test.subAmountBuilt(1);
        assertEquals(test.getAmount(BankStructure.AmountType.BUILT), 2);
    }

    @Test(expected = CatanException.class)
    public void testSubAmountException() throws Exception {
        BankStructure test = setSettlement();
        test.subAmountBuilt(1);
    }
}