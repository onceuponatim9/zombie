package zombie;

import java.util.Random;
import java.util.Scanner;

abstract class Unit {
	public Random ran;
	
	public int pos, hp, max;
	
	public Unit(int pos, int hp, int max) {
		this.pos = pos;
		this.hp = hp;
		this.max = max;
		ran = new Random();
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

class Hero extends Unit {
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

class Zombie extends Unit {
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

class Boss extends Zombie {
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
		
		int rNum = ran.nextInt(4);
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

public class Game {
	Scanner scan = new Scanner(System.in);
	
	Hero hero;
	Zombie zombie;
	Boss boss;
	
	int count;
	
	private boolean isRun;
	
	private Game() {
		hero = new Hero(1, 200, 20, 5);
		zombie = new Zombie(5, 100, 10);
		boss = new Boss(9, 300, 20, 10);
		isRun = true;
	}
	
	private static Game instance = new Game();
	public static Game getInstance() {
		return instance;
	}
	
	private int inputNumber(String message) {
		int number = -1;
		try {
			System.out.print(message + " : ");
			String input = scan.next();
			number = Integer.parseInt(input);
		} catch(Exception e) {
			System.out.println("숫자를 입력하세요.");
		}
		
		return number;
	}
	
	public boolean isRun() {
		return hero.getPos() == 10 || !isRun ? false : true;
	}
	
	public void endMessage() {
		System.out.printf("[%d/2]회 이겼습니다. 게임을 종료합니다.\n", count);
	}
	
	public void play() {
		System.out.printf("현재 위치 : %d\n", hero.getPos());
		int select = inputNumber("앞으로 이동하기(1),종료하기(2)");
		
		
		if(select == 1) {
			hero.move();
			
			if(hero.getPos() == zombie.getPos()) {
				System.out.println("zombie를 만났습니다. 공격모드로 바뀝니다.");
				
				while(true) {
					System.out.print("공격하기(1),포션마시기(2): ");
					int sel = scan.nextInt();
					
					if(sel == 1) {
						zombie.attack(hero);
						hero.attack(zombie);
						
					} else if(sel == 2)
						hero.recovery();
			            
					if(zombie.getHp() == 0) {
						System.out.println("zombie를 이겼습니다.");
						count++;
						break;
					}
					else if(hero.getHp() == 0) {
						System.out.println("zombie에게 졌습니다.");
						isRun = false;
						break;
					}
				}
				
			} else if(hero.getPos() == boss.getPos()) {
				System.out.println("boss를 만났습니다. 공격모드로 바뀝니다.");
				
				while(true) {
					System.out.print("공격하기(1),포션마시기(2): ");
					int sel = scan.nextInt();
					
					if(sel == 1) {
						boss.attack(hero);
						hero.attack(boss);
						
					} else if(sel == 2)
						hero.recovery();
					
					if(boss.getHp() == 0) {
						System.out.println("boss를 이겼습니다.");
						count++;
						break;
					}
					else if(hero.getHp() == 0) {
						System.out.println("boss에게 졌습니다.");
						isRun = false;
						break;
					}
				}
			}
		}
		
		else if(select == 2)
			isRun = false;
	}
	
	public void run() {
		//게임 진행
		while(isRun()) {
			play();
		}
		endMessage();
	}

}