package zombie;

import java.util.Random;

class Boss extends Zombie {
	Random ran = new Random();
	
	private int shield, power;
	
	public Boss(int pos, int hp, int max, int shield) {
		super(pos, hp, max);
		this.shield = shield;
	}
	
	public void setShield(int shield) {
		this.shield = shield;
	}
	
	public int getShield() {
		return this.shield;
	}
	
	public void attack(Unit hero) {
		int heroHp = hero.getHp();
		int bossHp = this.getHp();
		
		int rNum = super.ran.nextInt(4);
		if(rNum == 0) {
			power = 2 * ran.nextInt(max) + 1;
			System.out.println("boss의 필살기");
		}
		else {
			power = ran.nextInt(max) + 1;
			System.out.println("boss의 일반 공격");
		}
		
		hero.setHp(hero.getHp() - power);
		this.setHp(this.getHp() - power / 2);
		
		System.out.printf("boss가 %d의 power로 공격\n", power);
		System.out.printf("hero의 hp : %d -> %d\n", heroHp, hero.getHp());
		System.out.printf("boss의 hp : %d -> %d\n", bossHp, this.getHp());
	}
}
