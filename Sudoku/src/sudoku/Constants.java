package sudoku;
public class Constants {

    private int size;
    
    public Constants(){}
    
    public Constants(int size){
        this.size = size;
    }
    
    public int getSize(int exponent){
        return (int)Math.pow(size, exponent);
    }    
    
}