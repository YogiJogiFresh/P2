import student.TestCase;
/**
 * Test class for Database
 * @author Alan Kai
 * @version 2.0
 *
 */
public class DatabaseTest extends TestCase {
    private Database db;
    private Point pt;
    private Point pt2;
    private Point pt3;
    private Point pt4;
    private Point pt5;
    private Point pt6;
    /**
     * SetUp Method
     */
    public void setUp() {
        db = new Database();
        pt = new Point("a", 1, 2);
        pt2 = new Point("b", -1, 2);
        pt3 = new Point("c", 1, -2);
        pt4 = new Point("d", -1, -2);
        pt5 = new Point("1", 2, 2);
        pt6 = new Point("E", 4, 4);
        
    }
    /**
     * Validate test
     */
    public void testValidate() {
        assertFalse(db.validate(pt2));
        assertFalse(db.validate(pt3));
        assertFalse(db.validate(pt4));
        assertFalse(db.validate(pt5));
        assertTrue(db.validate(pt));
        assertTrue(db.validate(pt6));
    }
    /**
     * Insert Test 
     * reject if out of bounds
     */
    public void testInsert() {
        db.insert(pt);
        db.insert(pt2);
        assertFuzzyEquals("point inserted a 1 2\n point rejected b 1 2",
                systemOut().getHistory());
    }
    /**
     * Remove by name
     */
    public void testRemoveByName1() {
        db.insert(pt);
        db.removeByName("a");
        assertFuzzyEquals("point inserted a 1 2\n point removed a 1 2"
                , systemOut().getHistory());
    }
    /**
     * Remove by name
     * Not removed because not found
     */
    public void testRemoveByName2() {
        db.removeByName("a");
        assertFuzzyEquals("point not removed a", systemOut().getHistory());
    }
    /**
     * Remove by name
     * Reject because not a letter
     */
    public void testRemovebyName3() {
        db.removeByName("1");
        assertFuzzyEquals("point rejected 1", systemOut().getHistory());
    }
    /**
     * Test remove by coordinate
     */
    public void testRemoveByCoord1() {
        Point rPt = new Point(1, 2, 3, 4);
        db.insert(pt);
        db.removeByCoord(rPt);
        assertFuzzyEquals("point inserted a 1 2\n point removed a 1 2"
                , systemOut().getHistory());
    }
    /**
     * Test remove by coordinate
     * Point rejeted, because out of bounds
     */
    public void testRemoveByCoord2() {
        Point rPt = new Point("a", -2, 1);
        db.removeByCoord(rPt);
        assertFuzzyEquals("point rejected 2 1", systemOut().getHistory());
    }
    /**
     *Test Remove by Coordinate
     */
    public void testRemoveByCoord3() {
        Point rPt = new Point("a", 22, 32);
        db.removeByCoord(rPt);
        assertFuzzyEquals("point not found 22 32", systemOut().getHistory());
    }
    /**
     * Test Region Search
     * Reject regions
     */
    public void testRegionSearch() {
        int[] region = {0, 0, 10, -10};
        int[] region2 = {0, 0, -20, 20};
        int[] region3 = {0, 0, -30, -30};
        db.regionsearch(region);
        db.regionsearch(region2);
        db.regionsearch(region3);
        assertFuzzyEquals("rectangle rejected 0 0 10 10\n" +
            "rectangle rejected 0 0 20 20\n" +
            "rectangle rejected 0 0 30 30", systemOut().getHistory());
    }
    /**
     * Test intersections
     * No longer using intersections
     */
    public void testIntersections() {
        db.intersections();
        assertFuzzyEquals("Intersections pairs:", systemOut().getHistory());
    }
}
