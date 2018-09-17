package main.menu;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Mazer {

    private HashSet<Point> prevLocs = new HashSet<>();
    private ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
    private Graphics paint = new DrawingPanel(570,570).getGraphics();
    private Stack<Point> locs = new Stack<>();
    private Point end = new Point();
    
    public Mazer(Scanner read){ //scanning valid maze file
        boolean valInfo = false;
        while(!valInfo){
            System.out.println("Enter the x and y coordinate of your end point in the maze.");
            Scanner thru = new Scanner(new Scanner(System.in).nextLine()); //type "1 18" for the file "maze"
            if(thru.hasNextInt()){
                int x = thru.nextInt();
                if(thru.hasNextInt()){
                    end.setLocation(x-1,thru.nextInt()-1);
                    valInfo = true;
                }else System.out.println("Invalid input, please try again.");
            }
        }
        for(int y = 0; read.hasNextLine(); y++){
            grid.add(new ArrayList<>());
            String line = read.nextLine();
            for(int x = 0; x < line.length(); x++){
                grid.get(y).add(Integer.parseInt(""+line.charAt(x)));
                if(line.charAt(x)=='1') paint.fillRect(x*30-1,y*30-1,30,30);
            }
        }
        locs.push(new Point((grid.size())/2,(grid.size())/2));
        paint.setColor(Color.BLUE);
        do{
            Point L = locs.pop();
            if(!valLoc(L));
            else if(L.equals(end)) locs.clear();
            else{
                update(L);
                if(L.x>0) if(valLoc(new Point(L.x-1,L.y))) locs.push(new Point(L.x-1,L.y));
                if(L.x<grid.size()-1) if(valLoc(new Point(L.x+1,L.y))) locs.push(new Point(L.x+1,L.y));
                if(L.y>0) if(valLoc(new Point(L.x,L.y-1))) locs.push(new Point(L.x,L.y-1)); //problem with correct y coords
                if(L.y<grid.size()-1) if(valLoc(new Point(L.x,L.y+1))) locs.push(new Point(L.x,L.y+1));
                prevLocs.add(L);
            }
        }while(!locs.isEmpty());
        update(end);
        System.exit(0);
        System.out.println("You reached the end of the maze.");
    }
    
    public boolean valLoc(Point x){
        for(Point i: prevLocs) if(x.equals(i)) return false;
        return grid.get(x.y).get(x.x)==0;
    }
    
    public void update(Point L){
        System.out.println("Press ENTER to continue.");
        new Scanner(System.in).nextLine();
        paint.fillOval(L.x*30-1,L.y*30-1,30,30);
    }
    
}
