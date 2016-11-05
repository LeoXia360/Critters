/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4; // cannot be in default package
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.ws.Response;

import java.io.*;
import java.lang.reflect.Constructor;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws ClassNotFoundException, InvalidCritterException { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        try {
        	for(int i = 0; i < 25; i++){
        		Critter.makeCritter("Craig");
        	}
        	for(int j = 0; j < 100; j++){
        		Critter.makeCritter("Algae");
        	}
		} catch (InvalidCritterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("GLHF");
        
        //Controller Component
        boolean quit = false;
        
        while(!quit){
        	System.out.print("critter> ");
        	String input = kb.nextLine();
        	String[] reponse = input.split(" ");
        	
        	switch(reponse[0]){
        	case "quit":
        		if (reponse.length > 1){
        			System.out.println("error processing: " + input);
        		}
        		else{
        			System.exit(0);
        		}
        		break;
        	
        	case "show":
        		if (reponse.length > 1){
        			System.out.println("error processing: " + input);
        			break;
        		}
        		Critter.displayWorld();
        		break;
        	
        	case "step":
        		if (reponse.length > 2){
        			System.out.println("error processing: " + input);
        		}
        		else if (reponse.length == 2){
        			try{
        				int amount = Integer.parseInt(reponse[1]);
        				for (int i = 0; i < amount; i++){
        					Critter.worldTimeStep();
        				}
        			}
        			catch (NumberFormatException e){
        				System.out.println("error processing: " + input);
        			}
        		}
        		else{
        			Critter.worldTimeStep();
        		}
        		break;
        		
        	case "seed":
        		if (reponse.length != 2){
        			System.out.println("error processing: " + input);
        		}
        		else{
        			try{
        				long num = Integer.parseInt(reponse[1]);
                		Critter.setSeed(num);
        			}
        			catch(NumberFormatException e){
        				System.out.println("error processing: " + input);
        			}
        		}
        		break;
        	
        	case "make":
        		if (reponse.length > 3){
        			System.out.println("error processing: " + input);
        		}
        		else{
        			try{
        				int amount = 1;
        				if (reponse.length == 3){
        					amount = Integer.parseInt(reponse[2]);
        				}
        				for (int i = 0; i < amount; i++){
        					try{
        						Critter.makeCritter(reponse[1]);
        					}
        					catch(InvalidCritterException e){
        						System.out.println("error processing: " + input);
        					}
        					catch (Exception e){
        						System.out.println("error processing: " + input);
        					}
        				}
        			}
        			catch (NumberFormatException e){
        				System.out.println("error processing: " + input);
        			}
        		}
        		break;
	
        		
        	case "stats":
        		if (reponse.length < 2){
        			System.out.println("error processing: " + input);
        			break;
        		}
        		try {
        			Class.forName(myPackage + "."+ reponse[1]);
        			List<Critter> c = new ArrayList<Critter>();
        			c = Critter.getInstances(reponse[1]);
        			Critter.runStats(c);
        		}
//        		catch (ClassNotFoundException e){
//    				throw new InvalidCritterException(reponse[1]);
//        		}
        		catch (Exception e){
        			System.out.println("error processing: " + input);
        		}
        		break;
        	default: 
        		System.out.println("invalid command: " + input);
        		break;
        }
        
        /* Write your code above */
        System.out.flush();
        }
    }
}