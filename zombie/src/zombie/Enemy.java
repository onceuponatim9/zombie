package zombie;

public class Enemy {
	int position;
	int enemy;
	
	public Enemy(int position, int enemy) {
		this.position = position;
		this.enemy = enemy;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public int getEnemy() {
		return this.enemy;
	}
}
