package collegeattendancesystem;

import java.util.*;

public class CollegeAttendanceSystem {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String currTime = input.nextLine();
        Calendar cal = new Calendar(currTime);
        while(!cal.getValTime()){
            currTime = input.nextLine();
            cal = new Calendar(currTime);
        }
    }
    
}
