package shared.model.bank.structure;

import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;

/**
 * Created by Adrian on 9/28/2015.
 */
public class StructureTest {

    private Structure setCity() throws Exception{
        return new Structure(StructureType.CITY);
    }

    private Structure setSettlement() throws Exception{
        return new Structure(StructureType.SETTLEMENT);
    }

    private Structure setRoad() throws Exception{
        return new Structure(StructureType.ROAD);
    }

    @Test
    public void testGetType() throws Exception {
        Structure test = setCity();
        assertEquals(test.getType(), StructureType.CITY);
    }

    @Test
    public void testGetAmount() throws Exception {
        Structure test = setCity();
        assertEquals(test.getAmount(Structure.AmountType.BUILT), 0);
        assertEquals(test.getAmount(Structure.AmountType.MAX), 4);

        test = setSettlement();
        assertEquals(test.getAmount(Structure.AmountType.BUILT), 0);
        assertEquals(test.getAmount(Structure.AmountType.MAX), 5);

        test = setRoad();
        assertEquals(test.getAmount(Structure.AmountType.BUILT), 0);
        assertEquals(test.getAmount(Structure.AmountType.MAX), 15);
    }

    @Test
    public void testAddAmount() throws Exception{
        Structure test = setCity();
        test.addAmount(4);
        assertEquals(test.getAmount(Structure.AmountType.BUILT), 4);
    }

    @Test(expected = Exception.class)
    public void testAddAmountException() throws Exception {
        Structure test = setCity();
        test.addAmount(9);
    }

    @Test
    public void testSubAmount() throws Exception {
        Structure test = setSettlement();
        test.addAmount(3);
        test.subAmount(1);
        assertEquals(test.getAmount(Structure.AmountType.BUILT), 2);
    }

    @Test(expected = CatanException.class)
    public void testSubAmountException() throws Exception {
        Structure test = setSettlement();
        test.subAmount(1);
    }
}