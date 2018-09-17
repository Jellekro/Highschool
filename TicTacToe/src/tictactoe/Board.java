package tictactoe;
public class Board {

    private int size;
    private Cell[][] cells;
    private boolean full;
    private boolean win;
    
    public Board(int size){
        this.size = size;
        cells = new Cell[size][size];
        for(int row = 0; row < cells.length; row++){
            for(int col = 0; col < cells[row].length; col++){
                cells[row][col] = new Cell(CellStat._);
            }
        }
        full = false;
        win = false;
    }
    
    public void checkWin(int row, int col){
        boolean rowWin = true, colWin = true, dagWin = true;
        for(int y = 1; y < cells[row].length; y++){
            if(cells[row][y-1].getState()!=cells[row][y].getState() && !(cells[row][y-1].getState()==CellStat._ && cells[row][y].getState()==CellStat._)){
                rowWin = false;
            }
        }
        for(int x = 1; x < cells.length; x++){
            if(cells[x-1][col].getState()!=cells[x][col].getState() && !(cells[x-1][col].getState()==CellStat._ && cells[x][col].getState()==CellStat._)){
                colWin = false;
            }
        }
        for(int dag = 1; dag < cells.length; dag++){
            if(cells[dag-1][dag-1].getState()!=cells[dag][dag].getState() || (cells[dag-1][dag-1].getState()==CellStat._ && cells[dag][dag].getState()==CellStat._)){
                dagWin = false;
            }
        }
        if(rowWin==true || colWin==true || dagWin==true){
            win = true;
        }
    }
    
    public void checkFull(){
        full = true;
        for(int row = 0; row < cells.length; row++){
            for(int col = 0; col < cells[row].length; col++){
                if(cells[row][col].getState()==CellStat._){
                    full = false;
                }
            }
        }
    }
    
    @Override
    public String toString(){
        String text = "\n";
        String temp = "-";
        for(int i = 0; i <= size*8; i++){
            temp += "--";
        }
        temp = temp.substring(0,temp.length()-3);
        for(int row = 0; row < cells.length; row++){
            for(int col = 0; col < cells[row].length; col++){
                text += "\t"+cells[row][col].getState()+"\t|";
            }
            text = text.substring(0,text.length()-1);
            text += "\n"+temp+"\n";
        }
        return text.substring(0,text.length()-temp.length()-1);
    }
    
    public Cell[][] getCells(){
        return cells;
    }
    
    public boolean getFull(){
        return full;
    }
    
    public boolean getWin(){
        return win;
    }
    
}
