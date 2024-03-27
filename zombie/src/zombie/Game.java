package zombie;

import java.util.Random;
import java.util.Scanner;

abstract class Unit {
	Random ran;
	
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
	}
	public int getMax() {
		return max;
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
	
	public void move() {
		
	}
}

class Zombie extends Unit {
	public Zombie(int pos, int hp, int max) {
		super(pos, hp, max);
	}
	
	public void attack(Unit unit) {
		
	}
	
	public void move() {
		
	}
}

class Boss extends Unit {
	private int shield;
	
	public Boss(int pos, int hp, int max, int shield) {
		super(pos, hp, max);
		this.shield = shield;
	}
	
	public void attack(Unit unit) {
		
	}
	
	public void move() {
		
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