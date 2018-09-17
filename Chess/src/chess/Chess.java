package chess;

import java.util.*;

public class Chess {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<String> pieces = new ArrayList<>();
        pieces.add("pawn");
        pieces.add("knight");
        pieces.add("bishop");
        pieces.add("rook");
        pieces.add("queen");
        pieces.add("king");
        boolean checkmate = false;
        String entry = "", move = "white";
        while(!checkmate && !entry.equalsIgnoreCase("quit")){
            String piece = "", pos = "";
            System.out.println("It is "+move+"'s turn to move. Enter a piece and a position to move your piece to.");
            boolean valIn = false;
            while(!valIn){
                entry = input.nextLine();
                Scanner read = new Scanner(entry);
                if(read.hasNext()){
                    piece = read.next();
                    if(pieces.contains(piece)){
                        if(read.hasNext()){
                            pos = read.next();
                            if(pos.length()==2){
                                if(pos.charAt(0)>='A' && pos.charAt(0)<='H'){
                                    if(pos.charAt(1)>=1 && pos.charAt(1)<=8){
                                        if(!read.hasNext()){
                                            valIn = true;
                                        }
                                    }
                                }
                            }
                        }                            
                    }
                } 
            }
            
        }
    }
    
}
