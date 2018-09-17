package sudoku;

import java.util.Arrays;

public class Box{

    private Constants set;
    private boolean oneRule;
    private int[] sorted;
    private int[][] cells;
    
    
    public Box(Constants set){
        this.set = set;
        this.sorted = new int[set.getSize(2)];
        this.cells = new int[set.getSize(1)][set.getSize(1)];
    }
    
    public void setCell(int curr, int row, int col){
        this.cells[row][col] = curr;
    }
    
    public boolean get1Rule(){
        for(int i = 0; i < set.getSize(2); i++){
            this.sorted[i] = cells[i/set.getSize(1)][i%set.getSize(1)];
        }
        Arrays.sort(sorted); //sorting the cells makes it easier to see duplicates and doesn't cost too much memory for small doses
        this.oneRule = true;
        for(int i = 1; i < set.getSize(2) && oneRule==true; i++){
            this.oneRule = (sorted[i-1]==sorted[i]  && sorted[i-1]!=0)==false;
        }
        
        return oneRule;
    }
    
    public String toString(){ //prints the n by n box
        String text = "";
        for(int row = 0; row < set.getSize(1); row++){
            for(int col = 0; col < set.getSize(1); col++){
                text += cells[row][col];
            }
            text += "\n";
        }
        return text;
    }
    
}