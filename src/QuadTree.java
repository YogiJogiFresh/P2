/**
 * Quad Tree class
 * Uses flyweight design and abstract classes
 * @author Alan Kai
 * @version 3.5
 */
public class QuadTree {
    private Node root;
    private Node flyweight;
    private int size = 0;
    private int numValues = 0;
    private LinkedList keep = new LinkedList();

    /**
     * Constructor Initialize Flyweight
     */
    public QuadTree() {
        flyweight = new ENode();
        root = flyweight;
    }

    /**
     * Reset to use flyweight node
     */
    public void reset() {
        root = flyweight;
        size = 0;
    }

    /**
     * Insert recursively
     *
     * @param point Coordinate Point
     */
    public void insert(Point point) {
        root = root.insert(point, 0, 0, 1024);
    }

    /**
     * Remove recursively
     *
     * @param point Coordinate point
     */
    public void remove(Point point) {
        root = root.remove(point, 0, 0, 1024);

        for (int i = 0; i < keep.size(); i++) {
            // System.out.println("inserting: " + keep.get(i));
            insert((Point) keep.get(i));
        }

        for (int k = 0; k < keep.size(); k++) {
            keep.remove(k);
        }

    }

    /**
     * Interface for the Node classes Empty, Leaf, Internal
     *
     * @author Alan Kai
     * @author Chris Rocconi
     *
     */
    private interface Node {
        /**
         * Abstract method insert
         *
         * @param pt Point
         * @param curX Current x
         * @param curY Current y
         * @param bound bounds
         * @return Inserted
         */
        public abstract Node insert(Point pt, int curX, int curY, int bound);

        /**
         * Abstract method remove
         *
         * @param pt point to be removed
         * @param curX current x
         * @param curY current y
         * @param bound bound
         * @return removed
         */
        public abstract Node remove(Point pt, int curX, int curY, int bound);
    }

    /**
     * Empty Node
     *
     * @author Alan Kai
     *
     */
    private class ENode implements Node {
        @Override
        public Node insert(Point pt, int curX, int curY, int bound) {
            LeafNode leaf = new LeafNode();
            leaf.insert(pt, curX, curY, bound);
            return leaf;
        }

        @Override
        public Node remove(Point pt, int curX, int curY, int bound) {
            // removes nothing since its an empty node
            return this;
        }
    }

    /**
     * Leaf Node
     *
     * @author Chris Rocconi
     * @author Alan Kai
     */
    private class INode implements Node {
        private Node nw;
        private Node ne;
        private Node sw;
        private Node se;

        /**
         * Internal node constructor
         */
        public INode() {
            nw = flyweight;
            ne = flyweight;
            sw = flyweight;
            se = flyweight;
            // size++;
        }

        @Override
        public Node insert(Point pt, int curX, int curY, int bound) {
            int half = bound / 2;
            if (pt.getX() < curX + half && pt.getY() < curY + half) {
                nw = nw.insert(pt, curX, curY, half);
                return this;
            }
            else if (pt.getX() >= curX + half && pt.getY() < curY + half) {
                ne = ne.insert(pt, half + curX, curY, half);
                return this;
            }
            else if (pt.getX() < curX + half && pt.getY() >= curY + half) {
                sw = sw.insert(pt, curX, curY + half, half);
                return this;
            }
            else {
                se = se.insert(pt, curX + half, curY + half, half);
                return this;
            }
        }

        /**
         * returns nw
         *
         * @return the nw node
         */
        public Node getNW() {
            return nw;
        }

        /**
         * returns ne
         *
         * @return the ne node
         */
        public Node getNE() {
            return ne;
        }

        /**
         * return sw
         *
         * @return the sw node
         */
        public Node getSW() {
            return sw;
        }

        /**
         * return SE
         *
         * @return the SE node
         */
        public Node getSE() {
            return se;
        }

        @Override
        public Node remove(Point pt, int curX, int curY, int bound) {
            int half = bound / 2;
            if (pt.getX() < curX + half && pt.getY() < curY + half) {

                nw = nw.remove(pt, curX, curY, half);
            }
            else if (pt.getX() >= curX + half && pt.getY() < curY + half) {
                ne = ne.remove(pt, curX + half, curY, half);
            }
            else if (pt.getX() < curX + half && pt.getY() >= curY + half) {
                sw = sw.remove(pt, curX, curY + half, half);
            }
            else {
                se = se.remove(pt, curX + half, curY + half, half);
            }
            return merge();
        }

        /**
         * Helper method for remove
         *
         * @return Node
         */
        private Node merge() {
            if (nw.getClass().toString().contains("LeafNode") && ne == flyweight
                    && sw == flyweight && se == flyweight) {
                size -= 4;

                return nw;
            }
            else if (ne.getClass().toString().contains("LeafNode")
                    && nw == flyweight && sw == flyweight && se == flyweight) {
                size -= 4;
                return ne;
            }
            else if (sw.getClass().toString().contains("LeafNode")
                    && nw == flyweight && ne == flyweight && se == flyweight) {
                size -= 4;
                return sw;
            }
            else if (se.getClass().toString().contains("LeafNode")
                    && nw == flyweight && ne == flyweight && sw == flyweight) {
                size -= 4;
                return se;
            }
            else if (!nw.getClass().toString().contains("INode")
                    && !ne.getClass().toString().contains("INode")
                    && !sw.getClass().toString().contains("INode")
                    && !se.getClass().toString().contains("INode")) {
                if (nw == flyweight
                        && ne.getClass().toString().contains("LeafNode")
                        && sw.getClass().toString().contains("LeafNode")
                        && se.getClass().toString().contains("LeafNode")) {

                    LinkedList southwest = ((LeafNode) sw).getLinkedList();

                    LinkedList southeast = ((LeafNode) se).getLinkedList();

                    LinkedList northeast = ((LeafNode) ne).getLinkedList();

                    int levelValues = southwest.size() + southeast.size()
                            + northeast.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < southwest.size(); i++) {
                        keep.add(southwest.get(i));
                    }

                    for (int i = 0; i < southeast.size(); i++) {
                        keep.add(southeast.get(i));
                    }

                    return ne;
                }

                else if (ne == flyweight
                        && nw.getClass().toString().contains("LeafNode")
                        && sw.getClass().toString().contains("LeafNode")
                        && se.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) sw).getLinkedList();

                    LinkedList second = ((LeafNode) se).getLinkedList();

                    LinkedList third = ((LeafNode) nw).getLinkedList();

                    int levelValues = first.size() + second.size()
                            + third.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    for (int i = 0; i < second.size(); i++) {
                        keep.add(second.get(i));
                    }

                    return nw;
                }

                else if (sw == flyweight
                        && nw.getClass().toString().contains("LeafNode")
                        && se.getClass().toString().contains("LeafNode")
                        && ne.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) nw).getLinkedList();

                    LinkedList second = ((LeafNode) ne).getLinkedList();

                    LinkedList third = ((LeafNode) se).getLinkedList();

                    int levelValues = first.size() + second.size()
                            + third.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    for (int i = 0; i < second.size(); i++) {
                        keep.add(second.get(i));
                    }

                    return se;
                }

                else if (se == flyweight
                        && nw.getClass().toString().contains("LeafNode")
                        && ne.getClass().toString().contains("LeafNode")
                        && sw.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) ne).getLinkedList();

                    LinkedList second = ((LeafNode) nw).getLinkedList();

                    LinkedList third = ((LeafNode) sw).getLinkedList();

                    int levelValues = first.size() + second.size()
                            + third.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    for (int i = 0; i < second.size(); i++) {
                        keep.add(second.get(i));
                    }

                    return sw;
                }

                else if (nw == flyweight && ne == flyweight
                        && sw.getClass().toString().contains("LeafNode")
                        && se.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) sw).getLinkedList();

                    LinkedList second = ((LeafNode) se).getLinkedList();

                    int levelValues = first.size() + second.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    return se;
                }

                else if (nw == flyweight && sw == flyweight
                        && ne.getClass().toString().contains("LeafNode")
                        && se.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) se).getLinkedList();

                    LinkedList second = ((LeafNode) ne).getLinkedList();

                    int levelValues = first.size() + second.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    return ne;
                }

                else if (nw == flyweight && se == flyweight
                        && sw.getClass().toString().contains("LeafNode")
                        && ne.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) sw).getLinkedList();

                    LinkedList second = ((LeafNode) ne).getLinkedList();

                    int levelValues = first.size() + second.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    return ne;
                }

                else if (sw == flyweight && ne == flyweight
                        && nw.getClass().toString().contains("LeafNode")
                        && se.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) nw).getLinkedList();

                    LinkedList second = ((LeafNode) se).getLinkedList();

                    int levelValues = first.size() + second.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    return se;
                }

                else if (se == flyweight && ne == flyweight
                        && sw.getClass().toString().contains("LeafNode")
                        && nw.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) sw).getLinkedList();

                    LinkedList second = ((LeafNode) nw).getLinkedList();

                    int levelValues = first.size() + second.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    return nw;
                }

                else if (sw == flyweight && se == flyweight
                        && nw.getClass().toString().contains("LeafNode")
                        && ne.getClass().toString().contains("LeafNode")) {

                    LinkedList first = ((LeafNode) nw).getLinkedList();

                    LinkedList second = ((LeafNode) ne).getLinkedList();

                    int levelValues = first.size() + second.size();

                    if (levelValues > 3) {
                        return this;
                    }

                    size -= 4;

                    for (int i = 0; i < first.size(); i++) {
                        keep.add(first.get(i));
                    }

                    return ne;
                }

                else {
                    return this;
                }
            }
            else {
                return this;
            }
        }
    }

    /**
     * LeafNode
     *
     * @author Chris Rocconi
     * @author Alan Kai
     *
     */
    private class LeafNode implements Node {
        private LinkedList count = new LinkedList();
        private Point point;

        @Override
        public Node insert(Point pt, int curX, int curY, int bound) {
            if (point == null) {
                count.add(pt);
                point = pt;
                // numValues++;
                return this;
            }
            /**
             * else if (point.equals(pt)) { count.add(pt); return this; }
             */
            else if ((count.size() < 3)
                    || ((count.size() >= 3) && (!count.multipleUnique())
                            && (count.get(0).equals(pt)))) {
                count.add(pt);
                // numValues++;
                return this;
            }
            else {
                count.add(pt);
                numValues++;

                Node internal = new INode();
                internal = internal.insert(point, curX, curY, bound);
                for (int k = 1; k < count.size() - 1; k++) {
                    internal = internal.insert((Point) count.get(k), curX, curY,
                            bound);
                }
                internal = internal.insert(pt, curX, curY, bound);
                size += 4; // test
                return internal;
            }

        }

        /**
         * toString method
         */
        /**
         * public String toString() { String str = ""; str += point.getName() +
         * ", " + point.getX() + ", " + point.getY(); return str; }
         */
        /**
         * GetX of point
         *
         * @return x coordinate
         */
        /**
         * public int getX() { return point.getX(); }
         */
        /**
         * getY of point
         *
         * @return y coordinate
         */
        /**
         * public int getY() { return point.getY(); }
         */

        /**
         * get Linkedlist
         *
         * @return LinkedList
         */
        public LinkedList getLinkedList() {
            return count;
        }

        @Override
        public Node remove(Point pt, int curX, int curY, int bound) {
            if (count.size() > 1) {
                for (int i = 0; i < count.size(); i++) {
                    if ((((Point) count.get(i)).getX() == pt.getX())
                            && (((Point) count.get(i)).getY() == pt.getY())) {
                        count.remove(i);
                        numValues--;
                        break;
                    }
                }

                // System.out.println(numValues);
                if (numValues < 4) {
                    for (int k = 0; k < keep.size(); k++) {
                        keep.remove(k);
                    }

                    for (int i = 0; i < count.size(); i++) {
                        keep.add(count.get(i));
                    }

                    return flyweight; // deletes the whole leafNode, some way to
                                      // make it merge without deleting the whole
                                      // leafnode?
                }

                return this;
            }

            else if ((count.size() == 1) && (point.getX() == pt.getX())
                    && (point.getY() == pt.getY())) {
                // Trim method? Traverse back
                // Flyweight is the removed
                // Not merging correctly
                numValues--;

                return flyweight;
            }
            else {
                // Not found
                return this;
            }
        }
    }

    /**
     * dumps the nodes of the QuadTree
     *
     * @return returns the string representing the nodes of the QuadTree
     */
    public String dump() {
        String result = "";

        result += "QuadTree dump:\n" + /* "Node at 0, 0, 1024: Empty\n" + */
                dumpTraversal(root, 0, 0, 0, 1024) + (size + 1)
                + " quadtree nodes printed";
        return result;
    }

    /**
     * recursively calls for preorder traversal through the QuadTree
     *
     * @param rt root node
     * @param spaces number of spaces
     * @param currX current x
     * @param currY current y
     * @param width bound width
     * @return returns the string representing the Quad Tree
     */
    public String dumpTraversal(Node rt, int spaces, int currX, int currY,
            int width) {
        String str = "";

        if (rt == null) {
            return str;
        }
        if (rt.getClass().toString().contains("LeafNode")) {
            for (int i = 0; i < spaces; i++) {
                str += " ";
            }
            str += "Node at " + currX + ", " + currY + ", " + width + ":\n";
            for (int i = 0; i < ((LeafNode) rt).getLinkedList().size(); i++) {
                for (int j = 0; j < spaces; j++) {
                    str += " ";
                }
                str += "("
                        + ((Point) ((LeafNode) rt).getLinkedList().get(i))
                                .getName()
                        + ", "
                        + ((Point) ((LeafNode) rt).getLinkedList().get(i))
                                .getX()
                        + ", "
                        + ((Point) ((LeafNode) rt).getLinkedList().get(i))
                                .getY()
                        + ")" + "\n"; //
            }
            return str;
        }
        else if (rt.getClass().toString().contains("INode")) {
            for (int i = 0; i < spaces; i++) {
                str += " ";
            }
            str += "Node at " + currX + ", " + currY + ", " + width + ": "
                    + "Internal" + "\n";
            str += dumpTraversal(((INode) rt).getNW(), spaces + 2, currX, currY,
                    width / 2); // get to the northWest
            str += dumpTraversal(((INode) rt).getNE(), spaces + 2,
                    currX + width / 2, currY, width / 2); // get to the
                                                          // northEast
            str += dumpTraversal(((INode) rt).getSW(), spaces + 2, currX,
                    currY + width / 2, width / 2); // get to the southWest
            // Get to the south east
            str += dumpTraversal(((INode) rt).getSE(), spaces + 2,
                    currX + width / 2, currY + width / 2, width / 2);
        }
        else if (rt.getClass().toString().contains("ENode")) {
            for (int i = 0; i < spaces; i++) {
                str += " ";
            }
            str += "Node at " + currX + ", " + currY + ", " + width + ": "
                    + "Empty" + "\n";
        }
        return str;
    }

    /**
     * @return returns the duplicate points found in a system.out
     */
    public String duplicates() {
        // Test to make sure that it really works
        return duplicatesTraversal(root, 0, 0, 1024);
    }

    /**
     * recursively calls for preorder traversal through the QuadTree, outputting
     * the duplicates in each leaf node if they exist.
     *
     * @param rt root node
     * @param currX current x
     * @param currY current y
     * @param width bound width
     * @return returns the string representing the Quad Tree
     */
    public String duplicatesTraversal(Node rt, int currX, int currY,
            int width) {
        String str = "";

        if (rt == null) {
            return str;
        }
        if (rt.getClass().toString().contains("LeafNode")) {
            LinkedList dups = ((LeafNode) rt).getLinkedList().duplicates();
            for (int i = 0; i < dups.size(); i++) {
                System.out.print("(" + ((Point) dups.get(i)).getX()//
                        + ", " + ((Point) dups.get(i)).getY() + ")" + "\n");
            }
            return str;
        }
        else if (rt.getClass().toString().contains("INode")) {
            // Get to the northwest
            duplicatesTraversal(((INode) rt).getNW(), currX, currY, width / 2);
            // Get to the northeast
            duplicatesTraversal(((INode) rt).getNE(), currX + width / 2, currY,
                    width / 2);
            // get to the southWest
            duplicatesTraversal(((INode) rt).getSW(), currX, currY + width / 2,
                    width / 2);
            // Get to the south east
            duplicatesTraversal(((INode) rt).getSE(), currX + width / 2,
                    currY + width / 2, width / 2);
        }
        return str;
    }

    /**
     * Searches a rectangle shaped region and outputs all of the points inside
     *
     * @param x represents the x-coordinate
     * @param y represents the y-coordinate
     * @param w represents the width
     * @param h represents the height
     */
    public void regionsearch(int x, int y, int w, int h) {
        int sum = regionsearchTraversal(root, x, y, w, h, 0, 0, 1024);
        if (sum == 0) {
            System.out.println(1 + " quadtree nodes visited");
        }
        else {
            System.out.println(sum + " quadtree nodes visited");
        }
    }

    /**
     * recursively calls for traversal through the quadtree to find all points
     * within a given rectangle
     *
     * @param rt root node
     * @param x x coordinate of the given rectangle
     * @param y y coordinate of the given rectangle
     * @param w width of the given rectangle
     * @param h height of the given rectangle
     * @param currX current x
     * @param currY current y
     * @param width bound width
     * @return returns the string representing the Quad Tree
     */
    public int regionsearchTraversal(Node rt, int x, int y, int w, int h,
            int currX, int currY, int width) {
        int count = 0;
        if (rt == null) {
            return count;
        }
        if (rt.getClass().toString().contains("LeafNode")) {
            LinkedList listy = ((LeafNode) rt).getLinkedList();
            for (int i = 0; i < listy.size(); i++) {
                if ((((Point) listy.get(i)).getX() >= x)
                        && ((((Point) listy.get(i)).getX() < x + w)
                                && ((((Point) listy.get(i)).getY() >= y)
                                        && ((((Point) listy.get(i)).getY() < y
                                                + h))))) {
                    System.out
                            .print("Point found: " + "("
                                    + ((Point) ((LeafNode) rt).getLinkedList()
                                            .get(i)).getName()
                                    + ", "
                                    + ((Point) ((LeafNode) rt).getLinkedList()
                                            .get(i)).getX()
                                    + ", " + ((Point) ((LeafNode) rt)
                                            .getLinkedList().get(i)).getY()
                            + ")" + "\n"); //
                }
            }
            count++;

            return count;
        }
        else if (rt.getClass().toString().contains("INode")) {
            if ((x >= currX) && (x < (currX + (width / 2))) && (y >= currY)
                    && (y < (currY + (width / 2)))
                    || (x + w >= currX) && (x + w < (currX + (width / 2)))
                            && (y >= currY) && (y < (currY + (width / 2)))
                    || (x >= currX)
                            && (x < (currX + (width / 2))
                                    && (y + h >= currY) && (y
                                            + h < (currY + (width / 2)))
                            || (x + w >= currX)
                                    && (x + w < (currX + (width / 2)))
                                    && (y + h >= currY)
                                    && (y + h < (currY + (width / 2))))) {
                count++;
                count += regionsearchTraversal(((INode) rt).getNW(), x, y, w, h,
                        currX, currY, width / 2); // get to the northWest

            }
            if ((x >= currX + (width / 2))
                    && (x < ((currX + (width / 2)) + (width / 2)))
                    && (y >= currY) && (y < (currY + (width / 2)))
                    || (x + w >= currX + (width / 2))
                            && (x + w < ((currX + (width / 2)) + (width / 2)))
                            && (y >= currY) && (y < (currY + (width / 2)))
                    || (x >= currX + (width / 2))
                            && (x < ((currX + (width / 2)) + (width / 2)))
                            && (y + h >= currY)
                            && (y + h < (currY + (width / 2)))
                    || (x + w >= currX + (width / 2))
                            && (x + w < ((currX + (width / 2)) + (width / 2)))
                            && (y + h >= currY)
                            && (y + h < (currY + (width / 2)))) {
                count++;
                count += regionsearchTraversal(((INode) rt).getNE(), x, y, w, h,
                        currX + width / 2, currY, width / 2); // get to the
                                                              // northEast
            }
            if ((x >= currX) && (x < (currX + (width / 2)))
                    && (y >= currY + (width / 2))
                    && (y < ((currY + (width / 2)) + (width / 2)))
                    || (x + w >= currX) && (x + w < (currX + (width / 2)))
                            && (y >= (currY + (width / 2)))
                            && (y < ((currY + (width / 2)) + (width / 2)))
                    || (x >= currX)
                            && (x < (currX + (width / 2))
                                    && (y + h >= (currY + (width / 2))))
                            && (y + h < ((currY + (width / 2)) + (width / 2))
                                    || (x + w >= currX)
                                            && (x + w < (currX + (width / 2)))
                                            && (y + h >= (currY + (width / 2))))
                            && (y + h < ((currY + (width / 2)))
                                    + (width / 2))) {
                count++;
                count += regionsearchTraversal(((INode) rt).getSW(), x, y, w, h,
                        currX, currY + width / 2, width / 2); // get to the
                                                              // southWest
            }
            if ((x >= currX + (width / 2))
                    && (x < ((currX + (width / 2)) + (width / 2)))
                    && (y >= currY + (width / 2))
                    && (y < ((currY + (width / 2)) + (width / 2)))
                    || (x + w >= currX + (width / 2))
                            && (x + w < (currX + (width / 2) + (width / 2)))
                            && (y >= (currY + (width / 2)))
                            && (y < ((currY + (width / 2)) + (width / 2)))
                    || (x >= currX + (width / 2))
                            && (x < (currX + (width / 2) + (width / 2))
                                    && (y + h >= (currY + (width / 2))))
                            && (y + h < ((currY + (width / 2)) + (width / 2))
                                    || (x + w >= currX + (width / 2))
                                            && (x + w < (currX + (width / 2)
                                                    + (width / 2)))
                                            && (y + h >= (currY + (width / 2))))
                            && (y + h < ((currY + (width / 2)))
                                    + (width / 2))) {
                // Get to the south east
                count++;
                count += regionsearchTraversal(((INode) rt).getSE(), x, y, w, h,
                        currX + width / 2, currY + width / 2, width / 2);
            }
        }
        return count;
    }
}