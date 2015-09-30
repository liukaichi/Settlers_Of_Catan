/**
 * 
 */
package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import facades.*;

/**
 * @author cstaheli
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ClientFacadeTester.class, ProxyTester.class })
public class AllTests
{
    public static void main(String[] args)
    {

    }
}
