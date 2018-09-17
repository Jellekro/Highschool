package chess;

public class ChessPiece {
    
    protected STATE state;
    protected int xpos;
    protected int ypos;
    
    public ChessPiece(int xpos, int ypos){
        this.state = STATE.EMPTY;
        this.xpos = xpos;
        this.ypos = ypos;
    }
    
    public class Pawn extends ChessPiece {
        
        public Pawn(int xpos, int ypos){
            super(xpos, ypos);
            this.state = STATE.PAWN;
        }
        
        private void move(int xpos, int ypos){
            
        }
        
    }
    
    private class Knight extends ChessPiece {
        
        public Knight(int xpos, int ypos){
            super(xpos, ypos);
            this.state = STATE.KNIGHT;
        }
        
    }
    
    private class Bishop extends ChessPiece {
        
        public Bishop(int xpos, int ypos){
            super(xpos, ypos);
            this.state = STATE.BISHOP;
        }
        
    }
    
    private class Rook extends ChessPiece {
        
        public Rook(int xpos, int ypos){
            super(xpos, ypos);
            this.state = STATE.ROOK;
        }
        
    }
    
    private class Queen extends ChessPiece {
        
        public Queen(int xpos, int ypos){
            super(xpos, ypos);
            this.state = STATE.QUEEN;
        }
        
    }
    
    private class King extends ChessPiece {
        
        public King(int xpos, int ypos){
            super(xpos, ypos);
            this.state = STATE.KING;
        }
        
    }
    
}
