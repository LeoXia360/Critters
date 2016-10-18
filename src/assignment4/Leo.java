package assignment4;

/**
 * Does not fight other critters when on the same x_coord and y_coord
 * Runs when fight is invoked
 * 
 * @author Leo
 *
 */

public class Leo extends Critter{
	
	public String toString() { return "L"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Leo(){
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}

	public void doTimeStep() {
		// TODO Auto-generated method stub
		// run in the direction selected
		run(dir);
		
		
	}
	
	public boolean fight(String oponent) {
		this.run(dir);
		return false;
	}
	
	public static void runStats(java.util.List<Critter> leos) {
		
	}

	

}
