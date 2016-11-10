package assignment5;

/**
 * Does not fight other critters when on the same x_coord and y_coord
 * Runs when fight is invoked
 * Cycles through the directions and chooses a random one
 * 
 * @author Leo
 *
 */

public class Critter1 extends Critter{
	
	public String toString() { return "1"; }
	
	private int dir;
	
	public Critter1(){
		dir = Critter.getRandomInt(8);
	}

	public void doTimeStep() {
		// TODO Auto-generated method stub
		// run in the direction selected
		look(this.dir, true);
		run(dir);
		if (getEnergy() > 100){
			Critter1 child = new Critter1();
			reproduce(child, child.dir);
		}
		//set a new random direction
		this.dir = (this.dir + Critter.getRandomInt(7)) % 7;
	}
	
	public boolean fight(String oponent) {
		this.run(dir);
		return false;
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		
		return CritterShape.SQUARE;
	}
}
