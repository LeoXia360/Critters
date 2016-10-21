package assignment4;

/**
 * Will always fight other critters when on the same x_coord and y_coord
 * 
 * This critter stays at the same spot only to get energy when it wins in a fight
 * 
 * @author Kevin
 *
 */
public class Critter3 extends Critter {
	
	@Override
	public String toString() { return "3"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	public Critter3() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = 0;
	}

	@Override
	public void doTimeStep() {
		
	}
	
	public boolean fight(String oponent) {
		return true;
	}
	
	public static void runStats(java.util.List<Critter> kevins) {
		
	}
}
