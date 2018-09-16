package Chess;

public class Board {

    private enum STATE{EMPTY, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING}
    private Tile[][] board;

    public Board(){

    }

    private class Tile{

        private STATE state;
        Board.ChessPiece x;

    }

}