package tictactoe;
public class Cell {
    
    private CellStat state;
    
    public Cell(CellStat state){
        this.state = state;
    }
    
    public void setState(CellStat state){
        this.state = state;
    }
    
    public CellStat getState(){
        return state;
    }
    
}
