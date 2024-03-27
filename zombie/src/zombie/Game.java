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
	int recoveryCount;
	
	public Hero(int pos, int hp, int max, int recoveryCount) {
		super(pos, hp, max);
		this.recoveryCount = recoveryCount;
	}
	
	public void attack(Unit unit) {
		
	}
	
	public void recovery() {
		if(recoveryCount > 0) {
			this.setHp(this.getHp() + 50);
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
		this.setHp(this.getHp() + power);
		
		System.out.printf("zombie가 %d의 power로 공격\n", power);
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
	
	public void attack(Unit hero) {
		int heroHp = hero.getHp();
		int bossHp = this.getHp();
		
		int rNum = ran.nextInt(4);
		if(rNum == 0) {
			power = 2 * ran.nextInt(max) + 1;
			System.out.println("boss의 필살기");
		}
		else
			power = ran.nextInt(max) + 1;
		
		hero.setHp(hero.getHp() - power);
		this.setHp(this.getHp() + power);
		
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
	
	private Game() {
		hero = new Hero(1, 200, 20, 2);
		zombie = new Zombie(5, 100, 10);
		boss = new Boss(9, 300, 20, 100);
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
	
	public void run() {
		//게임 진행
		while(true) {
			System.out.printf("현재 위치 : %d\n", hero.getPos());
			int select = inputNumber("앞으로 이동하기(1),종료하기(2)");
			
			if(select == 1) {
				hero.move();
			}
			else if(select == 2)
				break;
		}
	}

}