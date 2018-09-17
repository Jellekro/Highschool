package unit.pkg1.project.retry;
import java.util.*;
public class Unit1ProjectRetry {

    public static void main(String[] args) {                                  
        System.out.println("Welcome to the IB Computer Science Calculator!");
        System.out.println("Enter a mode and then your expression.");
        Scanner input = new Scanner(System.in);
        double answer = Math.E;
        String result = "", express = "";
        double mode = mode(input);
        while(mode==1 || mode==2){
            System.out.print("What is your entry? (or quit)\n> ");
            express = input.nextLine();
            if(express.equalsIgnoreCase("quit")){
                mode = 0;
                System.out.println("Thank you for using my calculator!");
            }else if(mode==1){
                answer = algebra(express,answer);
                if(answer!=Math.E){
                    System.out.println(express+" = "+answer);
                }
            }else if(mode==2){
                result = calculus(express,result);
                System.out.println(result);
                mode = 0;
            }
        }    
        if(mode==3){
            statistics(input);
        }
        if(mode==4){
            RPN(input);
        }
    }
    public static double mode(Scanner input){
        double mode = 0;
        System.out.print("Choose your calculator mode (or \"quit\"/\"help\").\n> ");
        String mathSubject = input.nextLine();
        if(mathSubject.equalsIgnoreCase("help")){
            System.out.println("Your mode options are algebra, calculus, statistics, or RPN.");
            mode = mode(input);
        }else if(mathSubject.equalsIgnoreCase("quit")){
            mode = 0;
            System.out.println("Thank you for using my calculator!");
        }else if(mathSubject.equalsIgnoreCase("algebra")){
            mode = 1;
        }else if(mathSubject.equalsIgnoreCase("calculus")){
            mode = 2;
        }else if(mathSubject.equalsIgnoreCase("statistics")){
            mode = 3;
        }else if(mathSubject.equalsIgnoreCase("RPN")){
            mode = 4;
        }else{
            System.out.println("User Error 01: That is not a valid calculator mode. Please try again.");
            mode = mode(input);
        }
        return mode;
    }
    public static double algebra(String express, double answer){                        
        String oper = "";
        double x = 0;
        double y = 0;
        Scanner inputAlgebra = new Scanner(express);
        if(inputAlgebra.hasNextDouble()){                                           
            x = inputAlgebra.nextDouble();                 
            if(inputAlgebra.hasNextLine()){
                oper = inputAlgebra.next();
                if(inputAlgebra.hasNextDouble()){
                    y = inputAlgebra.nextDouble(); 
                    answer = doubleOperand(x,oper,y,answer);
                }else{
                    System.out.println("User Error 06: Your operand is not valid.");
                }
            }                                 
        }else if(inputAlgebra.hasNextLine()){
            oper = inputAlgebra.next();
            if(inputAlgebra.hasNextDouble()){
                x = inputAlgebra.nextDouble();                                    
                answer = oneOperand(oper,x,answer);
            }else{
                System.out.println("User Error 06: Your operand is not valid.");
            }
        }
        return answer;
    }                                                                         
    public static double doubleOperand(double x, String oper, double y, double answer){
        if(oper.equals("+")){
            answer = x + y;                                                
        }else if(oper.equals("-")){
            answer = x - y;
        }else if(oper.equals("*")){                                           
            answer = x * y;                                          
        }else if(oper.equals("/")){                                           
            answer = x / y;                                                
        }else if(oper.equals("^")){                                           
            answer = Math.pow(x,y);                                           
        }else if(oper.equals("%")){                       
            answer = x % y;                                           
        }else if(oper.equals("nv")){
            if(x==2){
                answer = Math.sqrt(y);
            }if(x==3){
                answer = Math.cbrt(y);
            }else{
                answer = Math.pow(y,1/x);
                int count = 0;
                while(Math.pow(answer,x)!=y && count < 10){
                    answer = Math.round(answer*Math.pow(10,15))/Math.pow(10,15);
                    count++;
                    System.out.println("wtf");
                }
            }
        }else if(oper.equalsIgnoreCase("nPr")){
            answer = calcPermutations(x,y,answer);
        }else if(oper.equalsIgnoreCase("nCr")){
            answer = calcCombinations(x,y,answer);
        }else{
            System.out.println("User Error 05: That is not a valid operator. Please try again.");
        }
        return answer;
    }       
    public static double calcPermutations(double x, double y, double answer){
        if(x>0 && x%1==0 && y>=0 && y%1==0){
                answer = calcFactorial(x) / calcFactorial(x-y);
            }else if(x<=0 || x%1!=0 || y<0 || y%1!=0){
                System.out.println("User Error 04: You cannot take the permutation of a negative or a decimal.");
            }
        return answer;
    }
    public static double calcCombinations(double x, double y, double answer){
        if(x>0 && x%1==0 && y>=0 && y%1==0){
            answer = calcFactorial(x) / (calcFactorial(x-y) * calcFactorial(y));
        }else if(x<=0 || x%1!=0 || y<0 || y%1!=0){
            System.out.println("User Error 03: You cannot take the combination of a negative or a decimal.");
        }
        return answer;
    }
    public static double oneOperand(String oper, double x, double answer){
        if(oper.equals("|")){
            answer = Math.abs(x);
        }else if(oper.equals("v")){
            answer = Math.sqrt(x);                                                      
        }else if(oper.equals("~")){                                      
            answer = Math.round(x);
        }else if(oper.equals("sin")){                                         
            answer = Math.sin(x);
        }else if(oper.equals("cos")){                                         
            answer = Math.cos(x);
        }else if(oper.equals("tan")){                                         
            answer = Math.tan(x);
        }else if(oper.equals("log")){
            answer = Math.log10(x);
        }else if(oper.equals("ln")){
            answer = Math.log(x);
        }else if(oper.equals("csc")){
            answer = 1/Math.sin(x);
        }else if(oper.equals("sec")){
            answer = 1/Math.cos(x);
        }else if(oper.equals("cot")){
            answer = Math.cos(x)/Math.sin(x);
        }else if(oper.equals("arcsin")){
            answer = Math.asin(x);
        }else if(oper.equals("arccos")){
            answer = Math.acos(x);
        }else if(oper.equals("arctan")){
            answer = Math.atan(x);
        }else if(oper.equals("arccsc")){
            answer = Math.asin(1/x);
        }else if(oper.equals("arcsec")){
            answer = Math.acos(1/x);
        }else if(oper.equals("arccot")){
            answer = Math.atan(1/x);
        }else if(oper.equals("!")){
            answer = calcFactorial(x);
        }else{
            System.out.println("User Error 05: That is not a valid operator. Please try again.");
        }
        return answer;
    }
    public static double calcFactorial(double x){
        double answer = 1;
        if(x%1==0 && x>=0){
            for(int i = 1; i<=x; i++){
                answer *= i;
            }
        }else if(x%1==0 && x<0){
            for(int i = 1; i<=(-1*x); i++){ 
                answer *= i;
            }
            answer *= -1;
        }else if(x%1!=0){
            System.out.println("User Error 07: You cannot take the factorial of a decimal.");
        }
        return answer;
    }
    public static String calculus(String express, String result){
        result = "The calculus mode is not yet implemented.";
        return result;
    }
    public static void statistics(Scanner input){
        String stat = "", express = "";
        double num, sum = 0;
        int i = 0;
        double[] data = new double[9999999];
        while(!express.equalsIgnoreCase("quit")){
            System.out.print("What is your entry? (or quit)\n> ");
            express = input.nextLine();
            Scanner check = new Scanner(express);
            if(check.hasNextDouble()){
                data[i] = check.nextDouble();
                i++;
            }else if(check.hasNextLine()){
                stat = check.nextLine();
                if(stat.equalsIgnoreCase("count")){
                    System.out.println("There are "+Arrays.copyOf(data,i).length+" pieces of data in your set.");
                }else if(stat.equalsIgnoreCase("sum")){
                    for(int x = 0; x<data.length; x++){
                        sum += x;
                    }
                    System.out.println(sum);
                }else if(stat.equalsIgnoreCase("max")){
                    
                }else if(stat.equalsIgnoreCase("min")){
                    
                }else if(stat.equalsIgnoreCase("mean")){
                    
                }else if(stat.equalsIgnoreCase("mode")){
                    
                }else if(stat.equalsIgnoreCase("median")){
                    
                }else if(stat.equalsIgnoreCase("lower quartile")){
                    
                }else if(stat.equalsIgnoreCase("upper quartile")){
                    
                }else if(stat.equalsIgnoreCase("domain")){
                    
                }else if(stat.equalsIgnoreCase("variance")){
                    
                }else if(stat.equalsIgnoreCase("standard deviation")){
                    
                }else if(!express.equalsIgnoreCase("quit")){
                    System.out.println("User Error 08: That is not a valid statistic or it is not yet implemented. Please try again.");
                }
            }
        }
        System.out.println(Arrays.toString(Arrays.copyOf(data,i)));
        System.out.println("Thank you for using my calculator!");
    }
    public static void RPN(Scanner input){
        System.out.println("uh RPN is hard m8...maybe I'll implement dat ish later...");
    }
}