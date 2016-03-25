import student.TestCase;
/**
 * Point Test
 * @author Alan Kai
 * @version 1.0
 *
 */
public class PointTest extends TestCase {
    private Point pt;
    /**
     * SetUp 
     */
    public void setUp() {
        pt = new Point("a", 1, 2, 3 , 4);   
    }
    /**
     * Test getX
     */
    public void testGetX() {
        assertEquals(1, pt.getX());
    }
    /**
     * test getY
     */
    public void testGetY() {
        assertEquals(2, pt.getY());
    }
    /**
     * Test getW
     */
    public void testGetW() {
        assertEquals(3, pt.getW());
    }
    /**
     * Test Get H
     */
    public void testGetH() {
        assertEquals(4, pt.getH());
    }
    /**
     * test Get name
     */
    public void testGetName() {
        assertEquals("a", pt.getName());
    }
    /**
     * test toString()
     */
    public void testToString() {
        assertEquals("1, 2", pt.toString());
    }
    /**
     * Tests equals
     */
    public void testEquals() {
        Point pt2 = new Point("b", 1, 2, 3, 4);
        Point pt3 = new Point("c", 2, 3, 4, 5);
        assertTrue(pt.equals(pt2));
        assertFalse(pt.equals(pt3));
    }
}