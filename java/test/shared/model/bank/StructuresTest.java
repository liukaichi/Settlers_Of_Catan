package shared.model.bank;

import org.junit.Before;
import org.junit.Test;
import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;
import shared.model.bank.structure.Structure;
import shared.model.bank.structure.Structures;

import static org.junit.Assert.*;

/**
 * Created by Adrian on 9/29/2015.
 */
public class StructuresTest {

    private Structures list;

    // Used a constructor so that an Exception can be thrown
    @Before
    public void setUp() throws Exception {
        list = new Structures();
    }

    @Test
    public void testGetStructure() throws CatanException {
        Structure test = list.getStructure(StructureType.CITY);

        assertEquals(test.getType(), StructureType.CITY);
        assertEquals(test.getAmount(Structure.AmountType.MAX), 4);
        assertEquals(test.getAmount(Structure.AmountType.BUILT), 0);

        test.addAmount(2);
        assertEquals(test.getAmount(Structure.AmountType.BUILT), 2);
    }
}