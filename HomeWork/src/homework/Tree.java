package homework;
import java.util.*;
public class Tree {
    
    Leaf root;
    boolean isBST;
    Random rng = new Random();
    
    public Tree(){} //instantiates a null tree
    
    public Tree(Scanner read){ //instantiates a tree of a file of numbers in order
        isBST = true;
        while(read.hasNextInt()) root = add(root,read.nextInt());
    }
    
    public void add(int val){
        add(root,val);
    }
    
    public Leaf add(Leaf curr, int val){ //adds new numbers in order
        if(curr==null) curr = new Leaf(val);
        else if(val<curr.data) curr.left = add(curr.left,val);
        else if(val>curr.data) curr.right = add(curr.right,val);
        return curr;
    }
    
    public Tree(int height){ //instantiates a tree of height number of layers filled with random numbers
        if(height<1) throw new IllegalArgumentException("the tree's height must be positive.");
        root = grow(height-1);
    }
    
    @Override
    public String toString(){
        return swPrint(root,"");
    }
    
    private String swPrint(Leaf curr, String indent){
        if(curr!=null) return swPrint(curr.right,indent+"    ")+indent+curr.data+"\n"+swPrint(curr.left,indent+"    ");
        return "";
    }
    
    private Leaf grow(int height){ //adds the random numbers
        if(height<1) return new Leaf(rng.nextInt(100));
        return new Leaf(rng.nextInt(100), grow(height-1), grow(height-1));
    }
    
    public boolean contains(int val){
        if(isBST) return contains(root,val);
        throw new IllegalArgumentException("You can only search for a number in a binary sorted tree.");
    }
    
    private boolean contains(Leaf curr, int val){ //checks if the value is in the tree
        if(curr==null) return false;
        else if(val<curr.data) return contains(curr.left,val);
        else if(val>curr.data) return contains(curr.right,val);
        return true;
    }
    
    public int getMin(){
        if(root==null) throw new NoSuchElementException("The BST is null, so there is no minimum.");
        return getMin(root);
    }
    
    private int getMin(Leaf curr){
        if(curr.left==null) return curr.data;
        return getMin(curr.left);
    }
    
    public int getMax(){
        if(root==null) throw new NoSuchElementException("The BST is null, so there is no maximum.");
        return getMax(root);
    }
    
    private int getMax(Leaf curr){
        if(curr.right==null) return curr.data;
        return getMax(curr.right);
    }
    
    public Integer remove(int val){
        root = remove(root,val);
        return val;
    }
    
    private Leaf remove(Leaf curr, int val){
        if(curr==null) return null;
        else if(val<curr.data) curr.left = remove(curr.left,val);
        else if(val>curr.data) curr.right = remove(curr.right,val);
        else{
            if(curr.left==null && curr.right==null) curr = null;
            else if(curr.right==null) return curr.left;
            else if(curr.left==null) return curr.right;
            else{
                curr.data = remove(getMin(curr.right));
                curr.right = remove(curr.right,curr.data);
            }
        }
        return curr;
    }
    
    public int size(){
        return size(root);
    }
    
    private int size(Leaf curr){
        if(curr!=null) return 1+size(curr.left)+size(curr.right);
        return 0;
    }
    
    public int countMultiples(int num){
        if(num==0) throw new IllegalArgumentException();
        return countMultiples(root,num);
    }
    
    private int countMultiples(Leaf curr, int num){
        if(curr!=null){
            int result = countMultiples(curr.left,num) + countMultiples(curr.right,num);
            if(curr.data%num==0) result++;
            return result;
        }
        return 0;
    }
    
    public String travPrint(String order){
        if(root==null || order==null) throw new NullPointerException("Check for null inputs.");
        else if(order.equals("pre")) return timberPrO(root);
        else if(order.equals("in")) return timberInO(root);
        else if(order.equals("post")) return timberPoO(root);
        throw new IllegalArgumentException("Invalid traversal type.");
    }
    
    private String timberPrO(Leaf curr){
        if(curr!=null) return " "+curr.data+timberPrO(curr.left)+timberPrO(curr.right);
        return "";
    }
    
    private String timberPoO(Leaf curr){
        if(curr!=null) return timberPoO(curr.left)+timberPoO(curr.right)+" "+curr.data;
        return "";
    }
    
    private String timberInO(Leaf curr){ //makes a String representation of the tree from in-order traversal
        if(curr!=null) return timberInO(curr.left)+" "+curr.data+timberInO(curr.right);
        return "";
    }
    
    private class Leaf implements Comparable<Leaf> {
    
        int data;
        Leaf left;
        Leaf right;
        
        public Leaf(int data){
            this.data = data;
        }
        
        public Leaf(int data, Leaf left, Leaf right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public int compareTo(Leaf other){
            return data-other.data;
        }
    }
}
