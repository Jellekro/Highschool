package sudoku;

import java.io.*;
import java.util.*;

public class Sudoku{
    
    public static void main(String[] args) throws FileNotFoundException{    
        System.out.println("If you want to end the program at any time, just type \"quit\".");
        Scanner input = new Scanner(System.in);
        boolean validMode = false;
        String invalInput = "That is an invalid input, please try again.";
        Board sudoku = new Board(); //create a dummy Board
        while(validMode==false){ //asks for a correct mode option till one is entered or quit is typed
            System.out.println("Please enter your mode (\"play\" or \"solve\")."); 
            String mode = input.nextLine().trim(); //trim in case the user enters "solve   " so that it still selects "solve"
            if(mode.equalsIgnoreCase("solve")||mode.equalsIgnoreCase("play")){ //whether the entry was play or solve it still parses
                validMode = true;
                boolean quit = false;
                while(sudoku.getValidFile()==false && quit==false){
                    System.out.println("Please enter your size (normally 3) and a filename (wo/\".txt\"). (Ex: \"3 sudoku1\")");
                    Scanner token = new Scanner(input.nextLine());
                    if(token.hasNextInt()){ //takes in the "n" (n of 3 makes normal 9 by 9 board)
                        Constants set = new Constants(token.nextInt()); //class which allows different versions of "n" to be accessed from anywhere
                        if(token.hasNext()){
                            String name = token.next(); //takes in filename
                            if(new File(name+".txt").exists()){ //as long as the file is a real name...
                                quit = true;
                                sudoku = new Board(name,set); //instantiate your board
                                System.out.println(sudoku.RCVToString());
                                System.out.println(sudoku.boardFillToString());
                                System.out.println(sudoku.RCVStatsToString());
                                if(mode.equalsIgnoreCase("play")){play(sudoku,set.getSize(2));} //if the mode entry was play then start playing the board
                                else if(mode.equalsIgnoreCase("solve")){sudoku.solve();} //solve it
                            }else{
                                System.out.println(invalInput); //if the filename was a "fake" ask for another entry
                            }
                        }else{
                            System.out.println(invalInput); //if they didn't have a "n" entered ask for another entry
                        }
                    }else if(token.hasNext() && token.next().equalsIgnoreCase("quit")){ //if the entry was quit let them exit
                        quit = true;
                    }else{
                        System.out.println(invalInput); //if they typed a word that wasn't quit then ask for another entry
                    }
                }
            }else if(mode.equalsIgnoreCase("quit")){ //if they typed quit when ask for a mode then end
                validMode = true;
                System.out.println("Thank you for using my IA app!");
            }else{ //if they just mistyped ask for another entry
                System.out.println(invalInput);
            }
        }
    }
    
    public static void play(Board sudoku, int max){
        Scanner input = new Scanner(System.in);
        /*initializing and printing some interaction speech Strings that will be used multiple times*/
        String prompt = "\nPlease enter a valid Row, Column, Value triplet (RCV). (type \"quit\" to end)";
        System.out.println(prompt);
        String reminder = "\nPlease try again. Remember, an example RCV entry is \"2,4,3\" which sets row 2, column 4's value to 3. (no spaces after the commas)";
        System.out.println("A"+reminder.substring(30,reminder.length()));
        String currBoard = "\nThe current board is:";
        System.out.println(currBoard+sudoku.toString("row"));
        String RCV = input.nextLine();
        while(!RCV.equalsIgnoreCase("quit") && sudoku.checkWin()==false){ //allows any RCV into here and only stops when the board is full or you "quit"       
            if(format(RCV, sudoku, max+1)==true){ //if the RCV player entry is in the correct format proceed
                int newVal = RCV.charAt(4)-'0'-1; //grab the numbers from the entry String
                int newRow = RCV.charAt(0)-'0'-1;
                int newCol = RCV.charAt(2)-'0'-1;
                if(sudoku.getRCV()[newVal][newRow][newCol]==CelStat.MAYBE){ //if that slot is empty they can fill it in
                    sudoku.updateExplicit((RCV.charAt(4)-'0')-1, (RCV.charAt(0)-'0')-1, (RCV.charAt(2)-'0')-1); //updates constraints/possibilities
                    sudoku.updateBoard(); //updates actual numbers on the board
                }else{ //this happens if the RCV was correct format but the slot was already full or the value didn't accord to one rule there
                    System.out.print("\nSorry, that coordinate was either filled or your value violated the one rule."+reminder);
                }
            }else{
                System.out.print(reminder); //if they get the format wrong remind them what it is
            }
            if(sudoku.checkWin()==false){ //if the board still isnt full ask for another RCV
                System.out.println(prompt+currBoard+sudoku.toString("row"));
                RCV = input.nextLine();
            }else if(sudoku.checkWin()==true && sudoku.get1Rule()==true){ //board is full and fits the one rule
                System.out.print(currBoard+sudoku.toString("row")+"\nCongratulations, you've won!"); //in other words, all constraints are fulfilled
            }
        } //says proper goodbye whether they quit or won
        System.out.println("\nThank you for using my IA app! I hope the experience was Worthit"+(char)169+".\n(press f6 to play again)");
    }
    
    public static boolean format(String RCV, Board sudoku, int max){ //checks the RCVs are entered by the user in the correct format for parsing
        if(!RCV.equalsIgnoreCase("quit")){ //as long as they didn't ask to quit...
            if(RCV.length()==5){ //check that the entry is length 5 like "2,4,3" is
                int row = RCV.charAt(0)-'0';
                int col = RCV.charAt(2)-'0';
                int val = RCV.charAt(4)-'0';
                if(RCV.charAt(1)==',' && RCV.charAt(3)==',' && row>0 && row<max && col>0 && col<max && val>0 && val<max){ //RCV has commas and valid values
                    return true; //the rest of this method is ignored if this happens
                }
                System.out.print("\nSome of your characters didn't follow the format."); //if this line is reached that means and error happened
            }
            System.out.print("\nYour entry didn't follow format."); //these two messages tell the user about their specific error
        }
        return false; //declares that specific RCV a failure and asks for another one
    }
    
}