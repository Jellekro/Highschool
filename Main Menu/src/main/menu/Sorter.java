package main.menu;
import java.io.*;
import java.util.*;
public class Sorter {
    
    public Sorter(File file)throws FileNotFoundException,IOException{ //only sorts numbers 0 to 100,000
        BufferedReader br = new BufferedReader(new FileReader(file));
        LineNumberReader liner = new LineNumberReader(new FileReader(file));
        liner.skip(Long.MAX_VALUE);
        int size = liner.getLineNumber()+1;
        int[] list = new int[size];
        for(int i = 0; br.ready(); i++) list[i] = Integer.parseInt(br.readLine());
        int[] sort = new int[size];
        for(int n = 1; n < 100000; n*=10){
            int[] digits = {0,0,0,0,0,0,0,0,0,0};
            for(Integer i: list) digits[i%(10*n)/n]++;
            for(int i = 1; i < digits.length; i++) digits[i] += digits[i-1];
            for(int i = list.length-1; i >= 0; i--){ 
                digits[list[i]%(10*n)/n]--;
                sort[digits[list[i]%(10*n)/n]] = list[i];
            }
            System.arraycopy(sort,0,list,0,sort.length);
        }
    }  
}