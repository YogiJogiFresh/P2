import student.TestCase;

/**
 * Test class for the Quad Tree
 * 
 * @author Alan Kai
 * @author Chris Rocconi
 * @version 1.0
 *
 */
public class QuadTreeTest extends TestCase {
    private QuadTree qtree;
    private Point pt = new Point("a", 1, 2);

    /**
     * Set up method
     */
    public void setUp() {
        qtree = new QuadTree();
    }

    /**
     * Tests dump
     */
    public void testDump() {
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
                + "1 quadtree nodes printed", qtree.dump());
        qtree.insert(pt);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024:\n"
                + "(a, 1, 2)\n" + "1 quadtree nodes printed", qtree.dump());
    }

    /**
     * Tests insert using dump
     */
    public void testInsert() {
        qtree.insert(pt);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024:\n"
                + "(a, 1, 2)\n" + "1 quadtree nodes printed", qtree.dump());
    }

    /**
     * Test insert if all duplicates and size > 3
     */
    public void testInsert1() {
        Point pt2 = new Point("b", 1, 2);
        Point pt3 = new Point("c", 1, 2);
        Point pt4 = new Point("d", 1, 2);
        Point pt5 = new Point("e", 1, 2);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.insert(pt5);
        assertEquals(
                "QuadTree dump:\n" + "Node at 0, 0, 1024:\n" + "(a, 1, 2)\n"
                        + "(b, 1, 2)\n" + "(c, 1, 2)\n" + "(d, 1, 2)\n"
                        + "(e, 1, 2)\n" + "1 quadtree nodes printed",
                qtree.dump());
    }

    /**
     * Test insert 3 same 1 different
     */
    public void testInsert2() {
        Point pt2 = new Point("b", 300, 300);
        Point pt3 = new Point("c", 1, 3); // Different
        Point pt4 = new Point("d", 300, 300);
        Point pt5 = new Point("e", 300, 300);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.insert(pt5);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Internal\n"
                + "  Node at 0, 0, 512: Internal\n" + "    Node at 0, 0, 256:\n"
                + "    (c, 1, 3)\n" + "    Node at 256, 0, 256: Empty\n"
                + "    Node at 0, 256, 256: Empty\n"
                + "    Node at 256, 256, 256:\n" + "    (b, 300, 300)\n"
                + "    (d, 300, 300)\n" + "    (e, 300, 300)\n"
                + "  Node at 512, 0, 512: Empty\n"
                + "  Node at 0, 512, 512: Empty\n"
                + "  Node at 512, 512, 512: Empty\n"
                + "9 quadtree nodes printed", qtree.dump());
    }

    /**
     * Test inserting and splitting into the SW Then removing everything
     */
    public void testInsert3() {
        Point pt2 = new Point("b", 300, 600);
        Point pt3 = new Point("c", 200, 600);
        Point pt4 = new Point("d", 300, 700);
        Point pt5 = new Point("e", 300, 800);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.insert(pt5);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Internal\n"
                + "  Node at 0, 0, 512: Empty\n"
                + "  Node at 512, 0, 512: Empty\n"
                + "  Node at 0, 512, 512: Internal\n"
                + "    Node at 0, 512, 256:\n" + "    (c, 200, 600)\n"
                + "    Node at 256, 512, 256:\n" + "    (b, 300, 600)\n"
                + "    (d, 300, 700)\n" + "    Node at 0, 768, 256: Empty\n"
                + "    Node at 256, 768, 256:\n" + "    (e, 300, 800)\n"
                + "  Node at 512, 512, 512: Empty\n"
                + "9 quadtree nodes printed", qtree.dump());
        qtree.remove(pt5);
        qtree.remove(pt4);
        qtree.remove(pt3);
        qtree.remove(pt2);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
                + "1 quadtree nodes printed", qtree.dump());
    }

    /**
     * Tests Duplicates method
     */
    public void testDuplicates() {
        Point pt2 = new Point("b", 1, 2);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.duplicates();
        assertFuzzyEquals("(1, 2)", systemOut().getHistory());
    }

    /**
     * Test for no duplicates
     */
    public void testDuplicates2() {
        Point pt2 = new Point("b", 900, 100);
        Point pt3 = new Point("c", 100, 100);
        Point pt4 = new Point("d", 100, 900);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.duplicates();
        assertFuzzyEquals("", systemOut().getHistory());
    }

    /**
     * Test for duplicates if root == null
     */
    public void testDuplicates3() {
        qtree.duplicates();
        assertFuzzyEquals("", systemOut().getHistory());
    }

    /**
     * Test Region Search
     */
    public void testRegionSearch() {
        Point pt2 = new Point("b", 5, 5);
        Point pt3 = new Point("c", 100, 100);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.regionsearch(0, 0, 20, 20);
        assertFuzzyEquals(
                "point found a 1 2\n" + "point found b 5 5\n"
                        + "1 quadtree nodes visited\n",
                systemOut().getHistory());
    }

    /**
     * Test region search for nothing found
     */
    public void testRegionSearch2() {
        Point pt2 = new Point("b", 5, 5);
        Point pt3 = new Point("c", 100, 100);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.regionsearch(900, 900, 100, 100);
        assertFuzzyEquals("1 quadtree nodes visited", systemOut().getHistory());
    }

    /**
     * Test Region search when region starts outside of bounds
     */
    public void testRegionSearch3() {
        Point pt2 = new Point("b", 5, 5);
        Point pt3 = new Point("c", 100, 100);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.regionsearch(-5, -5, 30, 30);
        assertFuzzyEquals(
                "point found a 1 2\n" + "point found b 5 5\n"
                        + "1 quadtree nodes visited\n",
                systemOut().getHistory());
    }

    /**
     * Test region search when region is not valid
     */
    public void testRegionSearch4() {
        qtree.regionsearch(0, 0, -2, 5);
        assertFuzzyEquals("1 quadtree nodes visited", systemOut().getHistory());
    }

    /**
     * Test region search when region partly is out of bounds but valid
     */
    public void testRegionSearch5() {
        Point pt2 = new Point("x", 1002, 1002);
        qtree.insert(pt2);
        qtree.regionsearch(1000, 1000, 50, 50);
        assertFuzzyEquals(
                "point found x 1002 1002\n" + "1 quadtree nodes visited",
                systemOut().getHistory());
    }

    /**
     * Region search test
     */
    public void testRegionSearch6() {
        Point pt2 = new Point("y", 10, 10);
        Point pt3 = new Point("x", 20, 20);
        Point pt4 = new Point("z", 50, 50);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.regionsearch(10, 10, 20, 20);
        assertFuzzyEquals("point found y 10 10\n" + "point found x 20 20\n"
                + "6 quadtree nodes visited", systemOut().getHistory());
    }

    /**
     * Region search test
     */
    public void testRegionSearch7() {
        Point pt2 = new Point("y", 600, 10);
        Point pt3 = new Point("x", 700, 20);
        Point pt4 = new Point("z", 800, 50);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.regionsearch(500, -100, 300, 300);
        assertFuzzyEquals("point found y 600 10\n" + "point found x 700 20\n"
                + "4 quadtree nodes visited", systemOut().getHistory());
    }

    /**
     * Test remove
     */
    public void testRemove() {
        qtree.insert(pt);
        qtree.remove(pt);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
                + "1 quadtree nodes printed", qtree.dump());
    }

    /**
     * Test remove when point does not exist
     */
    public void testRemove1() {
        qtree.remove(pt);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
                + "1 quadtree nodes printed", qtree.dump());
    }

    /**
     * 
     */
    public void testRemove2() {
        Point pt2 = new Point("b", 600, 600); // SE
        Point pt3 = new Point("c", 100, 600); // SW
        Point pt4 = new Point("d", 600, 100); // NE
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Internal\n"
                + "  Node at 0, 0, 512:\n" + "  (a, 1, 2)\n"
                + "  Node at 512, 0, 512:\n" + "  (d, 600, 100)\n"
                + "  Node at 0, 512, 512:\n" + "  (c, 100, 600)\n"
                + "  Node at 512, 512, 512:\n" + "  (b, 600, 600)\n"
                + "5 quadtree nodes printed", qtree.dump());
        qtree.remove(pt);
        qtree.remove(pt2);
        qtree.remove(pt3);
        qtree.remove(pt4);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
                + "1 quadtree nodes printed", qtree.dump());
    }

    /**
     * Test remvoing non existing pt
     */
    public void testRemove3() {
        // Hasnt been inserted yet
        qtree.remove(pt);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
                + "1 quadtree nodes printed", qtree.dump());

    }

    /**
     * Test case for remove
     */
    public void testRemove4() {
        Point pt2 = new Point("b", 600, 600);
        Point pt3 = new Point("b", 700, 600);
        Point pt4 = new Point("b", 800, 600);
        Point pt5 = new Point("b", 650, 600);
        Point pt6 = new Point("b", 700, 650);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.insert(pt5);
        qtree.insert(pt6);
        qtree.remove(pt4);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Internal\n"
                + "  Node at 0, 0, 512: Empty\n"
                + "  Node at 512, 0, 512: Empty\n"
                + "  Node at 0, 512, 512: Empty\n"
                + "  Node at 512, 512, 512: Internal\n"
                + "    Node at 512, 512, 256: Internal\n"
                + "      Node at 512, 512, 128:\n" + "      (b, 600, 600)\n"
                + "      Node at 640, 512, 128:\n" + "      (b, 700, 600)\n"
                + "      (b, 650, 600)\n"
                + "      Node at 512, 640, 128: Empty\n"
                + "      Node at 640, 640, 128:\n" + "      (b, 700, 650)\n"
                + "    Node at 768, 512, 256: Empty\n"
                + "    Node at 512, 768, 256: Empty\n"
                + "    Node at 768, 768, 256: Empty\n"
                + "13 quadtree nodes printed", qtree.dump());
    }

    /**
     * Test Case for remove
     */
    public void testRemove5() {
        Point pt2 = new Point("b", 600, 600);
        Point pt3 = new Point("b", 600, 100);
        Point pt4 = new Point("b", 100, 600);
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.remove(pt4);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024:\n"
                + "(b, 600, 600)\n" + "(a, 1, 2)\n" + "(b, 600, 100)\n"
                + "1 quadtree nodes printed", qtree.dump());
    }

    /**
     * Test Case for removing
     */
    public void testRemove6() {
        Point pt2 = new Point("b", 1, 1);
        Point pt3 = new Point("b", 1, 2);
        Point pt4 = new Point("b", 2, 1);
        Point pt5 = new Point("b", 2, 2);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.insert(pt5);
        qtree.remove(pt2);
        assertEquals("QuadTree dump:\n" 
            + "Node at 0, 0, 1024:\n"
            + "(b, 2, 1)\n" 
            + "(b, 1, 2)\n" 
            + "(b, 2, 2)\n"
            + "1 quadtree nodes printed", qtree.dump());

    }
    /**
     * Test Case for removing
     */
    public void testRemove7() {
        Point pt2 = new Point("b", 1, 1);
        Point pt3 = new Point("b", 1, 2);
        Point pt4 = new Point("b", 2, 1);
        Point pt5 = new Point("b", 2, 2);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        qtree.insert(pt5);
        qtree.remove(pt3);
        assertEquals("QuadTree dump:\n" 
            + "Node at 0, 0, 1024:\n"
            + "(b, 2, 2)\n"
            + "(b, 1, 1)\n"
            + "(b, 2, 1)\n"
            + "1 quadtree nodes printed", qtree.dump());

    }

    /**
     * Test reset
     */
    public void testReset() {
        Point pt2 = new Point("b", 600, 600); // SE
        Point pt3 = new Point("c", 100, 600); // SW
        Point pt4 = new Point("d", 600, 100); // NE
        qtree.insert(pt);
        qtree.insert(pt2);
        qtree.insert(pt3);
        qtree.insert(pt4);
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Internal\n"
                + "  Node at 0, 0, 512:\n" + "  (a, 1, 2)\n"
                + "  Node at 512, 0, 512:\n" + "  (d, 600, 100)\n"
                + "  Node at 0, 512, 512:\n" + "  (c, 100, 600)\n"
                + "  Node at 512, 512, 512:\n" + "  (b, 600, 600)\n"
                + "5 quadtree nodes printed", qtree.dump());
        qtree.reset();
        assertEquals("QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
                + "1 quadtree nodes printed", qtree.dump());
    }
}