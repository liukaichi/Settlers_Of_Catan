/**
 * 
 */
package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import facades.*;
import shared.model.bank.*;
import shared.model.map.CatanMapTest;
import shared.model.message.*;

/**
 * @author cstaheli
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ClientFacadeTester.class, ProxyTester.class, CatanMapTest.class, ChatTest.class, MessageLineTest.class,
        BankTest.class, DevCardsTest.class, DevCardTest.class, PlayerBankTest.class, ResourcesTest.class,
        ResourceTest.class, StructuresTest.class, StructureTest.class })
public class AllTests
{
    public static void main(String[] args)
    {

    }
}
