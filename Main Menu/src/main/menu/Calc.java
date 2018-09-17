package main.menu;

public class Calc {

    public Calc(){
        
    }
    
    public static void toBinary(int x){
        if(x<2){
            System.out.print(x);
        }else{
            toBinary(x/2);
            toBinary(x%2);
        }
    }    
    
    public static int pow(int base, int pow){
        if(pow>0)
            return pow(base,pow-1)*base;
        else if(pow==0)
            return 1;
        return 0;
    }
    
    public static int fib(int n){
        if(n>1)
            return fib(n-1)+fib(n-2);
        if(n==0)
            return 0;
        else if(n==1)
            return 1;
        return 0;
    }
    
}
