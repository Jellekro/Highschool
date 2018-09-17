package sudoku;

import java.util.Arrays;

public class Column{
    
    private Constants set;
    private boolean oneRule;
    private int[] sorted;
    private int[] cells;
    
    public Column(Constants set){
        this.set = set;
        this.sorted = new int[set.getSize(2)];
        this.cells = new int[set.getSize(2)];
    }
    
    public void setCell(int curr, int row){
        this.cells[row] = curr;
    }
    
    public boolean get1Rule(){
        System.arraycopy(cells, 0, this.sorted, 0, set.getSize(2));
        Arrays.sort(sorted); //sorting the cells makes it easier to see duplicates and doesn't cost too much memory for small doses
        this.oneRule = true;
        for(int i = 1; i < set.getSize(2) && oneRule==true; i++){
            this.oneRule = (sorted[i-1]==sorted[i]  && sorted[i-1]!=0)==false;
        }
        return oneRule;
    }
    
    public String toString(){ //prints the n^2 tall columns horizontally
        String text = "";
        for(int i = 0; i < set.getSize(2); i++){
            text += cells[i];
        }
        return text;
    }
    
}