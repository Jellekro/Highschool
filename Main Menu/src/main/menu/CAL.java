package main.menu;
import java.util.*;
public class CAL {
    
    private boolean valid;
    private int day;
    private int month;
    private int year;
    
    public CAL(){
        valid = false;
    }
    
    public CAL(String date){
        day = 0;
        month = 0;
        year = 0;
        int slash1 = date.indexOf("/");
        if(slash1==1 || slash1==2){
            String d = date.substring(0,date.indexOf("/"));
            if(new Scanner(d).hasNextInt())
                day = Integer.parseInt(d);
            date = date.substring(date.indexOf("/")+1,date.length());
            int slash2 = date.indexOf("/");
            if(slash1==1 || slash1==2){
                String m = date.substring(0,date.indexOf("/"));
                if(new Scanner(m).hasNextInt())
                    month = Integer.parseInt(m);
                String y = date.substring(date.indexOf("/")+1,date.length());
                if(new Scanner(y).hasNextInt())
                    year = Integer.parseInt(y);
                if(year>0 && day>0 && day<32 && month>0 && month<13){
                    if((month==4 || month==6 || month==9 || month==11) && day>31)
                        valid = true;
                    else if(month==2 && ((year%4==0 && year%100!=0) || year%400==0))
                        valid = true;
                    else
                        valid = true;
                }
            }
        }
    }
    
    public boolean getValid(){
        return valid;
    }
    
}
