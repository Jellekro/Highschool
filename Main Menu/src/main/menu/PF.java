package main.menu;
import java.util.*;
public class PF {
    
    Queue<Integer> set = new LinkedList<>();
    Queue<Integer> result = new LinkedList<>();
    
    public PF(int n){
        result.add(2);
        for(int i = 3; i < n; i+=2) set.add(i);
        while(set.peek()<Math.sqrt(n)){
            int curr = set.peek();
            result.add(set.remove());
            int size = set.size();
            for(int i = 0; i < size; i++){
                if(set.peek()%curr==0) set.remove();
                else set.add(set.remove());
            }
        }
        result.addAll(set);
        System.out.println("These are the prime numbers lower than the max value you set:\n"+result);
    }
    
}
