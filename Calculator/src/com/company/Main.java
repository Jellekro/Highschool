package com.company;

import Calculator.Num;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner readS = new Scanner(new File("spatial.txt"));
        Scanner readC = new Scanner(new File("conceptual.txt"));
        ArrayList<String> spatialMaths = new ArrayList<>(), conceptualMaths = new ArrayList<>();
        while(readS.hasNextLine()) spatialMaths.add(readS.nextLine());
        while(readC.hasNextLine()) conceptualMaths.add(readC.nextLine());
        System.out.println("Welcome to my Calculator. With this application you can perform operations relating to...");
        System.out.println("Spatial Math: "+spatialMaths+"\nOR\nConceptual Math: "+conceptualMaths+"\nOR\nMiscellaneous Math");
        Scanner input = new Scanner(System.in);
        boolean valIn = false;
        while(!valIn){
            String comm = input.nextLine().trim();
            if(comm.isEmpty()) System.out.println("Error #: blank entry.");
            else if(comm.equalsIgnoreCase("quit")) valIn = true;
            else if(spatialMaths.contains(comm) || conceptualMaths.contains(comm)){
                switch(comm){
                    case "algebra":
                        Algebra algebra = new Algebra();
                        break;
                    case "statistics":
                        Statistics statistics = new Statistics();
                }
            }
        }
    }

    public static class Algebra {

        public Algebra(){
            Scanner input = new Scanner(System.in);
            boolean valIn = false;
            while(!valIn){
                System.out.println("Enter your first number.");
                Scanner read = new Scanner(input.nextLine().trim());
                if(!read.hasNext()) System.out.println("Error #: blank entry.");
                else if(!read.hasNextInt()) System.out.println("Error #: invalid entry.");
                else{

                }
            }
        }

        public Num runOp(Num one, Num two){
            Scanner input = new Scanner(System.in);
            boolean valIn = false;
            while(!valIn){
                System.out.println("Do you want to add, subtract, multiply, or divide? (enter one)\n>");
                String comm = input.nextLine();
                Scanner read = new Scanner(comm);
                switch(comm){
                    case "add": return add(one,two);
                    case "subtract": return subtract(one,two);
                    case "multiply": return multiply(one, two);
                    case "divide": return divide(one, two);
                }
                if(!valIn) System.out.println("Error #: incorrect command.");
            }
            return null;
        }

        public Num add(Num one, Num two){
            return new Num(one.getRealPart()+two.getRealPart(), one.getImagPart()+two.getImagPart());
        }

        public Num subtract(Num one, Num two){
            return new Num(one.getRealPart()-two.getRealPart(), one.getImagPart()-two.getImagPart());
        }

        public Num multiply(Num one, Num two){
            return new Num(one.getRealPart()*two.getRealPart(), one.getImagPart()*two.getImagPart());
        }

        public Num divide(Num one, Num two){
            return new Num(one.getRealPart()/two.getRealPart(), one.getImagPart()/two.getImagPart());
        }

    }

    public static class Statistics {

        public Statistics(){

        }

        public Num mean(){}

    }
}
