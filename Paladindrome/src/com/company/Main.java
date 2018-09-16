package com.company;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Main{

    protected enum RANK{EMPEROR, WARLORD, MAGE, SOLDIER}
    protected enum TILESTATE{EMPTY, BARRIER, PLAYER, MONSTER}
    protected static Color[] tileColors;
    private static final int ENTTYPESNUM = 4; //4 types of entities possible: emperor, warlord, mage, & soldier
    private static final String TITLE = ">>>~~PALADINDROME~~<<<";
    private static ArmyTree army;
    public static Map map;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, InvalidPropertiesFormatException{
        startUp();
        update();
        exit();
    }

    public static Integer getIntry(int min, int max, int def, String command, String reminder, boolean evenReq, Scanner input) throws InterruptedException{
        Scanner read;
        String tempS;
        while(true){
            System.out.println(command+"\n"+reminder);
            tempS = input.nextLine();
            System.out.println();
            if(tempS.equals("quit")) exit();
            else if(tempS.trim().isEmpty()) return def;
            else if(!tempS.contains(" ")){
                read = new Scanner(tempS);
                if(read.hasNextDouble()){
                    double tempD = read.nextDouble();
                    int tempI = (int)tempD;
                    if(tempD==tempI){
                        if(tempI>=min){
                            if(tempI<=max){
                                if(!evenReq || (evenReq && tempI % 2==0)) return tempI;
                                else System.out.println("ERROR 01: entry should be even.");
                            }else System.out.println("ERROR 02: entry should be less than or equal to "+max+".");
                        }else System.out.println("ERROR 03: entry should be greater than or equal to "+min+".");
                    }else System.out.println("ERROR 04: entry is not a whole number.");
                }else System.out.println("ERROR 05: entry was not a number.");
            }else System.out.println("ERROR 06: multiple entries.");
        }
    }

    public static void startUp() throws FileNotFoundException, InterruptedException, InvalidPropertiesFormatException{
        System.out.println("Welcome to my game..." + TITLE + "...produced by Andy Worth(it)!\n*You can type \"quit\" at any time to exit.\n");
        tileColors = new Color[4];
        tileColors[0] = Color.WHITE; tileColors[1] = Color.BLACK; tileColors[2] = Color.GREEN; tileColors[3] = Color.RED; //make colors associated w/tile states
        ArrayList<Integer> entCounts = new ArrayList<>();
        ArrayList<String> coms = new ArrayList<>(), rems = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        Scanner readC = new Scanner(new File("commands.txt"));
        while(readC.hasNextLine()) coms.add(readC.nextLine());
        Scanner readR = new Scanner(new File("reminders.txt"));
        while(readR.hasNextLine()) rems.add(readR.nextLine());
        int maxScale = maxScale();
        int scale = getIntry(8, maxScale, 300, coms.get(1), rems.get(1).replace("$", ""+maxScale)+"\n"+rems.get(2), true, input); //arbitrary min and default
        int percBarr = getIntry(0, 100, 20, coms.get(2), rems.get(3)+"\n"+rems.get(4), false, input); //arbitrary default
        map = new Map(scale, percBarr);
        for(int i = 0; i<ENTTYPESNUM; i++){
            int max = (i+1)*10;
            String tempS = "";
            if(i>0) tempS = " per "+RANK.values()[i-1];
            entCounts.add(getIntry(1, max, 1, coms.get(0).replace("$", ""+RANK.values()[i]).replace("#", tempS), rems.get(0).replace("$", ""+max), false, input));
        }
        army = new ArmyTree(entCounts);
        System.out.println("The game was successfully loaded. Control your player with the text entry field and view the game in the DrawingPanel.");
    }

    public static int maxScale(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int minDim = screen.height;
        if(screen.width<screen.height) minDim = screen.width;
        if(minDim%2==1) minDim--;
        return minDim;
    }

    public static void update() throws InterruptedException{
        System.out.println("NOTICE: You can only move your player to empty tiles.\n");
        EntityNode.Player player = new EntityNode.Player(army.roots.get(0).power(), army.roots.get(0).deps.get(0).deps.get(0).power());
        while(!army.isDead() && !player.isDead()){ //continue playing till one side is dead
            army.spawn(1);
            player.move();
            army.move();
            player.updatePowers();
        }
    }

    public static void exit() throws InterruptedException{
        System.out.println("Thanks for using my game...\n" + TITLE);
        Thread.sleep(4000); //TODO: adjust this time later
        System.exit(0);
    }

    public static class EntityNode{

        protected int cap; //tells the max power/hp of the entity
        protected int power;
        protected Map.Tile currTile;
        protected boolean up; //tells whether the power is currently increasing (up==true) or decreasing (up==false)
        protected TILESTATE tileS;

        public void move(String dir){
            Point pos = currTile.getPos();
            Point tempP = new Point(pos.x, pos.y);
            switch(dir){ //pick the tile going in the selected direction adjacent to the current tile
                case "n": tempP.setLocation(tempP.x, tempP.y-1);
                          break;
                case "s": tempP.setLocation(tempP.x, tempP.y+1);
                          break;
                case "w": tempP.setLocation(tempP.x-1, tempP.y);
                          break;
                case "e": tempP.setLocation(tempP.x+1, tempP.y);
                          break;
            }
            HashSet<Map.Tile> temp = map.getNeighbors(currTile, TILESTATE.EMPTY); //get all empty adjacent tiles
            for(Map.Tile t: temp){ //if the selected direction is empty, move there, otherwise do nothing
                if(tempP.equals(t.getPos())){
                    currTile.setState(TILESTATE.EMPTY, null);
                    currTile = t;
                    currTile.setState(this.tileS, status());
                    break;
                }
            }
        }

        public int power(){
            int tempI = power;
            if(this instanceof Monster){
                Monster tempM = (Monster) this;
                if(!tempM.deps.isEmpty()) for(Monster m: tempM.deps) tempI += m.power();
            }
            return tempI;
        }

        public String status(){
            String type = "";
            if(this instanceof Player) type += "P: ";
            else{
                Monster tempM = (Monster) this;
                type += tempM.rank.toString().charAt(0)+": ";
            }
            return type+power();
        }

        protected void powerForward(EntityNode ent){
            if(ent.power==ent.cap) ent.up = !ent.up;
            if(ent.up) ent.power++;
            else ent.power--;
            currTile.updateStatus(status());
        }

        protected void powerBackward(EntityNode ent){
            if(ent.power==ent.cap) ent.up = !ent.up;
            if(ent.up) ent.power--;
            else ent.power++;
            currTile.updateStatus(status());
        }

        public static class Player extends EntityNode{

            public Player(int cap, int power){
                super.cap = cap; //initial power of highest ranked monster w/cohort
                super.power = power; //initial power of 2nd highest monster w/cohort
                super.currTile = map.getRandET();
                super.currTile.setState(TILESTATE.PLAYER, status());
                super.up = true;
                super.tileS = TILESTATE.PLAYER;
            }

            public boolean isDead(){
                if(power==0){
                    System.out.println("You have lost!");
                    currTile.setState(TILESTATE.EMPTY, null);
                    for(Monster tempM: army.getActives()) tempM.currTile.setState(TILESTATE.MONSTER, tempM.status());
                }
                return power==0;
            }

            public void move() throws InterruptedException{
                boolean valMove = false;
                Scanner input = new Scanner(System.in);
                String tempS;
                while(!valMove){
                    System.out.println("Enter \"n\", \"s\", \"w\", or \"e\" to move your player north, south, west, or east, respectively.");
                    tempS = input.nextLine().trim();
                    System.out.println();
                    if(tempS.equals("quit")) exit();
                    else if(tempS.isEmpty()) valMove = true;
                    else if(tempS.equals("n") || tempS.equals("s") || tempS.equals("w") || tempS.equals("e")){
                        valMove = true;
                        super.move(tempS);
                    }else System.out.println("ERROR 07: entry was not a valid movement choice.");
                }
            }

            public void updatePowers(){
                HashSet<Map.Tile> tempH1 = map.getNeighbors(currTile, TILESTATE.MONSTER);
                if(!tempH1.isEmpty()){
                    HashSet<Monster> tempH2 = new HashSet<>();
                    for(Monster tempM: army.getActives()) if(tempH1.contains(tempM.currTile)) tempH2.add(tempM); //converts set of monstertile neighbors to actual monsterset
                    Integer powerP = this.power();
                    for(Monster tempM: tempH2){
                        int powerM = tempM.power();
                        if(powerP<powerM){
                            powerBackward(this); //if the player's power at start of update was less than the monster, decrease
                            powerForward(tempM);
                        }else{
                            powerForward(this); //otherwise, increase
                            powerBackward(tempM);
                        }
                        if(tempM.power==0) army.kill(tempM);
                    }
                }
            }
        }

        public static class Monster extends EntityNode{

            protected RANK rank; // tells what class the entity is
            protected ArrayList<Monster> deps;
            private Random rng;

            public Monster(RANK rank){
                super.cap = 10*(RANK.values().length-rank.ordinal());
                super.power = super.cap/2;
                super.currTile = null;
                super.up = true;
                super.tileS = TILESTATE.MONSTER;
                this.rank = rank;
                this.deps = new ArrayList<>();
                this.rng = new Random();
            }

            public void setCurrTile(Map.Tile t){
                currTile = t;
            }

            public void move(){
                int dir = rng.nextInt(4); //4 ordinal directions possible
                switch(dir){
                    case 0: super.move("n");
                            break;
                    case 1: super.move("s");
                            break;
                    case 2: super.move("w");
                            break;
                    case 3: super.move("e");
                            break;
                }
            }

            public static class Emperor extends Monster{
                public Emperor(RANK rank){ super(rank);}
            }

            public static class Warlord extends Monster{
                public Warlord(RANK rank){ super(rank);}
            }

            public static class Mage extends Monster{
                public Mage(RANK rank){ super(rank);}
            }

            public static class Soldier extends Monster{
                public Soldier(RANK rank){
                    super(rank);
                    super.power = 1;
                }
            }
        }

    }

    public static class Map{

        private final int SCALE;
        private Tile[][] grid;
        private DrawingPanel canvas;
        public Graphics g;
        public HashSet<Tile> empties;
        private HashSet<Tile> barriers;
        private Random rng;

        public Map(int scale, int percBarr) throws InvalidPropertiesFormatException{
            this.SCALE = scale;
            this.empties = new HashSet<>();
            this.barriers = new HashSet<>();
            this.rng = new Random();
            initEnviro(percBarr);
            if(percBarr!=0 && percBarr!=100) genBarrier(grid.length*grid[0].length*percBarr/100); //if percBarr is 0 or 100 then generation is completed in initEnviro(#)
            else if(percBarr==100){
                for(Tile temp: barriers){ //draw all the successful barriers
                    Point tempP = temp.getCenter();
                    g.fillRect(tempP.x, tempP.y, SCALE, SCALE);
                }
            }
            if(empties.size()<2) throw new InvalidPropertiesFormatException("Stalemate! The scale you entered resulted in an unplayable game.");
        }

        private void genBarrier(int bCount){
            HashSet<Tile> notBarrs = new HashSet<>(); //tiles that can't be barriers
            while(bCount>0){
                Tile check = getRandET(notBarrs); //gets a random empty tile that may be a barrier
                check.setState(TILESTATE.BARRIER, null); //treats the tile as if it's a successful barrier
                barriers.add(check);
                if(isOpen()) bCount--; //checks that adding the barrier leaves the grid open for spawning everywhere, and decreases barriers still to be added if true
                else{ //this means the grid wasn't open with that barrier added, thus it's unsuccessful and must be reverted to an empty tile
                    check.setState(TILESTATE.EMPTY, null); //reverts its barrier properties
                    barriers.remove(check);
                    notBarrs.add(check); //add it to the set of unsuccessful empty tiles, which can't be barriers
                    if(notBarrs.equals(empties)) notBarrs.clear();
                }
            }
        }

        public Tile[][] getGrid(){
            return grid;
        }

        public HashSet<Tile> getEmpties(){
            return empties;
        }

        private Tile getRandET(HashSet<Tile> notBarrs){ //gets a random empty tile that may be a barrier
            Tile temp = getRandET();
            while(notBarrs.contains(temp)) temp = getRandET(); //if the tile can't be a barrier, try a different one
            return temp;
        }

        public Tile getRandET(){ //gets a random empty tile
            Tile check = null;
            int i = 0;
            int limit = rng.nextInt(empties.size());
            for(Tile temp: empties){
                if(i==limit){
                    check = temp;
                    break;
                }
                i++;
            }
            return check;
        }

        private boolean isOpen(){
            Tile init = getRandET();
            HashSet<Tile> seen = new HashSet<>(); //this consists of tiles we know the state of
            seen.addAll(barriers);
            if(getNeighbors(init, TILESTATE.EMPTY).isEmpty()) return false; //immediately deals with base case of initial point enclosed
            isOpen(init, seen);
            return empties.size()+barriers.size()==seen.size(); //if all the empties were seen, they were accessible, meaning there weren't enclosures
        }

        private void isOpen(Tile curr, HashSet<Tile> seen){
            seen.add(curr);
            HashSet<Tile> nbors = getNeighbors(curr, TILESTATE.EMPTY);
            for(Tile t: nbors) if(!seen.contains(t)) isOpen(t, seen); //views all the unseen empty adjacents, and theirs, and theirs... (recursively)
        }

        public HashSet<Tile> getNeighbors(Tile middle, TILESTATE lookFor){ //gets all accessible (empty) adjacents
            HashSet<Tile> tempH = new HashSet<>(); //puts all adjacent tiles (within the grid) in this hashset
            Point pos = middle.getPos();
            if(pos.y+1<grid.length) tempH.add(grid[pos.y + 1][pos.x]); //north
            if(pos.y-1>-1) tempH.add(grid[pos.y - 1][pos.x]); //south
            if(pos.x-1>-1) tempH.add(grid[pos.y][pos.x - 1]); //west
            if(pos.x+1<grid[0].length) tempH.add(grid[pos.y][pos.x+1]); //east
            Iterator itr = tempH.iterator();
            while(itr.hasNext()){
                Tile tempT = (Tile) itr.next();
                if(tempT.getState()!=lookFor) itr.remove();
            }
            return tempH; //returns the empty adjacent tiles
        }

        private void initEnviro(int percBarr) throws InvalidPropertiesFormatException{
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = screen.width-screen.width%SCALE;
            int y = screen.height-screen.height%SCALE;
            canvas = new DrawingPanel(x,y);
            g = canvas.getGraphics();
            grid = new Tile[y/SCALE][x/SCALE];
            TILESTATE filler = TILESTATE.EMPTY;
            if(percBarr==100) filler = TILESTATE.BARRIER;
            for(int i = 0; i<grid.length; i++){
                for(int j = 0; j<grid[i].length; j++){
                    grid[i][j] = new Tile(new Point(SCALE*j, SCALE*i), new Point(j, i), filler);
                    switch(filler){
                        case EMPTY:   empties.add(grid[i][j]);
                                      break;
                        case BARRIER: barriers.add(grid[i][j]);
                                      break;
                    }
                }
            }
        }

        public class Tile {

            private Point center;
            private Point pos;
            private TILESTATE state;

            public Tile(Point center, Point pos, TILESTATE state){
                this.center = center;
                this.pos = pos;
                this.state = state;
            }

            public Point getCenter(){
                return center;
            }

            public  Point getPos(){
                return pos;
            }

            public TILESTATE getState(){
                return state;
            }

            public void setState(TILESTATE state, String status){ //state describes how the tile is filled, status describes the player/monsters type and power
                this.state = state;
                g.setColor(tileColors[state.ordinal()]);
                g.fillRect(center.x, center.y, SCALE, SCALE);
                if(state==TILESTATE.EMPTY) empties.add(this); //don't consider barriers set as this only changes during generation
                else{
                    empties.remove(this);
                    if(state==TILESTATE.PLAYER || state==TILESTATE.MONSTER) updateStatus(status);
                }
            }

            public void updateStatus(String status){
                g.setColor(tileColors[state.ordinal()]);
                g.fillRect(center.x, center.y, SCALE, SCALE);
                g.setColor(Color.BLACK);
                g.drawString(status, center.x+SCALE/2, center.y+SCALE/2);
            }
        }
    }
}