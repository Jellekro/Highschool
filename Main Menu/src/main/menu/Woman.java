package main.menu;
import java.util.*;
public class Woman {

    private ArrayList<Integer> prefs;
    private boolean free;
    
    public Woman(String pref){
        free = true;
        prefs = new ArrayList<>();
        Scanner line = new Scanner(pref);
        while(line.hasNextInt())
        prefs.add(line.nextInt()-1);
    }
    
    public boolean getFree(){
        return free;
    }
    
    public void setFree(boolean free){
        this.free = free;
    }
    
    public ArrayList<Integer> getPrefs(){
        return prefs;
    }
    
}
