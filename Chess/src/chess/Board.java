package chess;

public class Board {

    private ChessPiece[][] board;
    
    public Board(){
        this.board = new ChessPiece[8][8];
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                board[i][j] = new ChessPiece(i,j);
            }
        }
        for(int j = 0; j<board.length; j++){
            board[1][j] = new ChessPiece.Pawn(1,j);
            board[6][j] = new Pawn(1,j); 
        }
    }
    
    public void move(String piece, String pos){
        
    }
    
}
