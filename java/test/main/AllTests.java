/**
 *
 */
package main;

import client.poller.PollTest;
import facades.MockProxyTester;
import facades.ProxyTester;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import server.facade.ServerFacadeTest;
import server.handler.UserHandlerTest;
import server.manager.GameManagerTest;
import server.util.FileUtilsTest;
import shared.communication.CatanCommandTests;
import shared.model.ClientModelTest;
import shared.model.TurnTrackerTest;
import shared.model.bank.*;
import shared.model.map.CatanMapTest;

/**
 * @author cstaheli
 */
@RunWith(Suite.class) @SuiteClasses({ TurnTrackerTest.class, ClientModelTest.class, PollTest.class, BankTest.class,
        DevCardsTest.class, DevCardTest.class, PlayerBankTest.class, ResourcesTest.class, ResourceTest.class,
        StructuresTest.class, BankStructureTest.class, MockProxyTester.class, ProxyTester.class, CatanMapTest.class,
        ServerFacadeTest.class, UserHandlerTest.class, FileUtilsTest.class, CatanCommandTests.class,
        GameManagerTest.class }) public class AllTests
{
    public static void main(String[] args)
    {

    }
}
