package zombie;

import java.util.Scanner;

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
	
	public void playZombie() {
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
	}
	
	public void playBoss() {
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
	
	public void play() {
		System.out.printf("현재 위치 : %d\n", hero.getPos());
		int select = inputNumber("앞으로 이동하기(1),종료하기(2)");
		
		if(select == 1) {
			hero.move();
			
			if(hero.getPos() == zombie.getPos()) {
				System.out.println("zombie를 만났습니다. 공격모드로 바뀝니다.");
				playZombie();
				
			} else if(hero.getPos() == boss.getPos()) {
				System.out.println("boss를 만났습니다. 공격모드로 바뀝니다.");
				playBoss();
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