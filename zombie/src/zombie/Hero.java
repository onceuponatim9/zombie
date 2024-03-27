package zombie;

import java.util.Random;

class Hero extends Unit {
	Random ran = new Random();
	
	int power, recoveryCount;
	
	public Hero(int pos, int hp, int max, int recoveryCount) {
		super(pos, hp, max);
		this.recoveryCount = recoveryCount;
	}
	
	// 보스인지 아닌지 구분해서 공격
	public void attack(Unit unit) {
		power = ran.nextInt(max) + 1;
		
		int heroHp = this.getHp();
		int enemyHp = unit.getHp();
		
		String name = "";
		
		if(unit instanceof Boss) {
			Boss boss = (Boss) unit;
			name = "boss";
			
			// boss는 shield 사용
			if(boss.getShield() > 0) {
				boss.setShield(boss.getShield() - 1);
				System.out.println("boss는 공격을 방어했습니다.");
				this.setHp(this.getHp() - power / 2);
				System.out.printf("남은 shield : %d개\n", boss.getShield());
			} else {
				unit.setHp(unit.getHp() - power);
				this.setHp(this.getHp() - power / 2);
			}
			
		} else {
			name = "zombie";
			unit.setHp(unit.getHp() - power);
			this.setHp(this.getHp() - power / 2);
		}
		
		System.out.printf("hero가 %d의 power로 %s 공격\n", power, name);
		System.out.printf("hero의 hp : %d -> %d\n", heroHp, this.getHp());
		System.out.printf("%s의 hp : %d -> %d\n", name, enemyHp, unit.getHp());
	}
	
	public void recovery() {
		if(recoveryCount > 0) {
			this.setHp(this.getHp() + 100);
			recoveryCount--;
			System.out.printf("hp 충전 완료) 현재 hp는 %d입니다.\n", this.getHp());
			
		} else {
			System.out.println("회복약을 모두 사용했습니다.");
		}
	}
}
