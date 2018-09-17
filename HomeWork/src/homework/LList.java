package homework;
import java.util.*;
public class LList {
    
    LNode front;
    int size;
    
    public LList(){
        front = null;
    } 
    
    public LList(Scanner read){
        while(read.hasNext()) add(read.next());
    }
    
    public void add(String name){
        add(new LNode(name));
    }
    
    private void add(LNode next){
        if(front==null) front = next;
        else{
            LNode temp = front;
            while(temp.next!=null) temp = temp.next;
            temp.next = next;
        }
        size++;
    }
    
    public boolean remove(String name){
        return remove(front,name);
    }
    
    private boolean remove(LNode temp, String name){
        do{
            if(temp.next.data.equals(name)){
                temp.next = temp.next.next;
                size--;
                front = temp;
                return true;
            }
            temp = temp.next;
        }while(temp!=front);
        return false;
    }
    
    @Override
    public String toString(){
        String text = "";
        LNode temp = front;
        for(int i = 0; i < size; i++){
            text += temp.data+" ";
            temp = temp.next;
        }
        return text;
    }
    
    public void rotate(){
        
    }
    
    private class LNode {
        String data;
        LNode next;

        public LNode(String data){
            this.data = data;
        }

        public LNode(String data, LNode next){
            this.data = data;
            this.next = next;
        }
    }
}