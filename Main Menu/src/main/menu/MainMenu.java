package main.menu;
import java.util.*;
import java.awt.*;
import java.io.*;
public class MainMenu {

    private String inType;
    
    public static void main(String[] args)throws FileNotFoundException,IOException{
        startup();
        /*System.out.println("You can type quit at any time while using my programs to terminate the system.");
        Scanner input = new Scanner(System.in);
        boolean valIn = false;
        while(!valIn){
            System.out.println("Please type a valid app name.");
            switch(input.nextLine()){
                case "MZ": MZstartup();
                           valIn = true;
                           break;
                case "SM": SMstartup();
                           valIn = true;
                           break;
                case "PF": PFstartup();
                           valIn = true;
                           break;
                case "NDA": NDAstartup();
                            valIn = true;
                            break;
                case "Cal": Calstartup();
                            valIn = true;
                            break;
                case "GoL": GoLstartup();
                            valIn = true;
                            break;
                case "ToH": ToHstartup();
                           valIn = true;
                           break;
                case "MS": MSstartup();
                           valIn = true;
                           break;
                case "Sorter": Sorstartup();
                           valIn = true;
                           break;
                case "quit": valIn = true;
                             break;
            }
            if(!valIn){
                System.out.println("Your entry was invalid, try again.");
            }
        }*/
    }    
    
    public static void MZstartup()throws FileNotFoundException{
        boolean valInfo = false;
        Scanner read = new Scanner(System.in);
        while(!valInfo){
            System.out.println("Please enter the maze file you want to escape.");
            String file = read.nextLine().trim();
            if(file.isEmpty()){
                System.out.println("Invalid Entry: blank ENTER.");
            }else if(file.equalsIgnoreCase("quit")){
                valInfo = true;
            }else if(new File(file+".txt").exists()){
                valInfo = true;
                Mazer mazer1 = new Mazer(new Scanner(new File(file+".txt")));
            }else{
                System.out.println("Invalid Entry: it should be a valid file name.");
            }
        }
        System.out.println("Thanks for using my Mazer app.");
    }
    
    public static void PFstartup(){
        boolean valInfo = false;
        Scanner read = new Scanner(System.in);
        while(!valInfo){
            System.out.println("Please enter the max value of the set of numbers you want to search for primes.");
            String num = read.nextLine().trim();
            if(num.isEmpty()) System.out.println("Invalid Entry: blank ENTER.");
            else if(num.equalsIgnoreCase("quit")) valInfo = true;
            else if(new Scanner(num).hasNextInt()){
                int n = Integer.parseInt(num);
                if(n>1){
                    valInfo = true;
                    PF find1 = new PF(n);
                }else System.out.println("There are no primes in your set of numbers.");
            }else System.out.println("Invalid Entry: it should be a number.");
        }
        System.out.println("Thanks for using my Prime Finder app.");
    }
    
    public static void Sorstartup()throws FileNotFoundException,IOException{
        boolean valInfo = false;
        Scanner read = new Scanner(System.in);
        while(!valInfo){
            System.out.println("Please enter the file of numbers you want to sort.");
            String file = read.nextLine().trim();
            if(file.isEmpty()){
                System.out.println("Invalid Entry: blank ENTER.");
            }else if(file.equalsIgnoreCase("quit")){
                valInfo = true;
            }else if(new File(file+".txt").exists()){
                valInfo = true;
                Sorter sorter1 = new Sorter(new File(file+".txt"));
            }else{
                System.out.println("Invalid Entry: it should be a valid file name.");
            }
        }
        System.out.println("Thanks for using my Sorter app.");
    }
    
    public static void MSstartup()throws FileNotFoundException{
        boolean valInfo = false;
        Scanner read = new Scanner(System.in);
        while(!valInfo){
            System.out.println("Please enter the file of numbers you want to sort.");
            String file = read.nextLine().trim();
            if(file.isEmpty()){
                System.out.println("Invalid Entry: blank ENTER.");
            }else if(file.equalsIgnoreCase("quit")){
                valInfo = true;
            }else if(new File(file+".txt").exists()){
                valInfo = true;
                MergeSort mser1 = new MergeSort(new File(file+".txt"));
            }else{
                System.out.println("Invalid Entry: it should be a valid file name.");
            }
        }
        System.out.println("Thanks for using my Merge Sort app.");
    }
    
    public static void ToHstartup(){
        boolean valInfo = false;
        Scanner read = new Scanner(System.in);
        while(!valInfo){
            System.out.println("Please enter the number of layers you want in your inital Tower of Hanoi.");
            String num = read.nextLine().trim();
            if(num.isEmpty()){
                System.out.println("Invalid Entry: blank ENTER.");
            }else if(num.equalsIgnoreCase("quit")){
                valInfo = true;
            }else if(new Scanner(num).hasNextInt()){
                int x = Integer.parseInt(num);
                if(x>0){
                    valInfo = true;
                    ToH tow1 = new ToH(x);
                }else System.out.println("Invalid Entry: nonpositive number.");
            }else{
                System.out.println("Invalid Entry: integer required.");
            }
        }
        System.out.println("Thanks for using my Tower of Hanoi app.");
    }
    
    public static void startup()throws FileNotFoundException{
        Map<Integer,ArrayList<String>> apps = new HashMap<>();
        for(int i = 0; i < 3; i++);
        Scanner read = new Scanner(new File("apps.txt"));
        for(int sect = 0; read.hasNextLine();){
            String line = read.nextLine();
            if(line.isEmpty()) sect++;
            
        }
        while(read.hasNextLine()) apps.add(read.nextLine());
        Scanner input = new Scanner(System.in);
        boolean valIn = false;
        while(!valIn){
            System.out.println("Please type the name of the app you would like to open.");
            String app = input.nextLine();
            if(app.equalsIgnoreCase("quit")) valIn = true;
            else if(!apps.contains(app)) System.out.println("Your app entry was invalid.");
            else if(apps.contains(app)){
                Object obj1 = new GoL();
                if(app.equals("Calendar")) obj1 = new CAL();
                else if(app.equals("Calculator")) obj1 = new Calc();
                else if(app.equals("Game of Life")) obj1 = new GoL();
                else if(app.equals("Stable Marriage")) obj1 = new SM();
                else if(app.equals("Number Data Analysis")) obj1 = new NDA();
                valIn = fileStart(obj1);
            }
        }        
    }
    
    public static boolean fileStart(Object obj1){
        if((obj1 instanceof CAL) || (obj1 instanceof Calc) || (obj1 instanceof GoL) || (obj1 instanceof SM) || (obj1 instanceof NDA)){
            
        }
        Scanner input = new Scanner(System.in);
        boolean valIn = false;
        while(!valIn){
            System.out.println("Please type a file name for the to consider.");
            String file = input.nextLine();
            if(file.equalsIgnoreCase("quit")) valIn = true;
            if(new File(file+".txt").exists()){
                obj1 = null;
                switch(file){
                }
            }else System.out.println("Your file entry was invalid.");
        }
        return valIn;
    }
    
    public static void SMstartup()throws FileNotFoundException{
        boolean valInfo = false;
        Scanner read = new Scanner(System.in);
        while(!valInfo){
            System.out.println("Please enter the file name of your bachelors' preferences.");
            String line = read.nextLine().trim()+".txt";
            if(line.length()==4){
                System.out.println("Invalid Entry: blank ENTER.");
            }else if(line.equalsIgnoreCase("quit.txt")){
                valInfo = true;
            }else if(new File(line).exists()){
                valInfo = true;
                SM match1 = new SM(line);
            }else{
                System.out.println("Invalid Entry: it should be a valid file name.");
            }
        }
        System.out.println("Thanks for using my Stable Marriage app.");
    }
    
    public static void NDAstartup()throws FileNotFoundException{
        Scanner input = new Scanner(System.in);
        boolean valEntry = false;
        while(!valEntry){
            System.out.println("Please enter the number of files you want to analyze with the filename format: \"numdata#\".");
            String info = input.nextLine().trim();
            if(info.isEmpty()){
                System.out.println("Invalid Entry: blank ENTER.");
            }else if(info.equalsIgnoreCase("quit")){
                valEntry = true;
            }else if(new Scanner(info).hasNextInt()){
                valEntry = true;
                NDA analyzer1 = new NDA(Integer.parseInt(info));
            }else{
                System.out.println("Invalid Entry: it should be a number indicating how many files, with format \"numdata#\", you want to analyze.");
            }
        }
        System.out.println("Thanks for using my Number Data Analysis app.");
    }
    
    public static void Calstartup(){
        Scanner input = new Scanner(System.in);
        boolean valDate = false;
        while(!valDate){
            System.out.println("Please type a valid date. (ex: 04/13/1999)");
            String date = input.nextLine().trim();
            if(date.isEmpty()) System.out.println("Invalid Entry: blank ENTER.");
            else if(date.equalsIgnoreCase("quit")) valDate = true;
            else valDate = new CAL(date).getValid();
        }
        System.out.println("Thanks for using my Calendar app.");
    }
    
    public static void GoLstartup()throws FileNotFoundException{
        Scanner appInfo = new Scanner(System.in);
        boolean valInfo = false;
        System.out.println("Please type a filename to generate and optionally two numbers to limit level scope (what part of the file is viewed).");
        System.out.println("If you enter one number the scope will be a square of that size or if you enter two numbers the scope will be that width and height.");
        while(!valInfo){
            String info = appInfo.nextLine();
            Scanner token = new Scanner(info);
            if(info.trim().isEmpty()){
                System.out.println("Invalid Entry: blank ENTER.");
            }else if(info.equalsIgnoreCase("quit")){
                valInfo = true;
            }else if(token.hasNext()){
                File data = new File(token.next() + ".txt");
                if(data.exists()){
                    if(token.hasNextInt()){
                        int length1 = token.nextInt();
                        if(token.hasNextInt()){
                            int length2 = token.nextInt();
                            if(!token.hasNext()){
                                GoL gen1 = new GoL(data,length1,length2);
                                valInfo = gen1.fileScan();                             
                            }else{
                                System.out.println("Invalid Entry: entry has more than three parts.");
                            }
                        }else if(!token.hasNext()){
                            GoL gen1 = new GoL(data,length1);
                            valInfo = gen1.fileScan();
                        }else{
                            System.out.println("Invalid Entry: entry has an unnecessary or incorrect third part.");
                        }
                    }else if(!token.hasNext()){
                        GoL gen1 = new GoL(data);
                        valInfo = gen1.fileScan();
                    }else{
                        System.out.println("Invalid Entry: entry two or more words.");
                    }
                }else{
                    System.out.println("Invalid Entry: filename does not exist.");
                }
            }if(!valInfo){
                System.out.println("Please enter a filename and/or one or two numbers to define file dimensions.");
            }
        }    
        System.out.println("Thanks for using my Game of Life app.");
    } 
    
    public String getInType(){return inType;}
}