package assignment4;

/**
 * Does not fight other critters when on the same x_coord and y_coord
 * Runs when fight is invoked
 * Cycles through the directions and chooses a random one
 * 
 * @author Leo
 *
 */

public class Leo extends Critter{
	
	public String toString() { return "L"; }
	
	private int dir;
	
	public Leo(){
		dir = Critter.getRandomInt(8);
	}

	public void doTimeStep() {
		// TODO Auto-generated method stub
		// run in the direction selected
		run(dir);
		if (getEnergy() > 100){
			Leo child = new Leo();
			reproduce(child, child.dir);
		}
		//set a new random direction
		this.dir = (this.dir + Critter.getRandomInt(7)) % 7;
	}
	
	public boolean fight(String oponent) {
		this.run(dir);
		return false;
	}
}
