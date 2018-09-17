package main.menu;
import java.util.*;
import java.io.*;
public class SM {
    
    private ArrayList<Man> men;
    private ArrayList<Woman> women;
    
    public SM(){}
    
    public SM(String line)throws FileNotFoundException{
        men = new ArrayList<>();
        women = new ArrayList<>();
        Scanner read = new Scanner(new File(line));
        boolean blank = false, woman = false;
        while(read.hasNextLine()){
            while(!blank && read.hasNextLine()){
                String nextL = read.nextLine();
                if(nextL.isEmpty()) blank = true;
                else if(!woman) men.add(new Man(nextL));
                else if(woman) women.add(new Woman(nextL));
            }
            woman = true;
            blank = false; 
        }
        while(!stable()){
            for(Man x: men){
                int bestW = 0;
                int bestPref = 2147483647;
                int mNum = men.indexOf(x); //man's number
                for(Woman y: women){
                    int wNum = women.indexOf(y); //woman's number
                    if(y.getFree() && top(y)==mNum && x.getPrefs().indexOf(wNum)<bestPref){ //she free, he her fav, she his best option
                        bestPref = x.getPrefs().indexOf(wNum);
                        bestW = wNum;
                    }
                }
                if(bestPref!=2147483647){
                    women.get(bestW).setFree(false);
                    for(Woman y: women){
                        if(women.indexOf(y)!=bestW){
                            y.getPrefs().set(y.getPrefs().indexOf(mNum),null);
                        }
                    }
                }    
            }
        }
        for(Woman y: women){
            System.out.println("Man "+(top(y)+1)+" and Woman "+(women.indexOf(y)+1)+".");
        }
    }
    
    public int top(Woman y){
        for(Integer i: y.getPrefs()){
            if(i!=null) return i;
        }
        return -1;
    }
    
    public boolean stable(){
        for(Woman i: women){if(i.getFree())return false;}
        return true;
    }
}
