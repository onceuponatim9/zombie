package zombie;

import java.util.Scanner;

public class Game {
	Scanner scan = new Scanner(System.in);
	
	private final int SIZE = 20;
	private final int ZOMBIE = 1;
	private final int BOSS = 2;
	
	Hero hero;
	Zombie zombie;
	Boss boss;
	
	int count = 4;
	int win;
	int enemy;
	
	private int[] enemyList;
	
	private boolean isRun;
	
	private Game() {
		hero = new Hero(1, 200, 20, 5);
		zombie = new Zombie(5, 50, 10);
		boss = new Boss(SIZE - 2, 250, 20, 10);
		isRun = true;
		enemyList = new int[SIZE];
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
		return hero.getPos() == SIZE || !isRun ? false : true;
	}
	
	public void endMessage() {
		printPosition();
		System.out.printf("[%d/%d]회 이겼습니다. 게임을 종료합니다.\n", win, count);
	}
	
	public void printMap() {
		// 두 줄 : 윗줄은 hero, 아랫줄은 enemy
		for(int i = 1; i <= SIZE; i++) {
			if(hero.getPos() == i)
				System.out.printf("%2s", "★");
			else
				System.out.printf("%2s", "〓");
		}
		System.out.println();
		
		for(int i = 1; i <= SIZE; i++) {
//			if(hero.getPos() >= i && enemyList[i] == 0)
//				System.out.printf("%2s", "▣");
			if(hero.getPos() == i && (hero.getPos() == zombie.getPos() || hero.getPos() == boss.getPos()))
			System.out.printf("%2s", "▣");
			else if(hero.getPos() >= i && enemyList[i - 1] != 0)
				System.out.printf("%2s", "X");
			else
				System.out.printf("%2s", "〓");
//			if(hero.getPos() == i && (hero.getPos() == zombie.getPos() || hero.getPos() == boss.getPos()))
//				System.out.printf("%2s", "▣");
//			else
//				System.out.printf("%2s", "〓");
		}
		System.out.println();
	}
	
	public void playZombie() {
		count++;
		boolean isWin;
		
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
				win++;
				isWin = true;
				setEnemyList(zombie.getPos(), isWin);
				zombie = new Zombie(zombie.getPos() + 6, 50, 10);
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
		count++;
		boolean isWin;
		
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
				isWin = true;
				setEnemyList(boss.getPos(), isWin);
				win++;
				break;
			}
			else if(hero.getHp() == 0) {
				System.out.println("boss에게 졌습니다.");
				isRun = false;
				break;
			}
		}
	}
	
	public void setEnemyList(int enemyPos, boolean isWin) {
		// enemyList에 enemy 위치 넣기
		for(int i = 0; i < SIZE; i++)
			if(i == enemyPos - 1 && isWin)
				enemyList[i] = enemy;
	}
	
	public void printPosition() {
		System.out.printf("현재 위치 : %d\n", hero.getPos());
	}
	
	public void play() {
		printPosition();
		int select = inputNumber("앞으로 이동하기(1),종료하기(2)");
		
		if(select == 1) {
			hero.move();
			printMap();
			
			if(hero.getPos() == zombie.getPos()) {
				System.out.println("zombie를 만났습니다. 공격모드로 바뀝니다.");
				enemy = ZOMBIE;
				playZombie();
				
			} else if(hero.getPos() == boss.getPos()) {
				System.out.println("boss를 만났습니다. 공격모드로 바뀝니다.");
				enemy = BOSS;
				playBoss();
			}
		}
		
		else if(select == 2)
			isRun = false;
	}
	
	public void run() {
		//게임 진행
		printMap();
		while(isRun()) {
			play();
		}
		endMessage();
	}

}