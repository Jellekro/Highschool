package test;

import java.util.*;
import java.io.*;

public class Test {

    private static Map<String, HashSet<String>> vennD = new TreeMap<>(); //each condition is mapped to its group of valid things

    public static void main(String[] args) throws FileNotFoundException {
        start();
        update();
        exit();
    }

    public static void start() throws FileNotFoundException {
        System.out.println("Please enjoy my Venn Diagrammer app and remember you can type \"quit\" at any time to exit.");
        readFile("Categories.txt");
        readFile("Things.txt");
    }

    public static boolean readFile(String fileName) throws FileNotFoundException {
        boolean valFile = false;
        File file = new File(fileName);
        if(file.exists()){
            /*for some reason the file are being perceived as nonexistent...needs fixing*/
            Scanner read = new Scanner(file);
            if(read.hasNextLine()){
                read.nextLine(); //throwaway instruction line
                if(fileName.equals("Categories.txt")){ //handles categories input process
                    while(read.hasNextLine()){
                        vennD.put(read.nextLine(), new HashSet<>());
                    }
                }else if(fileName.equals("Things.txt")){ //handles things input process
                    Scanner input = new Scanner(System.in);
                    System.out.println("Please answer each question with \"yes\" or \"no\".");
                    for(String descriptor: vennD.keySet()){
                        if(read.hasNextLine()){
                            String currThing = read.nextLine();
                            if(descriptor.equals("Things")){
                                vennD.get(descriptor).add(currThing);
                            }else{
                                System.out.println("Is \""+descriptor+"\" a fitting description of \""+currThing+"\"?");
                                String entry = input.nextLine().trim();
                                if(entry.equals("quit")){
                                    exit();
                                }else if(entry.equals("no")){
                                }else if(entry.equals("yes")){
                                    vennD.get(descriptor).add(currThing);
                                }else{
                                    System.out.println("ERROR #: invalid response to yes or no question.");
                                }
                            }
                        }else{
                            break;
                        }
                    }
                }else{
                    //placeholder for later file types
                }
            }else{
                System.out.println("ERROR #: missing file instruction line.");
                System.out.print("Please copy and paste the following line into the \""+fileName+"\":\n> ");
                switch(fileName){
                    case "Categories.txt":
                        System.out.println("\"List your category descriptors here:\"");
                        break;
                    case "Things.txt":
                        System.out.println("\"List the things you want to categorize:\"");
                        break;
                }
            }
        }else{
            System.out.println("ERROR #: file does not exit.");
        }
        return valFile; //this will only turn true if the file was formatted properly
    }

    public static void update(){

    }

    public static void exit(){
        //have option to save current venn diagram data to a file before exiting
        System.out.println("Thank you for using my Venn Diagrammer app!");
        System.exit(0);
    }    
}