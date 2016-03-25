import java.util.*;

//import SkipList.SkipNode;

//import SkipList.SkipNode;

import java.lang.reflect.Array;

/**
 * @author Alan Kai (yogijogi)
 * @version 2.3
 * @param <K> Key
 * @param <E> Value
 */
public class SkipList<K extends Comparable<K>, E> {
    /**
     *
     * @author Alan Kai (yogijogi)
     * @version 1.0
     *
     * @param <K>
     * @param <E>
     */
    @SuppressWarnings("hiding")
    private class SkipNode<K extends Comparable<K>, E> {

        private KVPair<K, E> kvp;
        private SkipNode<K, E>[] forward;
        private int depth;
        /**
         * SkipNode Constructor
         * @param pair KVPair
         * @param level Level of node
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, E> pair, int level) {
            kvp = pair;
            forward = new SkipNode[level + 1];
            depth = level + 1;

            for (int i = 0; i < level; i++) {
                forward[i] = null;
            }
        }

        /**
         * Gets the node's key and value pair.
         *
         * @return The node's KVPair<K, E>
         */
        public KVPair<K, E> element() {
            return kvp;
        }

        /**
         * Gets the node's depth in the SkipList.
         *
         * @return The node's depth as an integer.
         */
        public int depth() {
            return depth;
        }


    }

    private SkipNode<K, E> head;
    private int level;
    private int size;
    private Random rnd;

    /**
     * SkipList Constructor
     */
    public SkipList() {
        rnd = new Random();
        level = 1;
        size = 0;
        head = new SkipNode<K, E>(null, level);
    }

    /**
     * Adjusts the head node.
     *
     * @param newLevel The new highest level to update to.
     */
    private void adjustHead(int newLevel) {
        SkipNode<K, E> temp = head;
        head = new SkipNode<K, E>(null, newLevel);
        for (int i = 0; i <= level; i++) {
            head.forward[i] = temp.forward[i];
        }
        level = newLevel;
    }

    /**
     * Pick a level using geometric distribution.
     *
     * @return The generated random level.
     */
    private int randomLevel() {
        int lev;
        for (lev = 0; rnd.nextInt(2) == 0; lev++) {
            // Do nothing
        }
        return lev;
    }

    /**
     * Gets the size of the SkipList.
     *
     * @return The size of the SkipList.
     */
    public int size() {
        return size;
    }

    /**
     * Insert a KVPair into the SkipList.
     *
     * @param it The KVPair to insert.
     * @return True if insert successful; otherwise false.
     */
    public boolean insert(KVPair<K, E> it) {
        int newLevel = randomLevel();
        Comparable<K> k = it.key();
        if (level < newLevel) {
            adjustHead(newLevel);
        }
        @SuppressWarnings("unchecked")
        SkipNode<K, E>[] update = (SkipNode[]) Array
                .newInstance(SkipList.SkipNode.class, level + 1);
        SkipNode<K, E> x = head; // Start at header node
        for (int i = level; i >= 0; i--) {
            // Find insert position
            while ((x.forward[i] != null)
                    && (k.compareTo((K) ((x.forward[i]).element()).key()) > 0))
                x = x.forward[i];
            update[i] = x; // Track end at level i
        }
        x = new SkipNode<K, E>(it, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            // Splice into list
            x.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = x; // Who y points to
        }
        size++; // Increment dictionary size
        return true;
    }

    /**
     * finds rectangles using a key
     *
     * @param key represents the key we're searching for
     * @return returns the string array of rectangle parts
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String[] search(Comparable<K> key) {
        SkipNode x = head; // Dummy header node
        String rectstr = "";
        for (int i = level; i >= 0; i--) // For each level
        {
            while ((x.forward[i] != null) &&
                    (key.compareTo((K) x.forward[i].element().key()) > 0)) //go
            {
                x = x.forward[i]; // Go one last step
            }
        }
        x = x.forward[0]; // Move to actual record, if it exists
        while ((x != null) && (key.compareTo((K) x.element().key()) == 0))
        {
            rectstr += "(" + x.element().key().toString() + ", " +
                    x.element().value().toString() + ")";
            rectstr += "\n";
            x = x.forward[0];
        }
        if (rectstr.isEmpty())
        {
            return null;
        }
        else
        {
            String[] strarr = rectstr.split("\n");
            return strarr;
        }
    }

    /**
     * Dumps all the current entries in the SkipList, starting at the entry at
     * the left-most node.
     *
     * @return String of information about all the entries in the SkipList.
     */
    public String dump() {
        if (size == 0) {
            return "Node has depth 1, Value (null)\nSkipList size is: 0";
        }
        String str = "";
        SkipNode<K, E> node = head;
        while (node.forward[0] != null) {
            int depth = node.depth();
            str += "Node has depth " + depth + ", ";
            if (node == head) {
                str += "Value (null)\n";
            }
            else {
                str += "Value (" + node.element().key() + ", "
                        + node.element().value().toString() + ")\n";
            }
            node = node.forward[0];
        }
        int depth = node.depth();
        str += "Node has depth " + depth + ", ";
        str += "Value (" + node.element().key() + ", "
                + node.element().value().toString() + ")\n";
        str += "SkipList size is: " + size;
        return str;
    }
    /**
     * RegionSearch method
     * @param region Region to search for
     * @return String array of intersecting Points
     */
    /**
    public String[] regionSearch(Point region) {
        SkipNode<K, E> cur = head;
        String rectstr = "";
        boolean rect1;
        boolean rect2;
        boolean reg1;
        boolean reg2;
        while (cur.forward[0] != null) {
            Point in = (Point) cur.forward[0].element().value();
            rect1 = in.getX() + in.getW() > region.getX()
                    && in.getX() < region.getX() + region.getW();
            reg1 = region.getX() + region.getW() > in.getX()
                    && region.getX() < in.getX() + in.getW();
            rect2 = in.getY() + in.getH() > region.getY()
                    && in.getY() < region.getY() + region.getH();
            reg2 = region.getY() + region.getH() > in.getY()
                    && region.getY() < in.getY() + in.getH();
            if (rect1 || reg1) {
                if (rect2 || reg2) {
                    rectstr += cur.forward[0].element();
                    rectstr += "\n";
                }
            }
            cur = cur.forward[0];
        }
        if (rectstr.isEmpty()) {
            return null;
        }
        String[] result = rectstr.split("\n");
        return result;
    }
    */
    /**
     * removes a rectangle from the SkipList based on name
     *
     * @param key represents the name of the to be removed rectangle
     *
     * @return returns the removed rectangle or null if failed
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String remove(Comparable<K> key) {

        SkipNode x = head; // Dummy header node
        String rectstr = "";
        SkipNode[] update = (SkipNode[])Array.newInstance(
                SkipList.SkipNode.class, level + 1);
        for (int i = level; i >= 0; i--) // For each level...
        {
            while ((x.forward[i] != null) &&
                    (key.compareTo((K) x.forward[i].element().key()) > 0)) //go
            {
                x = x.forward[i]; // Go one last step
            }
            update[i] = x;
        }
        //x = x.forward[0]; // Move to actual record, if it exists
        if ((x.forward[0] != null) &&
                (key.compareTo((K) x.forward[0].element().key()) == 0))
        {
            rectstr = x.forward[0].element().key().toString() + ", " +
                    x.forward[0].element().value().toString();
        }
        if (rectstr.isEmpty())
        {
            return null;
        }
        else
        {
            if ((update[0].forward[0] != null) &&
                    (key.compareTo((K)update[0].forward[0].element().key())
                            == 0)
                     && (update[0].forward[0].forward[0] == null))
            {
                update[0].forward[0] = null;
            }
            else
            {
                for (int i = 0; i < x.depth(); i++)
                {
                    if ((update[i].forward[i] != null) &&
                            (key.compareTo((K)update[i].forward[i].
                                    element().key()) == 0)
                            && (update[i].forward[i].forward[i] != null))
                    {
                        update[i].forward[i] = update[i].forward[i].forward[i];
                    }

                }
            }
            size--;
            return rectstr;
        }
    }

    /**
     * Removes the Skiplist entry by its KVPair value.
     *
     * @param it The value to look for in the SkipList to remove.
     * @return String of the first match found and removed.
     */
    public String remove(E it) {
        String result = "";
        SkipNode<K, E> cur = head;
        KVPair<K, E> removed = null;
        boolean lastNode = false;
        int lvl = 0;
        boolean found = false;

        while (cur.forward[0] != null) {
            if (it.equals(cur.forward[0].element().value()) == true) {
                removed = cur.forward[0].element();
                result = removed.toString();
                found = true;
                if (cur.forward[0].forward[0] == null)
                    lastNode = true;
                break;
            }
            cur = cur.forward[0];
        }
        //Length of next node
        int length = cur.forward.length;
        int cd = cur.depth;
        if (found == true) {
            while (lvl < length) {
                if ((cur.forward[lvl] == cur.forward[0])
                        && (cur.forward[lvl].forward[lvl] != null)) {
                    cur.forward[lvl] = cur.forward[lvl].forward[lvl];
                }
                lvl++;
            }
            //Special Case: If found node is last node
            if (lastNode == true) {
                for (int i = 0; i < cd; i++) {
                    cur.forward[i] = null;
                }
            }
            size--;
        }

        return result;
    }

}