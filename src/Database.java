/**
 * Database to handle data structures
 * Communication 
 * @author Alan Kai
 * @version 3.2
 */
public class Database {
    /**
     * SkipList datastructure
     */
    private SkipList<String, Point> skipList;
    private QuadTree qTree;
    private KVPair<String, Point> kvp;
    /**
     * Database Constructor
     */
    public Database() {
        skipList = new SkipList<String, Point>();
        qTree = new QuadTree();
    }
    /**
     * Validates the rectangle can be created
     * @param r Point to validate
     * @return returns true if rectangle is in boundary 
     *          and starts with a letter
     */
    public boolean validate(Point r) {
        boolean valid = false;
        if (r.x < 0 || r.y < 0
                || r.x > 1024 || r.y > 1024) {
            return valid;
        }
        char ch = (r.name).charAt(0);
        //checks if first char is a letter
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            valid = true;
        }
        return valid;
    }
    /**
     * Validates and inserts rectangle into SkipList
     * @param r Input Rectangle
     */
    public void insert(Point r) {
        kvp = new KVPair<String, Point>(r.name, r);
        if (validate(r) == false) {
            System.out.println("Point rejected: (" + r.name + ", " 
                    + r.x + ", " + r.y + ")");
        }
        else {
            skipList.insert(kvp);
            qTree.insert(r);
            System.out.println("Point inserted: (" + r.name + ", " 
                    + r.x + ", " + r.y + ")");
        }
    }
    /**
     * Remove by name the rectangle by searching
     * the skiplist. Removes first rectangle if found
     * @param name Name of Rectangle to be removed
     */
    public void removeByName(String name) {
        int index = 0;
        if (!Character.isLetter(name.charAt(index))) {
            System.out.println("Point rejected: (" + name + ")");
        }
        else if (searchFound(name) != null) {
            String str = skipList.remove(name);
            String[] strarr = str.split(", ");

            System.out.println("Point removed: "
                    + str.toString());
            Point r = new Point(Integer.parseInt(strarr[1]),
                    Integer.parseInt(strarr[2].substring(0,
                            strarr[2].length() - 1)));
            qTree.remove(r);
        }
        else {
            System.out.println("Point not removed: " + name);
        }
    }
    /**
     * Searches for the rectangle by its coordinates & dimensions
     * in the skiplist and removes first match if found
     * @param r The rectangle to remove
     */
    public void removeByCoord(Point r) {
        if (r.x < 0 || r.y < 0) {
            System.out.println("Point rejected: (" + r.x + ", " + r.y + ")");
        }
        else {
            String result = skipList.remove(r);
            qTree.remove(r);
            if (result.equals("")) {
                System.out.println("Point not found: ("
                        + r.x + ", " + r.y + ")");
            }
            else {
                System.out.println("Point removed: (" + result + ")");
            }
        }
    }
    /**
     * Region is basically a rectangle and finding
     * what rectangles intersect that "rectangle"
     * @param region coordinates to make a rectangle
     */
    public void regionsearch(int[] region) {
        Point r;
        int x = region[0];
        int y = region[1];
        int w = region[2];
        int h = region[3];
        if (w > 0 && h > 0) {
            r = new Point(x, y, w, h);
            System.out.println("Points intersecting region ("
                    + r.toRString() + "):");
            r = new Point(x, y, w, h);
            //String[] results = skipList.regionSearch(r);
            qTree.regionsearch(x, y, w, h);
            
            //if (results != null) {
                //for (String a: results) {
                    //System.out.println("(" + a + ")");
                //}
            //}
        }
        else {
            r = new Point(x, y, w, h);
            System.out.println("Rectangle rejected: ("
                    + r.toRString() + ")");
        }
    }
    /**
     * Searches the skipList for the rectangle name
     * @param name Target rectangle searching for
     * @return an array containing the results of the found rectangles
     */
    public String[] searchFound(String name) {
        String[] result = skipList.search(name);
        return result;
    }
    /**
     * Search for the name of the rectangle
     * @param name Rectangle searching for
     * @param result An array containing the results of the rectangles found
     */
    public void search(String name, String[] result) {
        if (result != null) {
            for (int i = 0; i < result.length; i++)
                System.out.println("Point found: (" + result[i] + ")");
        }
        else {
            System.out.println("Point not found: " + name);
        }
    }
    /**
     * Reports all pairs of rectangles within that intersect
     */
    public void intersections() {
        System.out.println("Intersections pairs:");
        //skipList.intersections();
    }
    /**
     * Prints out a dump of the skipList
     */
    public void dump() {
        System.out.println("SkipList dump:");
        System.out.println(skipList.dump());
        System.out.println(qTree.dump());
    }
    /**
     * duplicates method
     */
    public void duplicates() {
        System.out.println("Duplicate points:");
        qTree.duplicates();
    }
}
