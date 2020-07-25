package seng202.team5;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * Does maths work?
     */
    public void testMaths() { assertEquals(2, 1+1); }

    public void testStuff() { assertFalse(1 + 1 == 11);}

    public void testTest() { assertEquals(5, 5); }

    public void testString() { assertEquals("word", "wo" + "rd"); }
}
