/**
 * Qwirkle --- Holds all game functions and used to create JFrame Window
 * 
 * @author Michael C. Velez II
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Qwirkle {

	// Board dimensions. I decided to use a 225 tile board (15 x 15)
	final int length = 15;
	final int width = 15;

	// value of a non-hero/villain tile
	final int walkerValue = 1;

	// 2D array for tracking placement of tiles
	CharacterTile[][] board = new CharacterTile[length][width];

	// Board of buttons for displaying game
	JButton[][] buttonBoard = new JButton[length][width];

	// Lists of possible colors and types of tiles
	List<String> colors = Arrays.asList("Red", "Orange", "Yellow", "Green", "Blue", "Purple");
	List<String> walkerTypes = Arrays.asList("Bicycle Walker", "Riot Gear Walker", "Floater Walker", "Burnt Walker",
			"Bleeding Eyes Walker", "Spiked Walker");

	// List to hold all possible walker tiles
	List<CharacterTile> possibleWalkers = new ArrayList<>();

	// Lists to hold player bags and hands of tiles
	List<CharacterTile> player1Bag = new ArrayList<>();
	List<CharacterTile> player1Hand = new ArrayList<>();
	List<CharacterTile> player2Bag = new ArrayList<>();
	List<CharacterTile> player2Hand = new ArrayList<>();

	// create both players
	Player player1 = new Player(1, 0, player1Bag, player1Hand);
	Player player2 = new Player(2, 0, player2Bag, player1Hand);

	// Tile currently selected by player
	CharacterTile activeTile = new CharacterTile(0, 0, null, 0, false, null, null);

	// Jframe, panels and labels for displaying game to user
	JFrame frame = new JFrame("Walking Dead Quirkle");
	JPanel topPanel = new JPanel();
	JLabel title = new JLabel("Walking Dead Quirkle");
	JPanel scorePanel = new JPanel();
	JPanel playerHand = new JPanel();
	JButton[][] grid;
	JLabel activeTileLabel = new JLabel("Active Tile: ");
	JLabel score1 = new JLabel("Player 1 Score: 0");
	JLabel score2 = new JLabel("Player 2 Score: 0");
	JLabel lineBreak = new JLabel("                            ");

	/**
	 * Constructor which runs the main logic for qwirkle game
	 * 
	 */
	public Qwirkle(int width, int length) {

		// Set up display
		JPanel boardPanel = new JPanel(new GridLayout(width, length));
		frame.setLayout(new BorderLayout()); // set layout
		topPanel.add(title);
		scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
		scorePanel.add(score1);
		scorePanel.add(score2);
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(scorePanel, BorderLayout.WEST);
		frame.add(boardPanel, BorderLayout.CENTER);
		frame.add(playerHand, BorderLayout.SOUTH);
		grid = new JButton[width][length]; // allocate the size of grid

		initializeBoard();

		// loop through grid and add buttons
		for (int y = 0; y < length; y++) {
			for (int x = 0; x < width; x++) {
				JButton gridSpot = new JButton("");

				// behavior of button when clicked
				gridSpot.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// check if button has been clicked before
						if (gridSpot.getText() == "") {
							JButton selectedBtn = (JButton) e.getSource();
							// loop through board of buttons to find button that
							// is being selected
							for (int row = 0; row < buttonBoard.length; row++) {
								for (int col = 0; col < buttonBoard[row].length; col++) {
									if (buttonBoard[row][col] == selectedBtn) {
										// check if move is valid
										if (isValidMove(activeTile, row, col)) {
											// if valid, set button's label to
											// be the character or walker's name
											board[row][col] = getCharacter(activeTile.name);
											// Update score for player
											score1.setText(
													"Player 1 Score: " + (player1.totalScore + activeTile.value));
											gridSpot.setText(activeTile.name);
										}
									}
								}
							}

							// used for debugging
							//printBoard();
						}
					}
				});
				// Add newly created button to grid
				grid[x][y] = gridSpot;
				buttonBoard[x][y] = gridSpot;
				boardPanel.add(grid[x][y]);
			}
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); // sets appropriate size for frame
		frame.setVisible(true); // makes frame visible
	}

	/**
	 * Retrieves character tile from possible tile list that matches the name
	 * input
	 * 
	 * @param name
	 *            The name to be returned.
	 * @return CharacterTile with the same name as string parameter.
	 */
	public CharacterTile getCharacter(String name) {
		CharacterTile character = null;
		Iterator<CharacterTile> iter = possibleWalkers.iterator();
		int i = 0;
		while (iter.hasNext()) {
			if (name == possibleWalkers.get(i).name) {
				character = possibleWalkers.get(i);
				return character;
			}
			iter.next();
			i++;
		}
		return character;
	}

	/**
	 * Checks if a coordinate (x,y) in grid is out of bounds in 2x2 array.
	 * (Reused code from GridSolver.java)
	 * 
	 * @param x
	 *            The horizontal x coordinate.
	 * @param y
	 *            The vertical y coordinate.
	 * @return returns boolean true if out of bounds and false if not.
	 */
	public boolean isOutofBounds(int x, int y) {
		boolean outofBounds = false;
		if (x < 0 || y < 0 || x > width - 1 || y > length - 1) {
			outofBounds = true;
		}
		return outofBounds;
	}

	/**
	 * Checks for validity of a move made by player and returns boolean
	 * accordingly.
	 * 
	 * @param character
	 *            The character tile which is being played.
	 * @param i
	 *            The horizontal coordinate of tile placement.
	 * @param j
	 *            The vertical coordinate of tile placement.
	 * @return returns boolean false if move isn't valid and true if valid.
	 */
	public boolean isValidMove(CharacterTile character, int i, int j) {
		boolean answer = true;
		int x = i;
		int y = j;

		// check line going right
		while (!isOutofBounds(x + 1, y)) {
			if (board[x + 1][y].name == character.name) {// && board[x +
															// 1][y].type!=character.type){
				answer = false;
			}
			if (board[x + 1][y].name != "") {
				// check for non matching tiles
				if (board[x + 1][y].color != character.color && board[x + 1][y].type != character.type) {
					answer = false;
				}
			}
			x++;
		}
		//reset x and y for next check
		x = i;
		y = j;

		// check line going left
		while (!isOutofBounds(x - 1, y)) {
			if (board[x - 1][y].name == character.name) {// && board[x -
															// 1][y].type!=character.type){
				answer = false;
			}
			if (board[x - 1][y].name != "") {
				// check for non matching tiles
				if (board[x - 1][y].color != character.color && board[x - 1][y].type != character.type) {
					answer = false;
				}
			}
			x--;
		}

		//reset x and y for next check
		x = i;
		y = j;

		// check line going up
		while (!isOutofBounds(x, y + 1)) {
			// check for duplicates
			if (board[x][y + 1].name == character.name) {// && board[x][y +
															// 1].type!=character.type){
				answer = false;
			}
			if (board[x][y + 1].name != "") {
				// check for non matching tiles
				if (board[x][y + 1].color != character.color && board[x][y + 1].type != character.type) {
					answer = false;
				}
			}
			y++;
		}

		//reset x and y for next check
		x = i;
		y = j;

		// check line going down
		while (!isOutofBounds(x, y - 1)) {
			if (board[x][y - 1].name == character.name) {// && board[x][y -
															// 1].type!=character.type){
				answer = false;
			}
			if (board[x][y - 1].name != "") {
				// check for non matching tiles
				if (board[x][y - 1].color != character.color && board[x][y - 1].type != character.type) {
					answer = false;
				}
			}
			y--;
		}
		
		return answer;
	}

	/**
	 * Initializes board with empty valued character tiles. 
	 * 
	 */
	public void initializeBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new CharacterTile(i, j, "", 0, false, "", "");
			}
		}
	}

	/**
	 * Helper function to print board for use in debugging. 
	 * 
	 */
	public void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print("[");
				if (board[i][j].name == "") {
					System.out.print("        ");
				} else {
					System.out.print(board[i][j].name);
				}
				System.out.print("]");
			}
			System.out.println();
		}
	}

	/**
	 * Generates all possible walker tiles and places them in bag to be split between players
	 * 
	 */
	public void generatePossibleWalkers() {
		// loop 3 times to get 108 tiles
		for (int x = 0; x < 3; x++) {
			// create a tile for each type of walker for each type of color
			for (int i = 0; i < colors.size(); i++) {
				for (int j = 0; j < walkerTypes.size(); j++) {
					String walkerName = colors.get(i) + " " + walkerTypes.get(j);
					CharacterTile walker = new CharacterTile(0, 0, walkerName, walkerValue, false, walkerTypes.get(j),
							colors.get(i));
					possibleWalkers.add(walker);
				}
			}
		}
	}

	/**
	 * Helper function for printing a bag of tiles. Used for initial debugging.
	 * 
	 * @param playerBag
	 *            List of tiles to be printed.
	 */
	public void printTiles(List<CharacterTile> playerBag) {
		Iterator<CharacterTile> iter = playerBag.iterator();
		int i = 0;
		while (iter.hasNext()) {
			System.out.println(playerBag.get(i).name);
			iter.next();
			i++;
		}
	}

	/**
	 * Split bag of all possible tiles randomly between both players.
	 * 
	 */
	public void splitBag() {
		//randomize tiles
		Collections.shuffle(possibleWalkers);
		int i = 0;
		//split bag in exactly half
		while (i < 54) {
			player1Bag.add(possibleWalkers.get(i));
			i++;
		}
		while (i < possibleWalkers.size()) {
			player2Bag.add(possibleWalkers.get(i));
			i++;
		}
	}

	/**
	 * Refills hand of player after they have made a move.
	 * 
	 * @param p
	 *            The player whose hand must be refilled.
	 */
	public void fillHand(Player p) {
		int i = 0;
		while (p.playerHand.size() < 6) {
			CharacterTile currentTile = p.tileBag.get(i);
			p.playerHand.add(currentTile);
			//Logic for when player selects an active tile to use during their move.
			JButton tile = new JButton(currentTile.name);
			tile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//display currently selected tile to user
					activeTile = currentTile;
					activeTileLabel.setText("Active Tile: " + activeTile.name);
				}
			});
			playerHand.add(tile);
			p.tileBag.remove(i);
			i++;
		}
		playerHand.add(lineBreak);
		//skip turn button whose logic was never fully developed.
		playerHand.add(new JButton("Pass Turn"));
		playerHand.add(activeTileLabel);
	}

	/**
	 * Function for running game with two players. 
	 * Code is incomplete but would have contained the main logic of the game.
	 * 
	 */	
	public void playGame(Player p1, Player p2) {	
		// generate possible tiles
		generatePossibleWalkers();
		// split tile bag in half
		splitBag();
		// create both players
		// generate hero tiles for player 1 and villain tiles for player 2
		p1.generatePossibleCharacters();
		p2.generatePossibleCharacters();
		
		fillHand(p1);
		while (!p1.tileBag.isEmpty() || !p2.tileBag.isEmpty()) {
			/*  Game logic would have been here.
			    While(each players bag is not empty or only heroes remain){
				Each player must:
				either make a move or give up turn for different tiles.
				if (move is made) {
					Player 2's AI would have:
					Checked each line in board and kept track of points possible for each card in deck
					Made decision based on highest points possible
				} 
				place on board and pull tiles from bag to refill hand of 6 
				next players turn
				}
			*/
		}

	}

}
