
/**
 * Player --- Class used to create a player object and holds functions unique to player.
 * 
 * @author Michael C. Velez II
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
	public int playerNumber;
	public int totalScore = 0;
	List<CharacterTile> tileBag = new ArrayList<>();
	List<CharacterTile> playerHand = new ArrayList<>();

	/**
	 * Constructor for creating a Player
	 * 
	 */
	public Player(int num, int x, List<CharacterTile> tileList, List<CharacterTile> pHand) {
		// Players number (p1 or p2), total score, bag of tiles, and current
		// hand of 6 tiles
		playerNumber = num;
		totalScore = x;
		tileBag = tileList;
		pHand = playerHand;
	}

	/**
	 * Generate either heroes or villains depending on Player's number.
	 * 
	 */
	public void generatePossibleCharacters() {
		if (playerNumber == 1) {
			generateHeroes();
		} else if (playerNumber == 2) {
			generateVillains();
		}
		Collections.shuffle(tileBag);
	}

	/**
	 * Generate hero tiles and add them to player's bag
	 * 
	 */
	public void generateHeroes() {
		CharacterTile Rick = new CharacterTile(0, 0, "Rick", 12, true, "Character", "Spiked");
		CharacterTile Michonne = new CharacterTile(0, 0, "Michonne", 11, true, "Character", "Bleeding Eyes");
		CharacterTile Carol = new CharacterTile(0, 0, "Carol", 10, true, "Character", "Burnt");
		CharacterTile Glenn = new CharacterTile(0, 0, "Glenn", 9, true, "Character", "Floater");
		CharacterTile Daryl = new CharacterTile(0, 0, "Daryl", 8, true, "Character", "Bicycle");
		CharacterTile Carl = new CharacterTile(0, 0, "Carl", 7, true, "Character", "Riot Gear");
		tileBag.add(Rick);
		tileBag.add(Michonne);
		tileBag.add(Carol);
		tileBag.add(Glenn);
		tileBag.add(Daryl);
		tileBag.add(Carl);
	}

	/**
	 * Generate villain tiles and add them to player's bag
	 * 
	 */
	public void generateVillains() {
		CharacterTile Negan = new CharacterTile(0, 0, "Negan", 12, true, "Character", "Spiked");
		CharacterTile Governor = new CharacterTile(0, 0, "Governor", 11, true, "Character", "Bleeding Eyes");
		CharacterTile Gareth = new CharacterTile(0, 0, "Gareth", 10, true, "Character", "Burnt");
		CharacterTile Shane = new CharacterTile(0, 0, "Shane", 9, true, "Character", "Floater");
		CharacterTile Dwight = new CharacterTile(0, 0, "Dwight", 8, true, "Character", "Bicycle");
		CharacterTile Dawn = new CharacterTile(0, 0, "Dawn", 7, true, "Character", "Riot Gear");
		tileBag.add(Negan);
		tileBag.add(Governor);
		tileBag.add(Gareth);
		tileBag.add(Shane);
		tileBag.add(Dwight);
		tileBag.add(Dawn);
	}

}
