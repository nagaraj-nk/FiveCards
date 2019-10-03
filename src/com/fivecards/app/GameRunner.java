package com.fivecards.app;

import java.util.Scanner;

import com.fivecards.model.Game;
import com.fivecards.model.Player;

public class GameRunner {

	private Game game = new Game();

	public void runGame() {
		int option = 0;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Five Cards");
			System.out.println("1. Play with system");
			System.out.println("2. Exit");

			option = scanner.nextInt();

			dispatchOption(option);

		} while (option >= 1 && option <= 2);
		scanner.close();
	}

	private void dispatchOption(int option) {
		switch (option) {
		case 1: {
			playWithSystem();
			break;
		}
		case 2: {
			System.exit(0);
			break;
		}
		}
	}

	private void playWithSystem() {
		int option = 0;
		Scanner scanner = new Scanner(System.in);
		addPlayers();
		initiateGame();
		do {
			System.out.println("1. Pick card from deck");
			System.out.println("2. Pick opponent's card");

			option = scanner.nextInt();
			dispatchOption(option);
		} while (option >= 1 && option <= 2);
		scanner.close();

	}

	private void initiateGame() {
		System.out.println("Shuffling cards...");
		game.startGame();
	}
	
	private void showMyCards() {
	}

	private void addPlayers() {
		System.out.println("Enter your name");
		Scanner scanner = new Scanner(System.in);

		String name = scanner.nextLine();
		Player mePlayer = new Player();
		mePlayer.setName(name);
		mePlayer.setId(1);

		Player systemPlayer = new Player();
		systemPlayer.setName("System");
		systemPlayer.setId(2);

		game.addPlayer(mePlayer);
		game.addPlayer(systemPlayer);
		scanner.close();
	}
}
