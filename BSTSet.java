/**
 * This class is an implementation of a Binary Search tree, using nodes as trees.
 * basic
 * toString enchancement
 * remove implemented
 *
 * @author Samuel No
 * @version 1/14/92 AD325 Assignment #2
 */
public class BSTSet implements StringSet_Check
{
    private Node root;
    /**
     * Constructor for objects of class BSTSet
     */
    public BSTSet()
    {
        root =  null;
    }

    /**
     * Adds the specified element to this set if it is 
     * not already present. More formally, adds the 
     * specified element s to this set if the set contains 
     * no element s2 such that s.equals(s2). If this 
     * set already contains the element, the call leaves 
     * the set unchanged and returns false. This ensures 
     * that the set never contains duplicate elements.
     * 
     * @param s element to be added to this set
     * @return true if this set did not already contain 
     * the specified element
     * @throws IllegalArgumentException if the specified
     * element is null
     */
    public boolean add(String s){
        if(s == null){
            throw new IllegalArgumentException("Null was entered as a parameter");
        }
        if(root == null){
            root = new Node(s);
            return true;
        }
        add(s, root);
        return false;
    }

    //method that uses recursion to add to the tree
    private boolean add(String s, Node n){
        if(n == null){
            n = new Node(s);
            return true;
        }
        if(s.compareTo(n.value) < 0){
            if(n.left == null){
                n.left = new Node(s);
                return true;
            }
            return add(s, n.left);
        }
        if(s.compareTo(n.value) > 0){
            if(n.right == null){
                n.right = new Node(s);
                return true;
            }
            return add(s, n.right);
        }
        return false;
    }


    /**
     * Removes the specified element from this set if 
     * it is present. More formally, this removes an 
     * element e such that s.equals(e), if this set 
     * contains such an element. Returns true if this 
     * set contained the element (or equivalently, if 
     * this set changed as a result of the call). 
     * (This set will not contain the element once the 
     * call returns.)
     *
     * @param s the String to be removed from this set, 
     * if present
     * @return true if this set contained the specified 
     * element
     * @throws IllegalArgumentException if the specified
     * element is null
     */
    public boolean remove(String s){
        if(s == null){
            throw new IllegalArgumentException("Can not pass null argument");
        }
        int cmp = s.compareTo(root.value);
        //cases to remove the root
        if(cmp == 0 && root.left == null && root.right == null){
            root = null;
            return false;
        } else if(cmp == 0 && (root.left == null && root.right != null)){
            //case for 1 open left
            root =  root.right;
            return true;
        } else if(cmp == 0 && (root.right == null && root.left != null)){
            //case for 1 open right
            root =  root.left;
            return true;
        } else if(cmp == 0 && (root.left != null && root.right != null)){
            Node temp = root;
            root = getMin(temp.right);
            root.right = deleteMin(temp.right);
            root.left = temp.left;
            return true;
        }
        //need to check the amount of left and right
        if(s.compareTo(root.value) < 0) {
            return remove(s, root.left, root);
        } else {
            return remove(s, root.right, root);
        }
    }

    //method that gets the parent and current node to remove using recursion
    private boolean remove(String s, Node current, Node parent){
        if(current == null){
            return false;
        }
        int cmp = s.compareTo(current.value);
        if(cmp == 0 && current.left == null && current.right == null){
            //if both children are empty
            //check if it's left or right
            if(parent.value.compareTo(current.value) > 0){
                parent.left = null;
            } else {
                parent.right = null;
            }
            return true;
        } else if(cmp == 0 && (current.left == null && current.right != null)){
            //case for 1 open left
            parent.right =  current.right;
            return true;
        } else if(cmp == 0 && (current.right == null && current.left != null)){
            //case for 1 open right
            parent.left =  current.left;
            return true;
        } else if(cmp == 0 && (current.left != null && current.right != null)){
            //has to change the parent node depending if the parent is greater
            //or less than the node being deleted
            if(parent.value.compareTo(current.value) > 0) {
                Node temp = current;
                parent.left = getMin(temp.right);
                parent.left.right = deleteMin(temp.right);
                parent.left.left = temp.left;
                return true;
            } else {
                Node temp = current;
                parent.right = getMin(temp.right);
                parent.right.right = deleteMin(temp.right);
                parent.right.left = temp.left;
                return true;
            }
        }
        parent = current;
        //go left or right with the current node as the parent
        if(cmp < 0){
             return remove(s, current.left, parent);
         } else if(cmp > 0){
             return remove(s, current.right, parent);
        }
        return false;
    }

    //find the max Node in order to remove a node with two children (from right of root)
    private Node getMin(Node n){
        if(n.left == null){
            return n;
        }
        return getMin(n.left);
    }

    //method to delete the min node
    private Node deleteMin(Node n) {
        if(n.left == null){
            return n.right;
        }
        n.left = deleteMin(n.left);
        return n;
    }

    /**
     * Removes all of the elements from this set. The set 
     * will be empty after this call returns.
     */
    public void clear(){
        root = null;
    }

    /**
     * Returns true if this set contains the 
     * specified element. More formally, returns true 
     * if and only if this set contains an element e 
     * such that s.equals(e).
     * 
     * @param s element whose presence in this set is 
     * to be tested
     * @return true if this set contains the specified 
     * element
     * @throws IllegalArgumentException if the specified
     * element is null
     */
    public boolean contains(String s){
        if(s == null){
            throw new IllegalArgumentException("Null was entered as a parameter");
        }
        if(root == null){
            return false;
        }
        if (s.compareTo(root.value) > 0 ) {
            return contains(s, root.right);
        } else {
            return contains(s, root.left);
        }
    }

    //method that uses recursion in order to go through to figure out
    //if it reaches the node itself
    private boolean contains(String s, Node n){
        if(n == null){
            return false;
        }
        if(s.compareTo(n.value) == 0){
            return true;
        }
        if(s.compareTo(n.value) > 0){
            return contains(s, n.right);
        } else {
            return contains(s, n.left);
        }
    }

    /**
     * Returns true if this set contains no elements.
     * 
     * @return true if this set contains no elements
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * Returns a String containing all of the elements 
     * in this set ordered using an in-order traversal
     * of the underlying tree. The values are separated 
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     * 
     * @return A string representation of the elements of
     * the set ordered by an in-order traversal of the 
     * underlying tree.
     */
    public String toStringInOrder(){
        if(root == null){
            return "[]";
        }
        String tree = "[";
        return tree + toStringInOrder(root) + "]";
    }

    private String toStringInOrder(Node n){
        if(n == null){
            return "";
        } else if(n.left != null && n.right == null){
            return toStringInOrder(n.left) + " " +  n.value;
        } else if(n.left == null && n.right != null){
            return n.value + " " + toStringInOrder(n.right);
        } else if(n.left == null && n.right == null){
            return n.value;
        } else {
            return toStringInOrder(n.left) + " " + n.value + " " + toStringInOrder(n.right);
        }    
    }

    /**
     * Returns a String containing all of the elements 
     * in this set ordered using a pre-order traversal
     * of the underlying tree. The values are separated 
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     * 
     * @return A string representation of the elements of
     * the set ordered by a pre-order traversal of the 
     * underlying tree.
     */
    public String toStringPreOrder(){
        if(root == null){
            return "[]";
        }
        String tree = "[";
        return tree + toStringPreOrder(root) + "]";
    }

    private String toStringPreOrder(Node n){
        if(n == null){
            return "";
        } else if(n.left != null && n.right == null){
            return n.value + " " + toStringPreOrder(n.left);
        } else if(n.left == null && n.right != null){
            return n.value + " " + toStringPreOrder(n.right);
        } else if(n.left == null && n.right == null){
            return n.value;
        } else {
            return n.value + " " + toStringPreOrder(n.left) + " " + toStringPreOrder(n.right);
        }

    }

    /**
     * Returns a String containing all of the elements 
     * in this set ordered using a post-order traversal
     * of the underlying tree. The values are separated 
     * by spaces, ' '. Ideally, there should not be a
     * final space separator in the returned string.
     * 
     * @return A string representation of the elements of
     * the set ordered by a post-order traversal of the 
     * underlying tree.
     * 
     */
    public String toStringPostOrder(){
        if(root == null){
            return "[]";
        }
        String tree = "[";
        return tree + toStringPostOrder(root) + "]";
    }

    //how to check if only 1 branch correctly
    //doesn't read 1 branch correctly
    private String toStringPostOrder(Node n){
        if(n == null){
            return "";
        } else if(n.left != null && n.right == null){
            return toStringPostOrder(n.left) + " " +  n.value;
        } else if(n.left == null && n.right != null){
            return toStringPostOrder(n.right) + " " + n.value;
        } else if(n.left == null && n.right == null){
            return n.value;
        } else {
            return toStringPostOrder(n.left) + " " + toStringPostOrder(n.right) + " " + n.value;
        }
    }

    /**
     * Returns the number of elements in this set (its 
     * cardinality).
     * 
     * @return the number of elements in this set (its 
     * cardinality)
     */
    public int size(){
        return size(root);
    }

    private int size(Node n){
        if(n == null){
            return 0;
        } else {
            return 1 + size(n.left) + size(n.right);
        }
    }

    public static void main(String[] args){
        BSTSet a = new BSTSet();
        a.add("p");
        a.add("po");
        a.add("poo");
        a.add("pooo");
        a.add("poooo");
        //poooo pooo poo po p
        System.out.println(a.toStringPostOrder());
        System.out.println(a.toStringPreOrder());
        System.out.println(a.toStringInOrder());

        BSTSet b = new BSTSet();
        b.add("3");
        b.add("2");
        b.add("4");
        b.add("5");
        b.add("1");
        //should not be added
        b.add("3");
        //should remove 5, causing print to not have 5 in it
        b.remove("5");
        //1 2 5 4 3
        System.out.println(b.toStringPostOrder());
        //3 2 1 4 5
        System.out.println(b.toStringPreOrder());
        //1 2 3 4 5
        System.out.println(b.toStringInOrder());

        BSTSet c = new BSTSet();
        c.add("poooo");
        c.add("pooo");
        c.add("poo");        
        c.add("po");
        c.add("p");

        System.out.println(c.toStringPostOrder());
        System.out.println(c.toStringPreOrder());
        System.out.println(c.toStringInOrder());

        BSTSet d = new BSTSet();
        d.add("11");
        d.add("7");
        d.add("8");        
        d.add("3");
        d.add("2");
        d.add("1");
        d.add("16");
        d.add("15");        
        d.add("13");
        d.add("18");
        d.add("22");

        System.out.println(d.toStringPostOrder());
        System.out.println(d.toStringPreOrder());
        System.out.println(d.toStringInOrder());

        BSTSet e = new BSTSet();
        e.add("e");
        e.add("c");
        e.add("d");        
        e.add("b");
        e.add("a");
        e.add("h");
        e.add("g");
        e.add("f");        
        e.add("i");
        e.add("j");

        //a b d c f g j i h e
        System.out.println(e.toStringPostOrder());
        //e c b a d h g f i j
        System.out.println(e.toStringPreOrder());
        //a b c d e f g h i j
        System.out.println(e.toStringInOrder());
        //print new
        System.out.println(e.contains("c"));

        e.remove("c");
        //a b d c f g i h e
        System.out.println(e.toStringPostOrder());
        //e c b a d h g f i
        System.out.println(e.toStringPreOrder());
        //a b c d e f g h i
        System.out.println(e.toStringInOrder());
        //print should print false
        System.out.println(e.contains("c"));

        BSTSet f = new BSTSet();
        f.add("a");
        System.out.println(f.toStringInOrder());
        f.remove("a");
        //should print nothing
        System.out.println(f.toStringInOrder());

        BSTSet g = new BSTSet();
        g.add("15");
        g.add("13");
        g.add("11");
        g.add("21");
        g.add("17");
        g.add("18");
        g.add("35");
        g.add("24");
        g.add("27");
        g.add("32");
        //11, 13, 15, 17, 18, 21, 24, 27, 32, 35
        System.out.println(g.toStringInOrder());
        g.remove("18");
        //11, 13, 15, 17, 21, 24 27, 32 35
        System.out.println(g.toStringInOrder());
        g.remove("27");
        //11, 13, 15, 17, 21 24 32, 35
        System.out.println(g.toStringInOrder());
        g.remove("21");
        //11, 13, 15, 17, 24, 32, 35
        System.out.println(g.toStringInOrder());
    }
}
/**
 * This class is a node of the tree
 */
class Node {
    String value;
    Node left, right;
    public Node(String s) {
        value = s;
        left = right = null;
    }
}