package main.menu;
import java.util.*;
import java.awt.*;
public class ToH {
    
    private int lay;
    private String order;
    private ArrayList<Stack<Integer>> towers = new ArrayList<>();
    private ArrayList<Integer> tops = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();
    
    public ToH(int lay){
        this.lay = lay;
        for(int i = 0; i < 3; i++){
            towers.add(new Stack<>());
            tops.add(0);
            scores.add(0);
        }
        for(int i = lay; i > 0; i--) towers.get(0).push(i);
        print();
        towers.get(towers.get(0).size()%2+1).push(towers.get(0).pop());
        print();
        order = order(1,"").substring(1);
        move();
    }
    
    public void move(){
        for(int i = 0; i < order.length(); i++){
            int curr = Integer.parseInt(""+order.charAt(i)), currTow, max=-1;
            for(Stack<Integer> tow: towers){
                if(tow.empty()) tops.set(towers.indexOf(tow),0);
                else tops.set(towers.indexOf(tow),tow.peek());
            }
            for(Integer j: tops){
                if(j==0) scores.set(tops.indexOf(j),1);
                else if(curr>j) scores.set(tops.indexOf(j),0);
                else if((j+curr)%2==1) scores.set(tops.indexOf(j),2);
                else if((j+curr)%2==0) scores.set(tops.indexOf(j),0);
            }
            for(Integer k: scores) if(k>max) max = k;
            towers.get(scores.indexOf(max)).push(towers.get(tops.indexOf(curr)).pop());
            print();
            System.out.println("Press any key to continue.");
            if(new Scanner(System.in).nextLine().equalsIgnoreCase("quit")) break;
        }
        System.exit(0);
    }
    
    public void print(){for(int i = 0; i < 3; i++) System.out.println(Arrays.toString(towers.get(i).toArray()));}
    
    public String order(int count, String ord){
        if(ord.length()+1==Math.pow(2,lay)) return ord;
        else return order(count+1,ord+count+ord);
    }       
}