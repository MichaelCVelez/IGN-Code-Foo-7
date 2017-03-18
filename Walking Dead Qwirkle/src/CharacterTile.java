/**
 * CharacterTile --- Class used to create a CharacterTile object which
 * represents all the different tile combinations in the game
 * 
 * @author Michael C. Velez II
 */

public class CharacterTile {
	public int x = 0;
	public int y = 0;
	public String name = new String();
	public int value = 0;
	public boolean isHero = false;
	public String type = new String();
	public String color = new String();

	/**
	 * Constructor for creating a CharacterTile
	 * 
	 * @param a
	 *            The horizontal coordinate of tile         
	 * @param b
	 *            The vertical coordinate of tile   
	 * @param tileName
	 *            The name of the tile
	 * @param val
	 *            The tile's value
	 * @param heroFlag
	 *            The boolean flag for determining if a tile is a hero/villain or just a walker tile
	 * @param tileType
	 *            The type of tile (or shape as in the original version of qwirkle)                
	 * @param tileColor
	 *            The color of the tile.        
	 */
	public CharacterTile(int a, int b, String tileName, int val, boolean heroFlag, String tileType, String tileColor) {
		x = a;
		y = b;
		name = tileName;
		value = val;
		isHero = heroFlag;
		type = tileType;
		color = tileColor;
	}
}
