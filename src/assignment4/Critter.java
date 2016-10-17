/* CRITTERS Critter.java
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
package assignment4;

import java.lang.reflect.Constructor;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	/**
	 * Method used for both run and walk
	 * Switches direction for performing the appropriate coordinate change
	 * @param direction
	 * @param num_steps
	 * @param energy_cost
	 */
	private void move(int direction, int num_steps, int energy_cost){
		switch (direction){
		case 0: this.x_coord += num_steps;
				break;
		case 1: this.x_coord += num_steps;
				this.y_coord += num_steps;
				break;
		case 2: this.y_coord += num_steps;
				break;
		case 3: this.y_coord += num_steps;
				this.x_coord -= num_steps;
				break;
		case 4: this.x_coord -= num_steps;
				break;
		case 5: this.x_coord -= num_steps;
				this.y_coord -= num_steps;
				break;
		case 6: this.y_coord -= num_steps;
				break;
		case 7:	this.x_coord += num_steps;
				this.y_coord -= num_steps;
				break;
	}
		this.energy -= energy_cost;

	}
	
	/**
	 * Determines how a critter walks using the move method
	 * @param direction
	 */
	protected final void walk(int direction) {
		move(direction, 1, Params.walk_energy_cost);
		if(this.x_coord > Params.world_width){
			this.x_coord = 0;
		}
		if(this.x_coord < 0){
			this.x_coord = Params.world_width;
		}
		if(this.y_coord > Params.world_height){
			this.y_coord = 0;
		}
		if(this.y_coord < 0){
			this.y_coord = Params.world_height;
		}
		
	}
	
	/**
	 * Determines how a critter runs using the move method
	 * @param direction
	 */
	protected final void run(int direction) {
		move(direction, 2, Params.run_energy_cost);
		switch (this.x_coord){
			case Params.world_width + 2:
				this.x_coord = 2;
				break;
			case Params.world_width + 1:
				this.x_coord  = 1;
				break;
			case -2:
				this.x_coord = Params.world_width - 2;
				break;
			case -1:
				this.x_coord = Params.world_width - 1;
				break;
		}
		switch (this.y_coord){
		case Params.world_height + 2:
			this.y_coord = 2;
			break;
		case Params.world_height + 1:
			this.y_coord  = 1;
			break;
		case -2:
			this.y_coord = Params.world_height - 2;
			break;
		case -1:
			this.y_coord = Params.world_height - 1;
			break;
	}
	}
	
	/**
	 * Check to see if critter can reproduce
	 * Offspring takes half energy and parent loses half energy
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.getEnergy() < Params.min_reproduce_energy){
			return;
		}
		offspring.energy = (int) Math.floor(this.getEnergy() / 2);
		this.energy = (int) Math.ceil(this.getEnergy() / 2);
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord + 1;
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		//we can probably reduce this code by putting in all the critters into a list and just going through the list
		//but we'll keep this for now and change it later when we add more critters.
		
		try {
			Class<?> createCritter = Class.forName("assignment4." + critter_class_name);
			Constructor<?> createConstructor = createCritter.getConstructor();
			
			Critter newCritter = (Critter) createConstructor.newInstance();
			
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);
			//System.out.println(newCritter.x_coord + ", " + newCritter.y_coord);
			newCritter.energy = Params.start_energy;
			
			population.add(newCritter);
		} 
		catch (NoClassDefFoundError e){
			throw new InvalidCritterException(critter_class_name);
		}
		
		//catch for ClassNotFound
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//catch the other Exceptions
		catch (Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		String c = null;
		try {
			Class<?> createCritter = Class.forName("assignment4." + critter_class_name);
			Constructor<?> createConstructor = createCritter.getConstructor();
			Critter newCritter = (Critter) createConstructor.newInstance();
			c = newCritter.toString();

		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (Exception e){
			//do nothing
		}
		// add all critters from population
		for(Critter critter: population){
			if (c.equals(critter.toString())){
				result.add(critter);
			}
			
		}
		// add all critters from babies
		for(Critter critter: babies){
			if(c.equals(critter.toString())){
				result.add(critter);
			}
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	public static void worldTimeStep() {
		for(Critter critter: population){
			critter.doTimeStep();
		}
		
		//deduct the rest energy from all critters
		for(Critter critter: population){
			critter.energy -= Params.rest_energy_cost;
		}
		
		//remove dead critters from population
		for(Critter critter: population){
			if (critter.energy <= 0)
				population.remove(critter);
		}
		
		//generate more alage on the board
		for(int i = 0; i < Params.refresh_algae_count; i ++){
	        try {
				Critter.makeCritter("Algae");
			} catch (InvalidCritterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Displays all the critters in the world
	 */
	
	public static void displayWorld() {
		String[][] world = new String[Params.world_height + 2][2*Params.world_width + 2];
		for (int row = 0; row < Params.world_height + 2; row++){
			for (int column = 0; column < 2*Params.world_width + 2; column++){
//				boolean critterExits = false;
//				for (Critter critter: population){
//					if (critter.x_coord + 1 == row && column == critter.y_coord + 1){
//						world[row][column] = critter.toString();
//						critterExits = true;
//						System.out.println(critter.x_coord + " " + critter.y_coord);
//					}
//				}
				//if (!critterExits){
					if ((row == 0 || row == Params.world_height + 1) && (column == 0 || column == Params.world_width + 1)){
						world[row][column] = "+";
					}
					else if (row == 0 || row == Params.world_height + 1){
						world[row][column] = "-";
					}
					else if (column == 0 || column == Params.world_width + 1){
						world[row][column] = "|";
					}
					else{
						world[row][column] = " ";
					}
				//}
				//System.out.print(world[row][column]);
			}
			//System.out.println();
		}
		
		for (Critter critter: population){
			world[critter.x_coord+1][critter.y_coord+1] = critter.toString();
			System.out.println(critter.x_coord + " " + critter.y_coord);
			for(int i= 0; i < Params.world_height+2; i++){
				for(int j = 0; j < Params.world_height; j++){
					System.out.print(world[i][j]);
				}
				System.out.println();
			}
		}
		
//		for(int i= 0; i < Params.world_height+2; i++){
//			for(int j = 0; j < Params.world_height; j++){
//				System.out.print(world[i][j]);
//			}
//			System.out.println();
//		}
		
//		char[][] world = new char[Params.world_height + 2][Params.world_width + 2];
//		boolean printed = false;
//		for (int row = 0; row < Params.world_height + 2; row++){
//			for (int col = 0; col < Params.world_width + 2; col++){
//				//if a corner
//				if((row == 0 && col == 0) ||
//				   (row == 0 && col == Params.world_width + 1) ||
//				   (col == 0 && row == Params.world_height + 1) ||
//				   (col == Params.world_width + 1 && row == Params.world_height + 1)){
//					world[row][col] = '+';
//					System.out.print(world[row][col]);
//					printed = true;
//				}
//				
//				//print top or bottom border
//				if (!printed && ((row == 0) || (row == Params.world_height + 1))){
//					world[row][col] = '-';
//					System.out.print(world[row][col]);
//					printed = true;
//				}
//				//print left or right border
//				if(!printed && ((col == 0) || (col == Params.world_width + 1))){
//					world[row][col] = '|';
//					System.out.print(world[row][col]);
//					col++;
//					printed = true;
//				}
//				
//				//cycle through the critters to see if one needs to be displayed
//				for(Critter critter: population){
//					int x = critter.x_coord + 1;
//					int y = critter.y_coord + 1;
//					
//					if (x == row && y == col){
//						//display the critter
//						System.out.print(critter);
//						printed = true;
//					}
//				}
//				
//				if(!printed){
//					world[row][col] = ' ';
//					//System.out.print(world[row][col]);
//					System.out.print(world[row][col]);
//				}
//				
//				printed = false;
//			}
//			System.out.println();
//		}
	}
}
