package zombie;

abstract class Unit {
	public int pos, hp, max;
	
	public Unit(int pos, int hp, int max) {
		this.pos = pos;
		this.hp = hp;
		this.max = max;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getPos() {
		return pos;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
		if(this.hp < 0)
			this.hp = 0;
	}
	public int getMax() {
		return max;
	}
	
	public void move() {
		this.setPos(getPos() + 1);
	}
	
	abstract void attack(Unit unit);
}
