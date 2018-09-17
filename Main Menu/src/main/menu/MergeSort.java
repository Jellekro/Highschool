package main.menu;
import java.io.*;
import java.util.*;
public class MergeSort {
    
    private ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
    
    public MergeSort(ArrayList<Integer> list){ 
        for(Integer i: list){
            lists.add(new ArrayList<>());
            lists.get(lists.size()-1).add(i);
        }
        sort(lists);
    }
    
    public MergeSort(File file)throws FileNotFoundException{
        Scanner read = new Scanner(file);
        while(read.hasNextInt()){
            lists.add(new ArrayList<>());
            lists.get(lists.size()-1).add(read.nextInt());
        }        
        sort(lists);
    }
    
    public void sort(ArrayList<ArrayList<Integer>> lists){
        if(lists.size()==1) System.out.println(lists.get(0));
        else{
            ArrayList<ArrayList<Integer>> temps = new ArrayList<>();
            for(int i = 0; i+1<lists.size(); i++){
                temps.add(new ArrayList<Integer>());
                ArrayList<Integer> list1 = lists.get(i);
                ArrayList<Integer> list2 = lists.get(i+1);
                while(!list1.isEmpty() && !list2.isEmpty()){
                    if(list1.get(0)<list2.get(0)){
                        temps.get(temps.size()-1).add(list1.get(0));
                        list1.remove(0);
                    }else{
                        temps.get(temps.size()-1).add(list2.get(0));
                        list2.remove(0);
                    }
                }
                if(!list1.isEmpty()){
                    temps.get(temps.size()-1).addAll(list1);
                    list1.remove(0);
                }else if(!list2.isEmpty()){
                    temps.get(temps.size()-1).addAll(list2);
                    list2.remove(0);
                }
            }
            sort(temps);
        }
    }
}