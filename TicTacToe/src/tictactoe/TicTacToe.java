package tictactoe;
import java.util.*;
public class TicTacToe {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a board size from 1-9. (Ex: \"3\" for a 3 by 3 board)");
        String size = input.nextLine();
        while(sizeFormat(size)==false && !size.equalsIgnoreCase("quit")){
            System.out.println("That was an invalid entry, please try again.");
            System.out.println("Enter a board size from 1-9. (Ex: \"3\" for a 3 by 3 board)");
            size = input.nextLine();
        }
        if(!size.equalsIgnoreCase("quit")){
            Board board = new Board(Integer.parseInt(size));
            String in = "blank";
            while(!in.equalsIgnoreCase("quit") && board.getFull()==false && board.getWin()==false){
                System.out.println(board.toString());
                System.out.println("Please enter a coordinate pair and letter. (Ex: \"3,3,X\", for row 3 and col 3 with entry X)");
                in = input.nextLine();
                while(coordFormat(in,size,board)==false && !in.equalsIgnoreCase("quit")){
                    System.out.println("That was an invalid entry, please try again.");
                    System.out.println("Please enter a coordinate pair and letter. (Ex: \"3,3,X\", for row 3 and col 3 with entry X)");
                    in = input.nextLine();
                }
                if(!in.equalsIgnoreCase("quit")){
                    int row = Integer.parseInt(""+in.charAt(0));
                    int col = Integer.parseInt(""+in.charAt(2));
                    CellStat state = CellStat._;
                    if(in.charAt(4)=='X'){
                        state = CellStat.X;
                    }else if(in.charAt(4)=='O'){
                        state = CellStat.O;
                    }
                    board.getCells()[row-1][col-1].setState(state);
                    board.checkFull();
                    board.checkWin(row-1,col-1);
                }
            }
            System.out.println(board.toString()+"\nThanks for playing!");
            if(board.getWin()==true){
                System.out.println("Congrats on your win!");
            }else if(board.getFull()==true){
                System.out.println("Sorry, that's a cat's game.");
            }
        }
    }
    
    public static boolean sizeFormat(String size){
        boolean valSize = false;
        if(size.length()==1){
            Scanner entry = new Scanner(size);
            if(entry.hasNextInt()){
                int temp = Integer.parseInt(size);
                if(temp>0 && temp <10){
                    valSize = true;
                }
            }
        }
        return valSize;
    }
    
    public static boolean coordFormat(String in, String size, Board board){
        if(in.length()==5){
            if(in.charAt(1)==',' && in.charAt(3)==',' && (in.charAt(4)=='X'||in.charAt(4)=='O')){
                int one = Integer.parseInt(""+in.charAt(0));
                int two = Integer.parseInt(""+in.charAt(2));
                int siz = Integer.parseInt(size);
                if(one>0 && two>0 && one<=siz && two<=siz){
                    if(board.getCells()[one-1][two-1].getState()==CellStat._){
                        return true;
                    }
                    System.out.println("Your entry was in a filled spot.");
                    return false;
                }
                System.out.println("Your entry had rows or columns that were out of range of the given size.");
                return false;
            }
            System.out.println("Your entry didn't have commas or had an incorrect value.");
            return false;
        }
        System.out.println("Your entry was too long.");
        return false;
    }
    
}
