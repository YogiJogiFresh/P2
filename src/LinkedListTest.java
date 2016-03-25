import student.TestCase;
/**
 * test class for linked list class
 * @author Alan Kai
 * @version 1.0
 *
 */
public class LinkedListTest extends TestCase {
    private LinkedList linky;
    private String point;
    /**
     * Test method for setUp
     */
    public void setUp() {
        linky = new LinkedList();
    }
    /**
     * Test Add method
     */
    public void testAdd() {
        point = "(2, 3)";
        linky.add(point);
        assertEquals(1, linky.size());
        assertEquals("(2, 3)", linky.get(0));
    }
    /**
     * Test get Method
     */
    public void testGet() {
        point = "(2, 3)";
        String point2 = "(1, 1)";
        linky.add(point2);
        linky.add(point);
        assertEquals(2, linky.size());
        assertEquals("(2, 3)", linky.get(1));
        assertEquals("(1, 1)", linky.get(0));
    }
    /**
     * Can't get an index less than 0
     */
    public void testGet1() {
        assertNull(linky.get(-2));
    }
    /**
     * Can't get an index thats greater than linkedlist
     */
    public void testGet2() {
        //assertNull(linky.get(2));
    }
    /**
     * Test Remove method
     */
    public void testRemove() {
        point = "(2, 3)";
        String point2 = "(1, 1)";
        linky.add(point);
        linky.add(point2);
        assertEquals(2, linky.size());
        assertTrue(linky.remove(0));
        assertEquals(1, linky.size());
        assertEquals("(1, 1)", linky.get(0));
    }
    /**
     * Test Remove method when it doesnt remove
     */
    public void testRemove1() {
        point = "(2, 3)";
        String point2 = "(1, 1)";
        linky.add(point);
        linky.add(point2);
        assertEquals(2, linky.size());
        assertFalse(linky.remove(-2));
        assertEquals(2, linky.size());
        assertEquals("(2, 3)", linky.get(0));
    }
    /**
     * Tests duplicates()
     */
    public void testDuplicates() {
        point = "(2, 3)";
        String point2 = "(2, 3)";
        String point3 = "(1, 1)"; //Unique Point
        String point4 = "(3, 4)";
        String point5 = "(3, 4)";
        linky.add(point);
        linky.add(point2);
        linky.add(point3);
        linky.add(point4);
        linky.add(point5);
        assertEquals(5, linky.size());
        LinkedList dup = linky.duplicates();
        assertEquals(2, dup.size());
        assertEquals("(2, 3)", dup.get(0));
        assertEquals("(3, 4)", dup.get(1));
    }
    /**
     * Test multipleUnique()
     * false because all points are the same
     */
    public void testMultipleUnique1() {
        point = "(2, 3)";   
        String point2 = "(2, 3)";
        String point3 = "(2, 3)";
        linky.add(point);
        linky.add(point2);
        linky.add(point3);
        assertEquals(3, linky.size());
        assertFalse(linky.multipleUnique());
    }
    /**
     * True because there is at least one pt different
     */
    public void testMultipleUnique2() {
        point = "(2, 3)";   
        String point2 = "(1, 3)";
        String point3 = "(2, 3)";
        linky.add(point);
        linky.add(point2);
        linky.add(point3);
        assertEquals(3, linky.size());
        assertTrue(linky.multipleUnique());
    }
    /**
     * True. Point3 is different
     */
    public void testMultipleUnique3() {
        point = "(1, 1)";
        String point2 = "(1, 1)";
        String point3 = "(2, 3)";
        String point4 = "(1, 1)";
        linky.add(point);
        linky.add(point2);
        linky.add(point3);
        linky.add(point4);
        assertEquals(4, linky.size());
        assertTrue(linky.multipleUnique());
    }
    /**
     * Test method for find
     */
    public void testFind() {
        Point pt1 = new Point("a", 1, 1);
        Point pt2 = new Point("b", 2, 2);
        Point pt3 = new Point("c", 3, 3);
        Point pt4 = new Point("d", 4, 4);
        linky.add(pt1);
        linky.add(pt2);
        linky.add(pt3);
        linky.add(pt4);
        assertEquals(0, linky.findByPt(1, 1, 0));
        assertEquals(1, linky.findByPt(2, 2, 0));
        assertEquals(2, linky.findByPt(3, 3, 0));
        assertEquals(-1, linky.findByPt(100, 100, 0));
    }
}
