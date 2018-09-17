package morsecode;
import java.util.*;
import java.io.*;
public class MorseCode {

    public static void main(String[] args)throws FileNotFoundException{
        System.out.println("Files necessary for this translator: \"key.txt\" & \"message.txt\".");
        Scanner input = new Scanner(System.in);
        File file;
        do{
            System.out.println("Type a valid file name. It should only include \".\"s, \"-\"s, and the lowercase alphabet.");
            file = new File(input.nextLine()+".txt");
        }while(!file.exists());
        MorseTree mt = new MorseTree(new Scanner(file));
        System.out.println("\nYour translated message is: "+mt.getResult());
    }
    
}
