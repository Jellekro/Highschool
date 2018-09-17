package main.menu;
import java.util.*;
import java.io.*;
public class NDA {
    
    private String inType;
    private ArrayList<File> files = new ArrayList<>();
    private ArrayList<ArrayList<Double>> sets = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> sortSets = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> stats = new ArrayList<>();
    /*1st dimension is file#; 
    2nd dimension is: 0 max, 1 min, 2 range, 3 sum, 4 mean, 5 population variance, 6 population standard deviation, 
                      7 median, 8 1st Quartile, 9 3rd Quartile*/
    
    public NDA(){}
    
    public NDA(int numFiles)throws FileNotFoundException{
        for(int i = 0; i < numFiles; i++){
            double max = -2147483648, min = 2147483647, sum = 0, varNum = 0;
            files.add(new File("numdata"+(i+1)+".txt"));
            sets.add(new ArrayList<>());
            sortSets.add(new ArrayList<>());
            stats.add(new ArrayList<>());
            Scanner read = new Scanner(files.get(i));
            while(read.hasNextDouble()){
                double num = read.nextDouble();
                sets.get(i).add(num);
                if(num>max){max = num;}
                if(num<min){min = num;} 
                sum += num;
            }
            for(int j = 0; j < sets.get(i).size(); j++){
                sortSets.get(i).add(sets.get(i).get(j));
            }
            sortSets.get(i).sort(null);
            stats.get(i).add(max);
            stats.get(i).add(min);
            stats.get(i).add(max-min);
            stats.get(i).add(sum);
            stats.get(i).add(sum/sets.get(i).size());
            for(int j = 0; j < sets.get(i).size(); j++){
                varNum += (sets.get(i).get(j)-stats.get(i).get(4))*(sets.get(i).get(j)-stats.get(i).get(4));
            }
            stats.get(i).add(varNum/sets.get(i).size());
            stats.get(i).add(Math.sqrt(stats.get(i).get(5)));
            int half = sortSets.get(i).size()/2;
            int quarter = half/2;
            if(sortSets.get(i).size()%2==1){
                stats.get(i).add(sortSets.get(i).get(half));
                if(sortSets.get(i).size()>3){
                    if(sortSets.get(i).size()/2%2==1){
                        stats.get(i).add(sortSets.get(i).get(quarter));
                        stats.get(i).add(sortSets.get(i).get(3*quarter));
                    }else if(sortSets.get(i).size()/2%2==0){
                        stats.get(i).add((sortSets.get(i).get(quarter)+sortSets.get(i).get(quarter-1))/2);
                        stats.get(i).add((sortSets.get(i).get(3*quarter)+sortSets.get(i).get(3*quarter-1))/2);
                    }
                }
            }else if(sortSets.get(i).size()%2==0){
                stats.get(i).add((sortSets.get(i).get(half)+sortSets.get(i).get(half-1))/2);
                if(sortSets.get(i).size()>3){
                    if(half%2==1){
                        stats.get(i).add(sortSets.get(i).get(quarter));
                        stats.get(i).add(sortSets.get(i).get(3*quarter));
                    }else if(half%2==0){
                        stats.get(i).add((sortSets.get(i).get(quarter)+sortSets.get(i).get(quarter-1))/2);
                        stats.get(i).add((sortSets.get(i).get(3*quarter)+sortSets.get(i).get(3*quarter-1))/2);
                    }
                }
            }
        }
        for(ArrayList<Double> list: stats){
            for(double x: list){
                System.out.print(x+", ");
            }
            System.out.println();
        }
    }
    
    public String getInType(){return inType;}
}
