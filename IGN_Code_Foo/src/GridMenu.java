/**
 * GridMenu --- main class to execute the grid solver
 * 
 * @author Michael C. Velez II
 */

public class GridMenu {

	/**
	 * Generates numbers from 0 <= n <= 9 and randomly populates 3 x 3 grid cells with them. 
	 * Then displays grid, solves it, and prints solutions to console.
	 */
	public static void main(String[] args) {
		GridSolver solver = new GridSolver();
		solver.generatePossibleNumbers();
		solver.populateGrid();
		solver.printGrid();
		solver.solveGrid();

	}

}
