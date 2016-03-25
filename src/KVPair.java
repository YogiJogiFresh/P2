/**
 * 
 * @author Given
 * @version 1.0
 * @param <K> Key
 * @param <E> Element
 */
// KVPair class definition
public class KVPair<K extends Comparable<K>, E>
        implements Comparable<KVPair<K, E>> {
    private K theKey;
    private E theVal;

    /**
     * Constructor
     * 
     * @param k key
     * @param v value
     */
    public KVPair(K k, E v) {
        theKey = k;
        theVal = v;
    }

    /**
     * Compare method
     * 
     * @param it KVPair
     * @return returns comparison result
     */
    public int compareTo(KVPair<K, E> it) {
        return theKey.compareTo(it.key());
    }

    /**
     * Compares against the key
     * 
     * @param it key
     * @return returns compared
     */
    public int compareTo(K it) {
        return theKey.compareTo(it);
    }

    /**
     * Get Key
     * 
     * @return the Key
     */
    public K key() {
        return theKey;
    }

    /**
     * Get value
     * 
     * @return thevalue
     */
    public E value() {
        return theVal;
    }

    /**
     * To string
     * 
     * @return key and value to string
     */
    public String toString() {
        return theKey.toString() + ", " + theVal.toString();
    }
}
/* *** ODSAendTag: KVPair *** */