package zombie;

import java.util.Random;

class Zombie extends Unit {
	Random ran = new Random();
	int power;
	
	public Zombie(int pos, int hp, int max) {
		super(pos, hp, max);
	}
	
	public void attack(Unit hero) {
		int heroHp = hero.getHp();
		int zombieHp = this.getHp();
		
		power = ran.nextInt(max) + 1;
		hero.setHp(hero.getHp() - power);
		this.setHp(this.getHp() - power / 2);
		
		System.out.printf("zombie가 %d의 power로 hero 공격\n", power);
		System.out.printf("hero의 hp : %d -> %d\n", heroHp, hero.getHp());
		System.out.printf("zombie의 hp : %d -> %d\n", zombieHp, this.getHp());
	}
}
