package com.fivecards.app;

import java.util.Scanner;

import com.fivecards.model.Card;
import com.fivecards.model.Game;
import com.fivecards.model.Player;

public class GameRunner {

	private Game game = new Game();
	Scanner scanner = new Scanner(System.in);
	private Player me;

	public void runGame() {
		int option = 0;

		do {
			System.out.println("Five Cards");
			System.out.println("1. Play with system");
			System.out.println("2. Exit");

			option = Integer.parseInt(scanner.nextLine());
			dispatchOption(option);
		} while (option >= 1 && option <= 2);
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
		addPlayers();
		initiateGame();
		do {
			showMyCards();
			showMyPoints();
			System.out.println("What are you going to do?");

			System.out.println("1. Challenge for points: " + game.calculatePoints(me));
			System.out.println("2. Pick Open card: " + game.getOpenCard().getDisplayNumber() + ", and Drop my choice");
			System.out.println("3. Tally same cards");
			option = Integer.parseInt(scanner.nextLine());
			dispatchGameOption(option);
		} while (option >= 1 && option <= 2);
	}

	private void showMyPoints() {
		int points = game.calculatePoints(me);
		System.out.println("My Points: " + points);
		System.out.println();
	}

	private void dispatchGameOption(int option) {
		switch (option) {
		case 1: {
			
			break;
		}
		case 2: {
			pickOpenCardAndDropMyChoice();
			break;
		}
		}
	}

	private void pickOpenCardAndDropMyChoice() {
		System.out.println("Which card you want to drop?");
		
	}

	private void initiateGame() {
		System.out.println();
		System.out.println("System is shuffling cards...");
		game.startGame();
		game.showRandomJokerCard();
		game.pickCardFromDeck();
	}

	private void showMyCards() {
		System.out.print("My Cards: ");
		for (Card card : me.getCards()) {
			System.out.print(card.getDisplayNumber() + "  ");
		}
		System.out.println();
	}

	private void addPlayers() {
		System.out.println("Enter your name");

		String name = scanner.nextLine();
		me = new Player();
		me.setName(name);
		me.setId(1);

		Player systemPlayer = new Player();
		systemPlayer.setName("System");
		systemPlayer.setId(2);

		game.addPlayer(me);
		game.addPlayer(systemPlayer);
	}
}
