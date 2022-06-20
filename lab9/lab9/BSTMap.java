package lab9;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        }
        else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        }
        else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        int compare = key.compareTo(p.key);
        if (compare == 0) {
            p.value = value;
            return p;
        } else if (compare < 0) {
            p.left = putHelper(key, value, p.left);
            return p;
        } else {
            p.right = putHelper(key, value, p.right);
            return p;
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet<K> ks = new HashSet<>();
        keySetHelper(root, ks);
        return ks;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V removeVal = get(key);
        root = removeHelper(key, root);
        if (removeVal != null) {
            size -= 1;
        }
        return removeVal;
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int compare = key.compareTo(p.key);
        if (compare == 0) {
            if (p.left == null && p.right == null) {
                return null;
            } else if (p.right == null) {
                Node predecessor = findPredecessor(p);
                p.key = predecessor.key;
                p.value = predecessor.value;
                p.left = removeHelper(predecessor.key, p.left);
                return p;
            } else {
                Node successor = findSuccessor(p);
                p.key = successor.key;
                p.value = successor.value;
                p.right = removeHelper(successor.key, p.right);
                return p;
            }
        } else if (compare < 0) {
            p.left = removeHelper(key, p.left);
            return p;
        } else {
            p.right = removeHelper(key, p.right);
            return p;
        }
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V removeVal = get(key);
        if (removeVal == null) {
            return null;
        }
        if (removeVal.equals(value)) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new myBSTMapIterator();
    }

    private class myBSTMapIterator implements Iterator<K> {
        private List<K> tempList;
        private int p;

        public myBSTMapIterator() {
            this.tempList = new ArrayList<>();
            this.p = 0;
            inorder(root);
        }

        @Override
        public boolean hasNext() {
            return p < tempList.size();
        }

        @Override
        public K next() {
            K key = tempList.get(p);
            p++;
            return key;
        }

        private void inorder(Node root) {
            if (root == null) {
                return;
            }
            inorder(root.left);
            tempList.add(root.key);
            inorder(root.right);
        }
    }

    private void keySetHelper(Node p, Set<K> keys) {
        if (p == null) {
            return;
        }
        keys.add(p.key);
        keySetHelper(p.left, keys);
        keySetHelper(p.right, keys);
    }

    private Node findSuccessor(Node p) {
        p = p.right;
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    private Node findPredecessor(Node p) {
        p = p.left;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        System.out.println((b.containsKey("waterYouDoingHere")));
        b.put("waterYouDoingHere", 0);
        System.out.println((b.containsKey("waterYouDoingHere")));
        b.put("a", 1);
        b.put("b", 2);
        b.put("c", 3);
        b.put("d", 4);
        b.put("e", 5);
        System.out.println(b.keySet());
        System.out.println(b.remove("a", 1));
        System.out.println(b.keySet());
        for (String s : b) {
            System.out.println(s);
        }
    }
}
