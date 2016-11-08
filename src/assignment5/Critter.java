package assignment5;

import java.util.List;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected String look(int direction, boolean steps) {
		//false == 1 step
		//true == 2 steps
		int num_steps = 1;
		if(steps){num_steps = 2;}
		int looking_x = this.x_coord;
		int looking_y = this.y_coord;
		//always execute this segment 
		this.energy -= Params.look_energy_cost;
		
		//adding to the x and y coord 
		switch (direction){
		case 0: looking_x = this.x_coord + num_steps;
				break;
		case 1: looking_x = this.x_coord + num_steps;
				looking_y = this.y_coord - num_steps;
				break;
		case 2: looking_y = this.y_coord - num_steps;
				break;
		case 3: looking_y = this.y_coord - num_steps;
				looking_x = this.x_coord - num_steps;
				break;
		case 4: looking_x = this.x_coord - num_steps;
				break;
		case 5: looking_x = this.x_coord - num_steps;
				looking_y = this.y_coord + num_steps;
				break;
		case 6: looking_y = this.y_coord + num_steps;
				break;
		case 7:	looking_x = this.x_coord + num_steps;
				looking_y = this.y_coord + num_steps;
				break;
		}
		
		//wrap x and y coord
		if(!steps){
			if(looking_x >= Params.world_width -1){
			looking_x = 0;
			}
			if(looking_x < 0){
				looking_x = Params.world_width -1;
			}
			if(looking_y >= Params.world_height-1){
				looking_y = 0;
			}
			if(looking_y < 0){
				looking_y = Params.world_height -1;
			}
		}else{
			if (looking_y == Params.world_height + 1){
				looking_y = 1;
			}else if (looking_y == Params.world_height){
				looking_y = 0;
			}else if (looking_y == -2){
				looking_y = Params.world_height -2;
			}else if (looking_y == -1){
				looking_y = Params.world_height -1;
			}
			
			if (looking_x == Params.world_height + 1){
				looking_x = 1;
			}else if(looking_x == Params.world_width){
				looking_x = 0;
			}else if(looking_x == -2){
				looking_x = Params.world_width - 2;
			}else if(looking_x == -1){
				looking_x = Params.world_width - 1;
			}
			
		}
		
		//checking to see if there is an occupant that has same x and y coord
		for(Critter occupant: population){
				if(looking_x == occupant.x_coord && looking_y == occupant.y_coord){
					return occupant.toString();
				}
		}
		return null;
	}
	
	/* rest is unchanged from Project 4 */
	
	
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
	private boolean move;
	private static int size = 20;
	
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
				this.y_coord -= num_steps;
				break;
		case 2: this.y_coord -= num_steps;
				break;
		case 3: this.y_coord -= num_steps;
				this.x_coord -= num_steps;
				break;
		case 4: this.x_coord -= num_steps;
				break;
		case 5: this.x_coord -= num_steps;
				this.y_coord += num_steps;
				break;
		case 6: this.y_coord += num_steps;
				break;
		case 7:	this.x_coord += num_steps;
				this.y_coord += num_steps;
				break;
		}
		this.energy -= energy_cost;
		this.move = true;
	}
	
	private final int wrapX(int x){
		if(x<0){
			return Params.world_width - 1;
		}else if (x > Params.world_width - 1) {
			return 0;
		} else {
			return x;
		}
	}
	
	private final int wrapY(int y){
		if (y<0){
			return Params.world_height -1;
		}else if (y == Params.world_height){
			return 0;
		}else{
			return y;
		}
	}
	
	protected final void walk(int direction) {
		move(direction, 1, Params.walk_energy_cost);
		if(this.x_coord >= Params.world_width -1){
			this.x_coord = 0;
		}
		if(this.x_coord < 0){
			this.x_coord = Params.world_width -1;
		}
		if(this.y_coord >= Params.world_height-1){
			this.y_coord = 0;
		}
		if(this.y_coord < 0){
			this.y_coord = Params.world_height -1;
		}
	}
	
	protected final void run(int direction) {
		move(direction, 2, Params.run_energy_cost);

		if (this.y_coord == Params.world_height + 1){
			this.y_coord = 1;
		}else if (this.y_coord == Params.world_height){
			this.y_coord = 0;
		}else if (this.y_coord == -2){
			this.y_coord = Params.world_height -2;
		}else if (this.y_coord == -1){
			this.y_coord = Params.world_height -1;
		}
		
		if (this.x_coord == Params.world_height + 1){
			this.x_coord = 1;
		}else if(this.x_coord == Params.world_width){
			this.x_coord = 0;
		}else if(this.x_coord == -2){
			this.x_coord = Params.world_width - 2;
		}else if(this.x_coord == -1){
			this.x_coord = Params.world_width - 1;
		}
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (this.getEnergy() < Params.min_reproduce_energy){
			return;
		}
		offspring.energy = (int) Math.floor(this.getEnergy() / 2);
		this.energy = (int) Math.ceil(this.getEnergy() / 2);
		switch (direction){
		case 0: offspring.x_coord += 1;
				break;
		case 1: offspring.x_coord += 1;
				offspring.y_coord -= 1;
				break;
		case 2: offspring.y_coord -= 1;
				break;
		case 3: offspring.y_coord -= 1;
				offspring.x_coord -= 1;
				break;
		case 4: offspring.x_coord -= 1;
				break;
		case 5: offspring.x_coord -= 1;
				offspring.y_coord += 1;
				break;
		case 6: offspring.y_coord += 1;
				break;
		case 7:	offspring.x_coord += 1;
				offspring.y_coord += 1;
				break;
		}
		offspring.x_coord = wrapX(offspring.x_coord);
		offspring.y_coord = wrapY(offspring.y_coord);

		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	
	public static void worldTimeStep() {
		//set move to false for when a new time step is done
		for(Critter critter: population){
			critter.move = false;
		}
		

		//do time step
		for(Critter critter: population){
			critter.doTimeStep();
		}
		
		//fight
		for(Critter critter: population){
			for(Critter oponent: population){
				//1. if more than one critter occupies the same space
				//2. Critter != Oponent 
				//3. Critter is alive
				if(critter.x_coord == oponent.x_coord && 
						critter.y_coord == oponent.y_coord && 
						population.indexOf(critter) != population.indexOf(oponent) &&
						(critter.energy > 0) && (oponent.energy > 0)
						){
					
					//get their current location and if they have moved, because when calling fight, they would have already moved
					int critterXTemp = critter.x_coord;
					int critterYTemp = critter.y_coord;
					int oponentXTemp = oponent.x_coord;
					int oponentYTemp = oponent.y_coord;
					boolean critterMove = critter.move;
					boolean oponentMove = oponent.move;
					
					//are the critters and oponents gonna fight?
					boolean cFight = critter.fight(oponent.toString());
					boolean oFight = oponent.fight(critter.toString());
					
					//both the critter and oponent want to fight
					if (cFight && oFight){
						int critterFightNum = getRandomInt(critter.energy);
						int oponentFightNum = getRandomInt(oponent.energy);
						if (critterFightNum > oponentFightNum){
							critter.energy += oponent.energy / 2;
							oponent.energy = 0;
						}else if (critterFightNum < oponentFightNum){
							oponent.energy += critter.energy / 2;
							critter.energy = 0;
						}else{
							//arbitrarily chooses a winner
							oponent.energy += critter.energy / 2;
							critter.energy = 0;
						}
						break;
					}
					else{
						//these fields lets us know whether or if either the critter or oponent could not leave
						boolean critterCanNotLeave = false;
						boolean oponentCanNotLeave = false;
						
						//critter wants to leave
						if(cFight == false){
							//critter didn't move during the doTimeStep
							if(critterMove == false){
								//if this for loop is reached, that means the critter can move, now to see if there's a space open for it.
								for (Critter i: population){
									//checks to see if the spot that the critter ran during fight is already occupied
									if((critter.x_coord == i.x_coord) && (critter.y_coord == i.y_coord) && (population.indexOf(critter) != population.indexOf(i)) && (i.getEnergy() > 0)){
										//move the critter back to its original place
										critter.x_coord = critterXTemp;
										critter.y_coord = critterYTemp;
										critter.move = false;
										critterCanNotLeave = true;
										break;
									}
								}
							}
						}
						else if(cFight == true){
							critterCanNotLeave = true;
						}
						
						if(oFight == false){
							//critter didn't move during the doTimeStep
							if(oponentMove == false){
								for (Critter i: population){
									//checks to see if the spot that the critter ran during fight is already occupied
									if((oponent.x_coord == i.x_coord) && (oponent.y_coord == i.y_coord) && (population.indexOf(oponent) != population.indexOf(i)) && (i.getEnergy() > 0)){
										//move the critter back to its original place
										oponent.x_coord = oponentXTemp;
										oponent.y_coord = oponentYTemp;
										oponent.move = false;
										oponentCanNotLeave = true;
										break;
									}
								}
							}
						}
						else if(oFight == true){
							oponentCanNotLeave = true;
						}
						
						//if this evaluates to true, then both the critter and oponent can't leave, so they have to fight
						if(critterCanNotLeave && oponentCanNotLeave){
							//if one or both of the critters are dead, then they don't need to fight anymore.
							if(critter.getEnergy() <= 0 || oponent.getEnergy() <= 0){
								break;
							}
							int critterFightNum = getRandomInt(critter.energy);
							int oponentFightNum = getRandomInt(oponent.energy);
							if (critter.toString().equals("@")){
								critterFightNum = 0;
							}
							if (oponent.toString().equals("@")){
								oponentFightNum = 0;
							}
							if (critterFightNum > oponentFightNum){
								critter.energy += oponent.energy / 2;
								oponent.energy = 0;
							}else if (critterFightNum < oponentFightNum){
								oponent.energy += critter.energy / 2;
								critter.energy = 0;
							}else{
								//arbitrarily chooses a winner
								oponent.energy += critter.energy / 2;
								critter.energy = 0;
							}
							break;
						}
						//this means that both of them could move, so not more conflict
						else{
							break;
						}
						
					}
				}
			}
		}

		
		
		
		
		//deduct the rest energy from all critters
		for(Critter critter: population){
			critter.energy -= Params.rest_energy_cost;
		}
		
		//add babies to the population
		population.addAll(babies);
		babies.clear();
		
		//generate more alage on the board
		for(int i = 0; i < Params.refresh_algae_count; i ++){
	        try {
				Critter.makeCritter("Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}
		
		//remove dead critters from population
		List<Critter> remove = new java.util.ArrayList<Critter>();
		for(Critter critter: population){
			if (critter.energy <= 0)
				remove.add(critter);
		}
		population.removeAll(remove);
	}
	
	public static void displayWorld() {
		Main.grid.getChildren().clear(); // clean up grid.
		
		//creates the grid
		for (int i = 0; i < Params.world_width; i++){
			for (int j = 0; j < Params.world_height; j++){
				Shape s = new Rectangle(size, size);
				s.setFill(javafx.scene.paint.Color.TRANSPARENT);
				s.setStroke(javafx.scene.paint.Color.BLACK);
				Main.grid.add(s, i, j);
			}
		}
		
		for (Critter critter: population){
			Shape s;
			switch(critter.viewShape()){
			case CIRCLE: 
				s = new Circle(size/2);
				s.setFill(critter.viewFillColor());
				s.setStroke(critter.viewOutlineColor());
				Main.grid.add(s, critter.x_coord, critter.y_coord);
				break;
				
			case SQUARE: 
				s = new Rectangle(size - 4, size - 4);
				s.setFill(critter.viewFillColor());
				s.setStroke(critter.viewOutlineColor());
				Main.grid.add(s, critter.x_coord, critter.y_coord);
				break;
			
			case TRIANGLE:
				Polygon triangle = new Polygon();
				triangle.getPoints().addAll(new Double[]{
						(double)(size/2), 0.0,
						0.0, (double)size,
						(double)size, (double)size});
				triangle.setFill(critter.viewOutlineColor());
				triangle.setStroke(critter.viewOutlineColor());
				Main.grid.add(triangle, critter.x_coord, critter.y_coord);
				break;
				
			case DIAMOND:
				Polygon diamond = new Polygon();
				diamond.getPoints().addAll(new Double[]{
						(double)(size/2), 0.0,
						0.0, (double)(size/2),
						(double)(size/2), (double)size,
						(double)size, (double)size/2 });
				diamond.setFill(critter.viewOutlineColor());
				diamond.setStroke(critter.viewOutlineColor());
				Main.grid.add(diamond, critter.x_coord, critter.y_coord);
				break;
			
			case STAR:
				Polygon star = new Polygon();
				star.getPoints().addAll(new Double[]{
						(double)(size/2), 0.0,
						(double)(size/4), (double)(size/5),
//						0.0, (double)(size/5),
						(double)(size/4), (double)(size/5)*2,
//						0.0, (double)size,
//						(double)(size/2), (double)(size/5)*3,
//						(double)size, (double)size,
						(double)(size/4)*2, (double)(size/5)*2,
//						(double)size, (double)(size/5),
						(double)(size/4)*2, (double)(size/5)});
				star.setFill(critter.viewOutlineColor());
				star.setStroke(critter.viewOutlineColor());
				Main.grid.add(star, critter.x_coord, critter.y_coord);
			}
		}
	}
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class<?> createCritter = Class.forName(myPackage + "." + critter_class_name);
			Constructor<?> createConstructor = createCritter.getConstructor();
			
			Critter newCritter = (Critter) createConstructor.newInstance();
			
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);

			//System.out.println(newCritter.x_coord + ", " + newCritter.y_coord);
			newCritter.energy = Params.start_energy;
			//System.out.println(newCritter.x_coord + " " + newCritter.y_coord);
			
			population.add(newCritter);
		} 
		catch (NoClassDefFoundError e){
			throw new InvalidCritterException(critter_class_name);
		}
		
		//catch for ClassNotFound
		catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		//catch the other Exceptions
		catch (Exception e){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		return null;
	}
	
	public static void runStats(List<Critter> critters) {}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
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
	}
	
	
}
