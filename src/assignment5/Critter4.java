package assignment5;

import assignment5.Critter.CritterShape;

/**
 * Does not fight other critters when on the same x_coord and y_coord
 * Runs when fight is invoked
 * 
 * This critter runs around in the shape of a square
 * 
 * @author Kevin
 *
 */
public class Critter4 extends Critter {
	
	@Override
	public String toString() { return "4"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	public Critter4() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = 0;
	}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		
		dir = (dir + 2) % 8;
	}
	
	public boolean fight(String oponent) {
		run(dir);
		return false;
	}
	
	public static void runStats(java.util.List<Critter> kevins) {
		
	}
	
	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.SALMON; }
}
