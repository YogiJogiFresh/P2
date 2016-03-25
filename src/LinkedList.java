/**
 * Linked list that holds the points
 *  stored in the Quad Tree Nodes
 *  @author Alan Kai
 *  @version 2.0
 */
public class LinkedList {
    private Node head;
    private int listCount;
    /**
     * Constructor
     */
    public LinkedList() {
        head = new Node(null);
        listCount = 0;
    }
    /**
     * Adds object into list
     * @param e Object
     */
    public void add(Object e) {
        Node temp = new Node(e);
        Node curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = temp;
        listCount++;
    }
    /**
     * Get element
     * @param index Index
     * @return Element
     */
    public Object get(int index) {
        if (index < 0) {
            return null;
        }
        Node curr = null;
        if (head != null) {
            curr = head.next;
            for (int i = 0 ; i < index; i++) {
                if (curr.next == null)
                    return null;
                curr = curr.next;
            }
            return curr.data;
        }
        return curr;
    }
    /**
     * Remove element
     * @param index Index to remove
     * @return true if removed/ false if not
     */
    public boolean remove(int index) {
        if (index < 0 || index > listCount) {
            return false; //out of bounds
        }
        Node curr = head;
        if (head != null) {
            for (int i = 0; i < index; i++) {
                if (curr.next == null)
                    return false;
                curr = curr.next;
            }
            listCount--;
            curr.next = curr.next.next;
            return true;
        }
        return false;
    }
    /**
     * Find by pt in linked list
     * @param x x coordinate of point
     * @param y y coordinate of point
     * @param pos position to start find (0)
     * @return position that point was found in list
     */
    public int findByPt(int x, int y, int pos) {
        if (pos + 1 >= this.size()) {
            return -1; //Not found
        }
        if (x == ((Point) this.get(pos)).getX() &&
                y == ((Point) this.get(pos)).getY()) {
            return pos;
        }
        return findByPt(x, y, pos + 1);
    }
    /**
     * Size of linklist
     * @return size
     */
    public int size() {
        return listCount;
    }

    /**
     * creates a linkedList of integers representing the elements where
     * there are duplicates in the original linkedList
     * @return the linkedList of duplicate locations
     */
    public LinkedList duplicates()//
    {
        LinkedList intarr = new LinkedList();
        boolean dup = false;
        for (int i = 0; i < listCount; i++)
        {
            for (int j = i; j < listCount; j++)
            {
                if ((i != j) && (this.get(i).equals(this.get(j))))
                {
                    for (int k = 0; k < intarr.size(); k++) {
                        if (this.get(i).equals(intarr.get(k))) {    
                            dup = true;
                        }
                    }
                    if (!dup) {
                        intarr.add(this.get(i));
                        break;
                    }
                }
            }
        }
        return intarr;
    }

    /**
     * checks if there are more than one unique value inside the linked list
     * @return the boolean of whether or not there are multiple unique values.
     */
    public boolean multipleUnique()
    {
        for (int i = 0; i < listCount; i++)
        {
            for (int j = i; j < listCount; j++) {
                if (!this.get(j).equals(this.get(i)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Node helper class
     * @author Alan Kai
     *
     */
    public class Node {
        private Node next;
        private Object data;
        /**
         * Constructor
         * @param e object
         */
        Node(Object e) {
            data = e;
        }
    }
}