package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class ArmyTree extends Main{

    public ArrayList<EntityNode.Monster> roots;
    private HashSet<EntityNode.Monster> actives; //monsters currently one the grid
    private ArrayList<Integer> entsCount;
    private int size;

    public ArmyTree(ArrayList<Integer> entsCount){
        this.roots = new ArrayList<>();
        this.actives = new HashSet<>();
        for(int i = 1; i<entsCount.size(); i++) entsCount.set(i,entsCount.get(i)*entsCount.get(i-1));
        this.entsCount = entsCount;
        for(Integer x: entsCount) size += x;
        for(int i = 0; i<entsCount.size(); i++){
            for(int j = entsCount.get(i); j>0; j--){
                EntityNode.Monster curr = null;
                switch(i){
                    case 0: curr = new EntityNode.Monster.Emperor(RANK.EMPEROR);
                        break;
                    case 1: curr = new EntityNode.Monster.Warlord(RANK.WARLORD);
                        break;
                    case 2: curr = new EntityNode.Monster.Mage(RANK.MAGE);
                        break;
                    case 3: curr = new EntityNode.Monster.Soldier(RANK.SOLDIER);
                        break;
                }
                add(curr);
            }
        }
    }

    public boolean isDead(){ //tells whether the army is fully dead or not
        if(roots.isEmpty()) System.out.println("You have won!");
        return roots.isEmpty();
    }

    public void kill(EntityNode.Monster target){
        kill(target, roots);
    }

    private boolean kill(EntityNode.Monster target, ArrayList<EntityNode.Monster> subs){
        for(EntityNode.Monster curr: subs){
            if(curr.equals(target)){
                actives.remove(curr);
                curr.currTile.setState(TILESTATE.EMPTY, null);
                if(curr.deps.isEmpty()) subs.remove(curr);
                else{
                    ArrayList<EntityNode.Monster> cohort = new ArrayList<>();
                    cohort.addAll(curr.deps);
                    subs.remove(curr);
                    EntityNode.Monster tempMin = min(subs);
                    if(tempMin!=null) tempMin.deps.addAll(cohort);
                }
                return true;
            }else if(curr.rank.compareTo(target.rank)<0 && kill(target, curr.deps)) return true;
        }
        return false;
    }

    public void move(){
        for(EntityNode.Monster tempM: actives) tempM.move();
    }

    public void spawn(int i){ //in case future developers want to spawn more and more monsters each round to incite the player to attack
        while(i>0 && size!=0){
            Map.Tile tempT = map.getRandET();
            EntityNode.Monster tempM = getR(); //get a random monster
            while(actives.contains(tempM)) tempM = getR(); // a new one that is
            tempM.setCurrTile(tempT); //put that monster on a random empty tile
            actives.add(tempM); //note that the monster is now active in the game
            size--;
            tempT.setState(TILESTATE.MONSTER, tempM.status()); //note that the tile now has a monster on it
            i--;
        }
    }

    public EntityNode.Monster getR(){ //gets a random monster from the army tree
        Random rng = new Random();
        int tempV = 0, tempI = 0;
        while(tempV==0){ //checks that the random ordinal doesn't address killed of rank
            tempI = rng.nextInt(RANK.values().length);
            tempV = entsCount.get(tempI);
        }
        RANK rank = RANK.values()[tempI];
        return getR(rng, rank, roots);
    }

    private EntityNode.Monster getR(Random rng, RANK rank, ArrayList<EntityNode.Monster> subs){
        if(!subs.isEmpty() && subs.get(0).rank==rank) return subs.get(rng.nextInt(subs.size()));
        return getR(rng, rank, subs.get(rng.nextInt(subs.size())).deps);
    }

    public void add(EntityNode.Monster ent){
        if(roots.isEmpty() || ent.rank.ordinal()==0) roots.add(ent); //if adding an Emperor or the tree is empty, then add directly
        else add(ent, min(roots)); //otherwise add it in a specific spot
    }

    private void add(EntityNode.Monster ent, EntityNode.Monster curr){
        if(curr.deps.isEmpty() || curr.rank.ordinal()==ent.rank.ordinal()-1) curr.deps.add(ent); //if the current cohort is the same rank or is empty, add here
        else add(ent,min(curr.deps)); //otherwise keeping going down the tree by going through the node with the current least power to bring them up in power
    }

    private EntityNode.Monster min(ArrayList<EntityNode.Monster> subs){
        if(subs.isEmpty()) return null;
        if(subs.size()==1) return subs.get(0);
        int minInd = 0;
        int minVal = Integer.MAX_VALUE;
        for(int i = 0; i<subs.size(); i++){
            int power = subs.get(i).power();
            if(power<minVal) {
                minVal = power;
                minInd = i;
            }
        }
        return subs.get(minInd);
    }

    public HashSet<EntityNode.Monster> getActives(){
        return actives;
    }
}