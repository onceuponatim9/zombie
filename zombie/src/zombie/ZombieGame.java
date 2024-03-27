package zombie;

public class ZombieGame {
	
	public static void main(String[] args) {
		ZombieGame.run();
	}
	
	public static void run() {
		Game.getInstance().run();
	}
	
}