package collegeattendancesystem;

public class Calendar {
    
    private int month;
    private int day;
    private int year;
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private boolean valTime;
    
    public Calendar(String currTime){
        if(format(currTime)){
            valTime = true;
            month = Integer.parseInt(currTime.substring(0,2));
            day = Integer.parseInt(currTime.substring(3,5));
            year = Integer.parseInt(currTime.substring(6,10));
            System.out.println(month + ", " + day + ", " + year);
        }else{valTime=false;}
    }
    
    public boolean getValTime(){
        return valTime;
    }
    
    public boolean format(String time){
        if(time.length()==10){
            if(time.charAt(2)=='/' && time.charAt(5)=='/'){
                int i = 0, s = 0;
                while(i < time.length() && time.charAt(i)>'.' && time.charAt(i)<':'){
                    if(time.charAt(i)=='/'){s++;}
                    i++;
                }
                if(i==time.length() && s==2 && !((time.charAt(0)=='0'&&time.charAt(1)=='0')||(time.charAt(3)=='0'&&time.charAt(4)=='0'))){
                    int tempDay = Integer.parseInt(time.substring(3,5));
                    int tempMonth = Integer.parseInt(time.substring(0,2));
                    int tempYear = Integer.parseInt(time.substring(6,10));
                    if(tempMonth<13 && tempDay<29){
                        return true;
                    }else{
                        if(tempMonth<8){
                            if(tempMonth%2==1 && tempDay<32){
                                return true;
                            }else if(tempMonth%2==0 && tempDay<31 && tempMonth!=2){
                                return true;
                            }else if(tempMonth==2 && tempDay<30 && ((tempYear%4==0 && tempYear%100!=0) || tempYear%400==0)){
                                return true;
                            }else{
                                System.out.println("Your month has too many days.");
                                return false;
                            }                           
                        }else if(tempMonth<13){
                            if(tempMonth%2==1 && tempDay<31){
                                return true;
                            }else if(tempMonth%2==0 && tempDay<32){
                                return true;
                            }else{
                                System.out.println("Your month has too many days.");
                                return false;
                            }                        
                        }
                        System.out.println("Your month number was invalid.");
                        return false;
                    }
                }
                System.out.println("Your entered start date was not a sufficient length or some characters were invalid.");
                return false;
            }
            System.out.println("Your entered start date that didn't have correct seperators of \"/\".");
            return false;
        }
        System.out.println("Your entered start date was not the correct length of 10.");
        return false;
    }
    
}