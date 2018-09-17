package main.menu;
import java.util.*;
public class Man {

    private ArrayList<Integer> prefs;
    
    public Man(String pref){
        prefs = new ArrayList<>();
        Scanner line = new Scanner(pref);
        while(line.hasNextInt())
        prefs.add(line.nextInt()-1);
    }
    
    public ArrayList<Integer> getPrefs(){
        return prefs;
    }
    
}
