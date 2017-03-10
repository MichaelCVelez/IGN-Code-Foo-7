/**
 * GridSolver --- List of functions used to solve the grid.
 * 
 * @author Michael C. Velez II
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class GridSolver {
	
	//grid dimensions and area
	final int length = 3;
	final int width = 3;
	final int gridArea = length * width;
	
	//grid of numbers to be solved and boolean grid to track visited cells
	int[][] numberGrid = new int[length][width];
	boolean[][] initialVisitedGrid = new boolean[length][width];
	
	//list of chains/solutions and list of possible numbers to populate grid with
	List<List<Integer>> chainList = new ArrayList<List<Integer>>();
	List<Integer> possibleNumbers = new ArrayList<>();

	/** Generates the possible numbers 0 < n < 9 and fills a list with them. The list is then shuffled for randomization.
	 * 
	 */
	public void generatePossibleNumbers() {
		for (int i = 0; i <= gridArea; i++)
			possibleNumbers.add(i);
		Collections.shuffle(possibleNumbers);
	}

	/** Populates numberGrid with the numbers generated in list possibleNumbers.
	 * 
	 */
	public void populateGrid() {
		Iterator<Integer> iter = possibleNumbers.iterator();
		for (int i = 0; i < numberGrid.length; i++) {
			for (int j = 0; j < numberGrid[i].length; j++) {
				numberGrid[i][j] = iter.next();
			}
		}
	}

	/** Prints the grid of numbers to console.
	 * 
	 */
	public void printGrid() {
		System.out.println("Grid:");
		for (int i = 0; i < numberGrid.length; i++) {
			for (int j = 0; j < numberGrid[i].length; j++) {
				System.out.print(numberGrid[i][j] + " ");
			}
			System.out.println(); // separate rows
		}
		System.out.println(); 
	}

	/** Prints grid of booleans which tracks visited cells. I used this function to help when troubleshooting my program.
	 * 
	 * @param visitedGrid	Boolean grid to be printed.
	 */
	public void printVisitedGrid(boolean[][] visitedGrid) {
		for (int i = 0; i < visitedGrid.length; i++) {
			for (int j = 0; j < visitedGrid[i].length; j++) {
				System.out.print(visitedGrid[i][j]);
			}
			System.out.println(); // separate rows
		}
		System.out.println(); 
	}

	/** Uses iterator to print all chains which solve the grid.
	 * 
	 * @param chainList	The list of chains which are each a list of integers.
	 */
	public void printSolution(List<List<Integer>> chainList) {
		Iterator<List<Integer>> iter = chainList.iterator();
		System.out.println("Solution:");
		//while there are more chains in solution list
		while (iter.hasNext()) {
			printChain(iter.next());	//print chain
		}
	}

	/** Prints an individual chain in equation form.
	 * 
	 * @param chain
	 */
	public void printChain(List<Integer> chain) {
		Iterator<Integer> iter = chain.iterator();
		int i = 0;
		//while chain has more cells 
		while (iter.hasNext()) {
			System.out.print(chain.get(i));	//print current cell
			//print either + sign if not at end of chain, otherwise print = sign and gridArea
			if (i < chain.size() - 1) {
				System.out.print(" + ");
			} else {
				System.out.println(" = " + gridArea);
			}
			iter.next();
			i++;
		}
	}

	/** Checks if a coordinate (x,y) in grid is out of bounds in 2x2 array.
	 * 
	 * @param x	The horizontal x coordinate.
	 * @param y	The vertical y coordinate.
	 * @return	returns boolean true if out of bounds and false if not.
	 */
	public boolean isOutofBounds(int x, int y) {
		boolean outofBounds = false;
		if (x < 0 || y < 0 || x > width - 1 || y > length - 1) {
			outofBounds = true;
		}
		return outofBounds;
	}

	/** Initializes boolean grid with false in every cell.
	 * 
	 * @param visitedGrid	boolean grid to be initialized.
	 */
	public void initializeVisitedGrid(boolean[][] visitedGrid) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < length; y++) {
				visitedGrid[x][y] = false;
			}
		}
	}

	/** Copies one boolean array into another.
	 * 
	 * @param visitedGrid	original boolean array to be copied
	 * @param tempVisitedGrid	boolean array to be copied to
	 */
	public void copyArray(boolean[][] visitedGrid, boolean[][] tempVisitedGrid) {
		for (int i = 0; i < visitedGrid.length; i++)
			for (int j = 0; j < visitedGrid[i].length; j++)
				tempVisitedGrid[i][j] = visitedGrid[i][j];
	}

	/** Calculates the sum of the cells within a chain.
	 * 
	 * @param possibleChain The chain whose sum is to be calculated
	 * @return	chainSum	The sum of the chain.
	 */
	public int chainSumCalculator(List<Integer> possibleChain) {
		int chainSum = 0;
		for (int i = 0; i < possibleChain.size(); i++) {
			chainSum += possibleChain.get(i);
		}
		return chainSum;
	}

	/** Removes duplicate solutions from list of chains by sorting each chain and then converting the list to a set and then back to a list.
	 * 
	 */
	public void cleanUpChainList() {

		//sort each chain of numbers so that duplicates are more recognizable
		Iterator<List<Integer>> iter = chainList.iterator();
		while (iter.hasNext()) {
			Collections.sort(iter.next());
		}

		//convert list of chains to a set in order to remove duplicates
		List<List<Integer>> uniqueSolutions = new ArrayList<>(new LinkedHashSet<>(chainList));

		//clear original list and add chains from set back to list of chains
		chainList.clear();
		chainList.addAll(uniqueSolutions);

	}

	/** Function which uses recursion to solve grid at each adjacent cell.
	 * 
	 * @param x	Horizontal x coordinate of current cell being considered.
	 * @param y	Vertical y coordinate of current cell being considered.
	 * @param visitedGrid	Boolean grid tracking path of cells visited so far.
	 * @param possibleSolution	Current chain being considered and tested as a possible solution.
	 */
	public void recursionGrid(int x, int y, boolean[][] visitedGrid, List<Integer> possibleSolution) {
		
		if (chainSumCalculator(possibleSolution) > gridArea) {
			// chain sum is too large therefore exit function to avoid further testing of current chain.
			return;
		} else if (chainSumCalculator(possibleSolution) == gridArea && possibleSolution.size() >= width - 1) {
			// chain's sum is equal to grid's area and at least (width-1) cells are used, therefore it is a valid solution.
			chainList.add(possibleSolution);	//add solution to list of chains
			return;
		}
		
		// make copies of boolean grid and chain to be used for recursion
		boolean[][] tempVisitedGrid = new boolean[length][width];
		copyArray(visitedGrid, tempVisitedGrid);
		
		List<Integer> tempPossibleSolution = new ArrayList<Integer>(possibleSolution);

		// mark current cell as visited in copy of boolean grid
		tempVisitedGrid[x][y] = true;
		
		// add current cell's value to copy of chain to be tested
		tempPossibleSolution.add(numberGrid[x][y]);

		// check adjacent spots if they haven't been visited and aren't out of bounds

		if (!isOutofBounds(x + 1, y) && !tempVisitedGrid[x + 1][y]) {
			recursionGrid(x + 1, y, tempVisitedGrid, tempPossibleSolution); // check right adjacent cell
		}

		if (!isOutofBounds(x, y + 1) && !tempVisitedGrid[x][y + 1]) {
			recursionGrid(x, y + 1, tempVisitedGrid, tempPossibleSolution); // check above adjacent cell
		}

		if (!isOutofBounds(x - 1, y) && !tempVisitedGrid[x - 1][y]) {
			recursionGrid(x - 1, y, tempVisitedGrid, tempPossibleSolution); // check left adjacent cell
		}

		if (!isOutofBounds(x, y - 1) && !tempVisitedGrid[x][y - 1]) {
			recursionGrid(x, y - 1, tempVisitedGrid, tempPossibleSolution); // check below adjacent cell
		}
		
		// check diagonally adjacent cells
		if (!isOutofBounds(x + 1, y - 1) && !tempVisitedGrid[x + 1][y - 1]) {
			recursionGrid(x + 1, y - 1, tempVisitedGrid, tempPossibleSolution); // check top right adjacent cell 
		}

		if (!isOutofBounds(x - 1, y - 1) && !tempVisitedGrid[x - 1][y - 1]) {		
			recursionGrid(x - 1, y - 1, tempVisitedGrid, tempPossibleSolution); // check top left adjacent cell
		}

		if (!isOutofBounds(x + 1, y + 1) && !tempVisitedGrid[x + 1][y + 1]) {
			recursionGrid(x + 1, y + 1, tempVisitedGrid, tempPossibleSolution); // check bottom right adjacent cell
		}

		if (!isOutofBounds(x - 1, y + 1) && !tempVisitedGrid[x - 1][y + 1]) {
			recursionGrid(x - 1, y + 1, tempVisitedGrid, tempPossibleSolution); // check bottom left adjacent cell
		}

	}

	/** Function which loops through matrix and calls recursive function on every cell
	 * 
	 */
	public void solveGrid() {
		List<Integer> possibleSolution = new ArrayList<>();

		// for every spot in grid
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < length; y++) {
				// check spot
				recursionGrid(x, y, initialVisitedGrid, possibleSolution);
			}
		}
		// clean up list of chains and print them to console
		cleanUpChainList();
		printSolution(chainList);
	}

}
