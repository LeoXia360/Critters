package assignment4;

/**
 * Direction is always set to 2
 * Child direction is always set to 6
 * Steve jobs will always defeat opponents
 * @author Leo
 *
 */
public class SteveJobs extends Critter{
	
	public String toString() { return "S"; }
	
	private int dir;
	
	public SteveJobs(){
		// Steve only moves forward
		dir = 2;
	}
	
	public void doTimeStep() {
		// TODO Auto-generated method stub
		// run in the direction selected
		run(dir);
		if (getEnergy() > 100){
			SteveJobs tim_cook = new SteveJobs();
			// Tim only moves backwards :(
			tim_cook.dir = 6;
			reproduce(tim_cook, tim_cook.dir);
		}
	}

	public boolean fight(String oponent) {
		//Steve always defeats opponents
		
		return true;
	}

}
