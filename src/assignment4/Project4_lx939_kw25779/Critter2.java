package assignment4;

/**
 * Direction is always set to 2
 * Child direction is always set to 6
 * Steve jobs will always defeat opponents
 * @author Leo
 *
 */
public class Critter2 extends Critter{
	
	public String toString() { return "2"; }
	
	private int dir;
	
	public Critter2(){
		// Steve only moves forward
		dir = 2;
	}
	
	public void doTimeStep() {
		// TODO Auto-generated method stub
		// run in the direction selected
		run(dir);
		if (getEnergy() > 100){
			Critter2 tim_cook = new Critter2();
			// Tim only moves backwards :(
			tim_cook.dir = 6;
			reproduce(tim_cook, tim_cook.dir);
		}
	}

	public boolean fight(String oponent) {
		return true;
	}

}
