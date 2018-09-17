package sudoku;

import java.io.*;
import java.util.*;

public class Board{
    
    private final String warning = "\nSomething was wrong with your file. Please review it and try again.\nError Count: "; //informs user of errors
    private int errorCount; //inform them of the number of errors when with the String warning
    private boolean validFile; //this becomes false if the file is not formatted correctly
    private boolean oneRule; //this becomes false if there is number repetition within a box/cell/row
    private Constants set; //the Constants class allows us to access different variations of our base size value of "n"
    private CelStat[][][] RCV; //an exactish cover which covers what row and column constraints have been filled and organizes them by number possibilities 
    private CelStat[][] boardFill; //this array stores info about which cells are full thus accounting for the constraint that all cells must be filled
    private Box[][] boxes; //this is an array of Box objects that the file parses into
    private Row[] rows; //same as above but Row objects
    private Column[] cols; //same as above but Column objects
    private int[][] rcbPercFull; //tells how many rows/columns/boxes are full (maximum of 9 and miniumum of 0)
    private int[] rcbMaxs;
    private int[][][][][][] RCVStats; //counts the number of "NO"'s, "MAYBE"'s, and "YES"'s in each rcb
    private File sudokuFile; //this is the file that we are parsing
    
    public Board(){
        this.validFile = false; //this is for the dummy Board created earlier in "Sudoku" so that it will be an invalid file so it can enter the while loop
        this.sudokuFile = new File("32456789765435678986YoloDon'tUseThisFilename"); //dummy fileName
        if(sudokuFile.exists()){ //in case someone actually has the file named that^ it doesn't end the program wo/telling them how to fix it
            System.out.println("Please delete or rename the file named \"32456789765435678986YoloDon'tUseThisFilename\" in order to use my program.");
        }
    }
    
    public Board(String fileName, Constants set) throws FileNotFoundException{
        setVariables(fileName,set);
        if(getValidFile()){ //checks that file was creatable
            int row = checkFile(sudokuFile);
            if(row!=set.getSize(2) || get1Rule()==false){error();} //if 9 rows were not taken in or if the oneRule is false it's invalid
            else if(row==set.getSize(2)&&get1Rule()==true){
                checkPercFull();
                checkRCVStats();
            }
        }else{error();} //if the file didn't exist (this error shouldn't happen since I check file existence in "Sudoku" but it's here just in case)
    }
    
    public void solve(){
        while(checkWin()==false ){
            checkPercFull();
            checkRCVStats();
            for(int val = 0; val < RCVStats.length; val++){
                for(int shp = 0; shp < RCVStats[val].length; shp++){
                    for(int shpNum = 0; shpNum < RCVStats[val][shp].length; shpNum++){
                        for(int x = 0; x < RCVStats[val][shp][shpNum].length; x++){
                            for(int y = 0; y < RCVStats[val][shp][shpNum][x].length; y++){
                                if(RCVStats[val][shp][shpNum][x][y][2]==1){
                                    updateExplicit(val, x, y);
                                    updateBoard();
                                }
                            }
                        }
                    }
                }
            }    
        }
    }
    
    public void setVariables(String fileName, Constants set){
        this.validFile = true; //Constructing basic variables
        this.set = set;
        this.RCV = new CelStat[set.getSize(2)][set.getSize(2)][set.getSize(2)];
        this.boardFill = new CelStat[set.getSize(2)][set.getSize(2)];
        this.boxes = new Box[set.getSize(1)][set.getSize(1)];
        this.rows = new Row[set.getSize(2)];
        this.cols = new Column[set.getSize(2)];
        this.rcbPercFull = new int[3][set.getSize(2)]; //1st dimension selects row/column/box and 2nd dimension selects which rcb (1-9)
        this.rcbMaxs = new int[3];
        this.RCVStats = new int[set.getSize(2)][3][set.getSize(2)][set.getSize(2)][set.getSize(2)][3]; 
        //1st is what value, 2nd dim. is shape, 3rd is which of that shape, 4th is what row, 5th is col, 6th is a Y/N/M count
        this.sudokuFile = new File(fileName + ".txt");
        for(CelStat[][] valSet: RCV){
            for(CelStat[] row: valSet){
                Arrays.fill(row,CelStat.MAYBE);
            }
        }
        for (CelStat[] row : boardFill) {
            Arrays.fill(row,CelStat.EMPTY);
        }
        for(int i = 0; i < set.getSize(2); i++){ //instantiating all the objects in each array so that they can be referenced
            this.boxes[i/set.getSize(1)][i%set.getSize(1)] = new Box(set);
            this.rows[i] = new Row(set);
            this.cols[i] = new Column(set);
        }
    }
    
    public int checkFile(File sudokuFile) throws FileNotFoundException{
        Scanner inFile = new Scanner(sudokuFile);
        int row = 0;
        while(row<set.getSize(2) && validFile==true){ //reading through the file line by line while it has the correct format
            String line = "";
            if(inFile.hasNextLine()){
                line = inFile.nextLine();
            }else{
                while(line.length()<set.getSize(2)){
                    line += " ";
                }
            }
            if(line.length()<set.getSize(2)){ //if the line is incomplete assume the user forgot to put blank spaces on the end of that line 
                while(line.length()<set.getSize(2)){ //add spaces till the line is the correct length so it can be parsed correctly
                    line += " ";
                }
            }else if(line.length()>set.getSize(2)){ //if the line is longer than it should be, only take what "should" be there
                line = line.substring(0,set.getSize(2));
            }
            int col = 0;
            int curr = (int)line.charAt(col)-'0';
            while(((curr>-1 && curr<set.getSize(2)+1)||curr==-16) && col<line.length()){ //checks that each char is a non-zero digit and line format
                curr = (int)line.charAt(col)-'0';
                if(((curr>-1 && curr<set.getSize(2)+1)||curr==-16)){ 
                /*this secondary check is necessary in case there was a blank and then an invalid char*/
                /*at first the blank char would turn from -16 to 0 allowing the next run through to get in the while loop*/
                /*if the first run through allowed invalid char in that would cause errors so we reset the curr char value and recheck validity*/
                    if(curr==-16){ //this turns blank spaces into 0s because I represent those as 0s in my game
                        curr += 16; //I represent them as 0s because the board array is type int and would be annoying to use as type String
                    }
                    if(curr!=0){ //update the for sure constraints/possibilites fulfilled by the current char
                        updateExplicit(curr-1, row, col);
                    }
                    updateBoard();
                    col++;
                }else{error();}
            }
            if(col!=line.length()){error();} //if the while loop finished wo/col making it to 9 then there was an invalid char
            row++;
        }
        return row;
    }
    
    public void checkPercFull(){
        for(int shape = 0; shape < rcbPercFull.length; shape++){
            int max = 0, numMax = 0;
            for(int num = 0; num < rcbPercFull[shape].length; num++){
                if(rcbPercFull[shape][num]>max){
                    max = rcbPercFull[shape][num];
                    numMax = num;
                }
            }
            rcbMaxs[shape] = (numMax+1)*10 + max; //the array stores the most filled rcb, and the integer in each slot is 2 digits
        }                                         //the first digit represents which of the 9 rcb has the most, and the second digit tells how many
    }
    
    public void checkRCVStats(){
        for(int val = 0; val < RCV.length; val++){
            for(int row = 0; row < RCV[val].length; row++){
                for(int col = 0; col < RCV[val][row].length; col++){
                    int box = (set.getSize(1)*(row/set.getSize(1)))+(col/set.getSize(1));
                    switch(RCV[val][row][col]){
                        case YES: RCVStats[val][0][row][row][col][0]++;
                                  RCVStats[val][1][col][row][col][0]++;
                                  RCVStats[val][2][box][row][col][0]++;
                            break;
                        case NO: RCVStats[val][0][row][row][col][1]++;
                                 RCVStats[val][1][col][row][col][1]++;
                                 RCVStats[val][2][box][row][col][1]++;
                            break;
                        case MAYBE: RCVStats[val][0][row][row][col][2]++;
                                    RCVStats[val][1][col][row][col][2]++;
                                    RCVStats[val][2][box][row][col][2]++;
                            break;
                    }
                }
            }
        }
    }
    
    public String RCVStatsToString(){
        String text = "";
        for(int val = 0; val < RCVStats.length; val++){
            text += "\nValue "+(val+1)+"------------------------------------\n";
            for(int shape = 0; shape < RCVStats[val].length; shape++){
                text += "\nShape "+(shape+1)+"\n";
                for(int shpNum = 0; shpNum < RCVStats[val][shape].length; shpNum++){
                    for(int row = 0; row < RCVStats[val][shape][shpNum].length; row++){
                        for(int col = 0; col < RCVStats[val][shape][shpNum][row].length; col++){
                            text += "\nShape "+(shpNum+1)+"; Row "+(row+1)+"; Col "+(col+1)+": ";
                            for(int NMY = 0; NMY < RCVStats[val][shape][shpNum].length; NMY++){
                                text += RCVStats[val][shape][shpNum][row][col][NMY]+", ";
                            }
                        }
                    }
                }
            }
        }
        return text;
    }
    
    public void error(){ //triggered whenever an error happens and is a small interaction to inform the user
        this.validFile = false;
        this.errorCount++;
        System.out.println(warning+errorCount);
    }
    
    public boolean checkWin(){ //checks if the board is full of values which can only be filled in according to the one rule, thus filling all constraints
        boolean win = true;
        for(int row = 0; row < boardFill.length; row++){
            for(int col = 0; col < boardFill[row].length; col++){
                if(boardFill[row][col]==CelStat.EMPTY){
                    win = false;
                }
            }
        }
        return win;
    }
    
    public void updateExplicit(int value, int row, int col){
        int box = (set.getSize(1)*(row/set.getSize(1)))+(col/set.getSize(1));
        this.RCV[value][row][col] = CelStat.YES; //explicitly known that there is a value in this position now (1 is boolean for true here)
        this.boardFill[row][col] = CelStat.FULL; //explicitly known that this spot on the board has a number in it
        this.rcbPercFull[0][row]++;
        this.rcbPercFull[1][col]++;
        this.rcbPercFull[2][box]++;
        updateImplicit(value, row, col, box);
    }

    //calls for the surrounding information to be update (an explicit 2 implies that there are no other 2s in its row/col/box)    
    public void updateImplicit(int value, int row, int col, int box){ // checks against the "constraints"
        for(int i = 0; i < set.getSize(2); i++){
            if(i!=col){
                this.RCV[value][row][i] = CelStat.NO; //row oneRule updater
            }
            if(i!=row){
                this.RCV[value][i][col] = CelStat.NO; //column oneRule updater
            }
        }
        for(int i = 0; i < set.getSize(4); i++){ //box oneRule updater
            if(i/set.getSize(1)%set.getSize(1)+i/set.getSize(3)*set.getSize(1)==box && i!=(row*set.getSize(2)+col)){
                this.RCV[value][i/set.getSize(2)][i%set.getSize(2)] = CelStat.NO;
            }
        }
        for(int val = 0; val < set.getSize(2); val++){ //cells full updater
            for(int x = 0; x < set.getSize(2); x++){
                for(int y = 0; y < set.getSize(2); y++){
                    if(boardFill[x][y]==CelStat.FULL && RCV[val][x][y]==CelStat.MAYBE){
                        RCV[val][x][y] = CelStat.NO;
                    }
                }
            }
        }
    }
    
    public void updateBoard(){ //updates the values in the board either upon parsing or player row/column/value (RCV) entry
        for(int val = 0; val<set.getSize(2); val++){
            for(int row = 0; row<set.getSize(2); row++){
                for(int col = 0; col<set.getSize(2); col++){
                    if(RCV[val][row][col]==CelStat.YES){
                        this.boxes[row/set.getSize(1)][col/set.getSize(1)].setCell(val+1, row%set.getSize(1), col%set.getSize(1));
                        this.rows[row].setCell(val+1, col);
                        this.cols[col].setCell(val+1, row);
                    }
                }
            }
        }
    }
    
    public void nakedSingle(){
        int rowMaybe = 0;
        for(int valSet = 0; valSet < RCV.length; valSet++){
            
        }
    }
    
    public String RCVToString(){ //prints the constraints vs possibilities table with a 1 for "that RCV", -1 for "not that RCV", and 0 for "undetermined"
        String horBar = "";
        for(int i = 0; i < set.getSize(2); i++){
            horBar += "----------------";
        }
        String text = "\nV#/\\"+horBar.substring(3,horBar.length())+"\n";
        for(int value = 0; value < RCV.length; value++){
            for(int row = 0; row < RCV[value].length; row++){
                text += "|";
                for(int col = 0; col < RCV[value][row].length; col++){
                    text += "\t" + RCV[value][row][col] + "\t|";
                }
                text += "\n";
            }
            text += "V"+(value+1)+"/\\"+horBar.substring(3,horBar.length())+"\n";
        }
        return text;
    }
    
    public String boardFillToString(){ //prints the array that tells which cells are full with a "1" for full and "0" for empty
        String text = "\n";
        for(int row = 0; row < boardFill.length; row++){
            for(int col = 0; col < boardFill[row].length; col++){
                text += boardFill[row][col] + "\t";
            }
            text += "\n";
        }
        return text;
    }
    
    public boolean getValidFile(){
        return sudokuFile.exists() && validFile;
    }
    
    public boolean get1Rule(){
        return oneRule("box")&&oneRule("row")&&oneRule("col");
    }
    
    public boolean oneRule(String section){ //can check the one rule for different parts of the board
        this.oneRule = true;
        for(int i = 0; i < set.getSize(2) && oneRule==true; i++){
                switch(section){
                    case "box": this.oneRule = boxes[i/set.getSize(1)][i%set.getSize(1)].get1Rule();
                                break;
                    case "row": this.oneRule = rows[i].get1Rule();
                                break;
                    case "col": this.oneRule = cols[i].get1Rule();
                                break;
                }
        }
        return oneRule;
    }
    
    public String toString(String section){ //prints the actual board of values
        String text = "\n";
        String bigBar = "";
        String horBar = "";
        while(horBar.length()<(2*set.getSize(2))+1){
            bigBar += "#";
            horBar += "-";
        }
        bigBar = "\n"+bigBar+"\n";
        horBar = "\n#"+horBar.substring(0,horBar.length()-2)+"#";
        text += bigBar;
        for(int i = 0; i<set.getSize(2); i++){ //can switch between different parts but best formatting is for "rows"
            switch(section){
                case "box": text += boxes[i/set.getSize(1)][i%set.getSize(1)].toString();
                            break;
                case "row": if(i!=set.getSize(2)-1){
                                text += "#"+rows[i].toString()+"#";
                                text += horBar;
                            }else{
                                text += "#"+rows[i].toString()+"#";
                                text += bigBar.substring(0,bigBar.length()-1);
                            }        
                            break;
                case "col": text += cols[i].toString();
                            break;
            }
            text += "\n";
        }
        return text;
    }
    
    public CelStat[][][] getRCV(){return RCV;}
    
}