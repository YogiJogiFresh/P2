import student.TestCase;

/**
 * Test class for KVPair class
 * 
 * @author Alan Kai
 * @author Chris R
 * @version 1.0
 */
public class KVPairTest extends TestCase {
    @SuppressWarnings("rawtypes")
    private KVPair kvp;
    private Point rect;

    /**
     * Set up method
     */
    public void setUp() {
        rect = new Point(1, 2, 3, 4);
        kvp = new KVPair<String, Point>("a", rect);
    }

    /**
     * Test method for key()
     */
    public void testKey() {
        assertEquals("a", kvp.key());
    }

    /**
     * Test method value()
     */
    public void testValue() {
        assertEquals("1, 2, 3, 4", ((Point) kvp.value()).toRString());
    }
    /**
     * Test method for compareTo KVPair
     * 0 is true
     * 1 is false
     */
    @SuppressWarnings("unchecked")
    public void testCompareTo1() { 
        KVPair<String, Point> it = new KVPair<String, Point>("a", rect);
        assertEquals(0, kvp.compareTo(it));
    }
    /**
     * Test method for compareTo key 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testCompareTo2() {
        KVPair it = new KVPair<String, Point>("a", rect);
        assertEquals(0, it.compareTo(kvp.key()));
    }
}
