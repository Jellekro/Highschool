package main.menu;
import java.io.*;
import java.util.*;
import java.awt.*;
public class GoL {
    
    private String inType = "file";
    private int width;
    private int height;
    private File data;
    private int[][] grid;
    private int[][] outCount;
    private DrawingPanel canvas;
    private Graphics paint;
    private Random RNG = new Random();
    private String mode;
    private Color gridCol;
    private Color liveCol;
    private Color deadCol;
    private Color[] palette = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
                               Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    
    public GoL(){}
    
    public GoL(File data)throws FileNotFoundException{
        this.data = data;
        Scanner read = new Scanner(data);
        while(read.hasNextLine()){
            int line = read.nextLine().length();
            if(line>width){
                width = line;
            }
            height++;
        }
    }
    
    public GoL(File data, int length)throws FileNotFoundException{
        this.data = data;
        this.width = length;
        this.height = length;
    }
    
    public GoL(File data, int width, int height)throws FileNotFoundException{
        this.data = data;
        this.width = width;
        this.height = height;
    }
 
    public void worldGen()throws FileNotFoundException{
        this.grid = new int[height][width];
        this.canvas = new DrawingPanel(21*width+1,21*height+1);
        this.paint = canvas.getGraphics();
        Scanner read = new Scanner(data);
        for(int row = 0; row < height && read.hasNextLine(); row++){
            String line = read.nextLine();
            for(int col = 0; col < width && col < line.length(); col++){
                if(line.charAt(col)=='1'){
                   grid[row][col] = 1; 
                }
            }
        }
        if(mode.equals("color")){
            gridCol = palette[RNG.nextInt(13)];
            liveCol = palette[RNG.nextInt(13)];
            while(gridCol==liveCol){liveCol = palette[RNG.nextInt(13)];}
            deadCol = palette[RNG.nextInt(13)];
            while(gridCol==deadCol || liveCol==deadCol){deadCol = palette[RNG.nextInt(13)];}
        }
        drawGen();
        evolve();
    }
    
    public void drawGen(){
        paint.setColor(gridCol);
        for(int row = 0; row <= height; row++){
            paint.drawLine(0,21*row,21*width+1,21*row);
        }
        for(int col = 0; col <= width; col++){
            paint.drawLine(21*col,0,21*col,21*height+1);
        }
    }
    
    public void evolve(){
        Scanner read = new Scanner(System.in);
        String command = "Type \"quit\" to exit or press any other key to continue evolution.";
        while(repeatAct(read,command)){
            countOut();
            nextGen();
        }
        System.exit(0);
    }
    
    public boolean repeatAct(Scanner read, String command){
        switch(mode){
            case "default":
                liveCol = Color.BLACK;
                deadCol = Color.WHITE;
                break;
            case "disco":
                gridCol = palette[RNG.nextInt(13)];
                liveCol = palette[RNG.nextInt(13)];
                while(gridCol==liveCol){liveCol = palette[RNG.nextInt(13)];}
                deadCol = palette[RNG.nextInt(13)];
                while(gridCol==deadCol || liveCol==deadCol){deadCol = palette[RNG.nextInt(13)];}
                drawGen();
                break;
            case "rave":
                gridCol = palette[RNG.nextInt(13)];
                deadCol = palette[RNG.nextInt(13)];
                while(gridCol==deadCol){deadCol = palette[RNG.nextInt(13)];}
                drawGen();
                drawNextRave();
                break;
        }
        if(!mode.equals("rave")){drawNext();}
        System.out.println(command);
        return !read.nextLine().equalsIgnoreCase("quit");
    }
    
    public void countOut(){
        this.outCount = new int[height][width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                if(row!=0){
                    if(grid[row-1][col]==1){outCount[row][col]++;}
                    if(col!=0){
                        if(grid[row-1][col-1]==1){outCount[row][col]++;}
                    }
                }
                if(col!=0){
                    if(grid[row][col-1]==1){outCount[row][col]++;}
                    if(row!=height-1){
                        if(grid[row+1][col-1]==1){outCount[row][col]++;}
                    }
                }
                if(row!=height-1){
                    if(grid[row+1][col]==1){outCount[row][col]++;}
                    if(col!=width-1){
                        if(grid[row+1][col+1]==1){outCount[row][col]++;}
                    }
                }
                if(col!=width-1){
                    if(grid[row][col+1]==1){outCount[row][col]++;}
                    if(row!=0){
                        if(grid[row-1][col+1]==1){outCount[row][col]++;}
                    }
                }
            }
        }
    }
    
    public void nextGen(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                if(grid[row][col]==1){
                    if(outCount[row][col]<2){grid[row][col]=0;}
                    else if(outCount[row][col]>3){grid[row][col]=0;}
                }else if(grid[row][col]==0){
                    if(outCount[row][col]==3){grid[row][col]=1;}
                }
            }
        }
    }
    
    public void drawNext(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                switch(grid[row][col]){
                    case 1: paint.setColor(liveCol);
                            paint.fillRect(21*col+1,21*row+1,20,20);
                            break;
                    case 0: paint.setColor(deadCol);
                            paint.fillRect(21*col+1,21*row+1,20,20);
                            break;
                }
            }
        }
    }
    
    public void drawNextRave(){
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                switch(grid[row][col]){
                    case 1: paint.setColor(palette[RNG.nextInt(13)]);
                            paint.fillRect(21*col+1,21*row+1,20,20);
                            break;
                    case 0: paint.setColor(deadCol);
                            paint.fillRect(21*col+1,21*row+1,20,20);
                            break;
                }
            }
        }
    }
    
    public boolean fileScan()throws FileNotFoundException{
        Scanner read = new Scanner(data);
        for(int row = 0; row < height && read.hasNextLine(); row++){
            String line = read.nextLine();
            for(int col = 0; col < width && col < line.length(); col++){
                char x = line.charAt(col);
                if(x!='0' && x!='1' && x!=' '){
                    System.out.println("Invalid Entry: your file had invalid character(s).");
                    return false;
                }
            }
        }
        Scanner appMode = new Scanner(System.in);
        System.out.print("Please type a valid display mode. ");
        boolean valMode = false;
        while(!valMode){
            System.out.println("Your options are: default, color, disco, and rave. Experiment to see what they do!");
            String setting = appMode.nextLine();
            Scanner token = new Scanner(setting);
            if(setting.equalsIgnoreCase("quit")){
                valMode = true;
            }else if(setting.equals("default") || setting.equals("color") || setting.equals("disco") || setting.equals("rave")){
                mode = setting;
                worldGen();
                valMode = true;
            }else{
                System.out.println("That was not a valid display mode, please try again.");
            }
        }
        return true;
    }
}