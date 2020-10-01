package Entity;

public class PlayerSave {
	
	private static int health = 5;
	
	public static void init() {
		health = 5;
	}
	
	public static int getHealth() { return health; }
	public static void setHealth(int i) { health = i; }
	
	
}
