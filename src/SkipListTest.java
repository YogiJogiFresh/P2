import student.TestCase;
/**
 * Test class for SkipList
 * @author Alan Kai
 * @version 2.0
 */
public class SkipListTest extends TestCase {
    private KVPair<String, Point> kvp;
    private KVPair<String, Point> kvp2;
    private KVPair<String, Point> kvp3;
    private SkipList<String, Point> skip;

    private Point rect;
    private Point rect2;
    private Point rect3;

    /**
     * SetUp method
     */
    public void setUp() {
        rect = new Point(1, 2);
        rect2 = new Point(3, 4);
        rect3 = new Point(5, 6);
        skip = new SkipList<String, Point>();
        kvp = new KVPair<String, Point>("a", rect);
        kvp2 = new KVPair<String, Point>("b", rect2);
        kvp3 = new KVPair<String, Point>("c", rect3);
    }
    /**
     * Test method for insert
     */
    public void testInsert() {
        assertTrue(skip.insert(kvp));
        assertEquals(1, skip.size());
    }
    /**
     * Test method for remove
     */
    public void testRemove() {
        assertTrue(skip.insert(kvp));
        assertEquals(1, skip.size());
        assertEquals("a, 1, 2", skip.remove("a"));
        assertEquals(0, skip.size());
    }
    /**
     * Test method for search
     */
    public void testSeaarch1() {
        skip.insert(kvp);
        assertNotNull(skip.search("a"));
        skip.insert(kvp3);
        skip.insert(kvp2);
        assertNotNull(skip.search("c"));
    }
    /**
     * Test method for non existing point
     */
    public void testSearch2() {
        skip.insert(kvp);
        assertNull(skip.search("b"));
    }
    /**
     * Test regionSearch()
     */
    public void testRegionSearch() {
        Database db = new Database();
        Point r = new Point("alpha", 1, 2, 3, 4);
        db.insert(r);
        int[] x = {-5, 0, 20, 20};
        db.regionsearch(x);
        
        String a = "Point inserted: (alpha, 1, 2)\n";
        String b = "Points intersecting region (-5, 0, 20, 20):"
            + "\nPoint found: (alpha, 1, 2)" + "\n1 quadtree nodes visited\n";
        String output = systemOut().getHistory();
        assertEquals(a + b, output);
    }
}