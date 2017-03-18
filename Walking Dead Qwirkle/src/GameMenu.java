/**
 * GameMenu --- Main class to run Qwirkle game
 * 
 * @author Michael C. Velez II
 */

public class GameMenu {

	public static void main(String[] args) {
		// create game
		Qwirkle game = new Qwirkle(15, 15);
		
		// create both players
		Player player1 = new Player(1, 0, game.player1Bag, game.player1Hand);
		Player player2 = new Player(2, 0, game.player2Bag, game.player2Hand);
		
		// Run game with both players
		game.playGame(player1, player2);
		
		// helper function to print game board to console for my own debugging
		game.printBoard();
	}

}
