package sudoku;

public class Cell{
    
    private Constants set;
    private int num;
    
    public Cell(Constants set, int num){ //possible future feature would allow for tally marks in cells during "play" mode
        this.set = set;
        this.num = num;
    }
    
    public int getNum(){
        return num;
    }
    
}