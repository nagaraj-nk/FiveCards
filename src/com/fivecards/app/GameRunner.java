package com.fivecards.app;

import java.util.List;
import java.util.Scanner;

import com.fivecards.model.Card;
import com.fivecards.model.Game;
import com.fivecards.model.Player;
import com.fivecards.utils.RandomGenerator;

public class GameRunner {

	private Game game;
	private Scanner scanner;
	private Player me, systemPlayer;
	private int round;
	private boolean myTurn;

	public GameRunner() {
		super();
		this.game = new Game();
		this.scanner = new Scanner(System.in);
		this.round = 0;
		this.myTurn = false;
	}

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
			System.out.println("Joker: " + game.getRandomJokerCard().getDisplayNumber());
			showCards(me);
			// showCards(systemPlayer);
			showPoints();

			System.out.println();
			System.out.println("Round: " + round);
			System.out.println("What are you going to do?");

			System.out.println("1. Challenge for points: " + game.calculatePoints(me));
			System.out.println("2. Pick Open card: " + game.getOpenCard().getDisplayNumber() + ", and Drop my choice");
			System.out.println("3. Pick card from deck, and Drop my choice");
			if (game.hasSameCards(me))
				System.out.println("4. Drop same cards");
			System.out.println("Type any other number to quit game");

			option = Integer.parseInt(scanner.nextLine());
			dispatchGameOption(option);
		} while (option >= 1 && option <= 5);
	}

	private void showPoints() {
		int points = game.calculatePoints(me);
		System.out.println("My current points: " + points);
		// System.out.println("System current points: " +
		// game.calculatePoints(systemPlayer));
		System.out.println();
	}

	private void dispatchGameOption(int option) {
		switch (option) {
		case 1: {
			challengeGame(me, systemPlayer);
			game.restart();
			initiateGame();
			break;
		}
		case 2: {
			pickOpenCardAndDropMyChoice(true);
			letSystemPlay();
			break;
		}
		case 3: {
			pickOpenCardAndDropMyChoice(false);
			letSystemPlay();
			break;
		}
		case 4: {

			if (game.hasSameCards(me)) {
				dropSameCards(me, game.getCardIndicesSameOfOpenCard(me));
				System.out.println("You dropped same cards");
				letSystemPlay();
			} else {
				System.out.println("You don't have same cards");
			}
			break;
		}
		case 5: {
			System.exit(0);
			break;
		}
		}
	}

	private void pickOpenCardAndDropMyChoice(boolean flag) {
		System.out.println("Which card you want to drop?");
		listMyCards();
		int cardIndex = Integer.parseInt(scanner.nextLine());
		cardIndex = cardIndex - 1;
		Card droppingCard = me.getCards().get(cardIndex);
		me.getCards().remove(cardIndex);
		if (flag)
			me.getCards().add(game.getOpenCard());
		else {
			Card pickedCard = game.pickCardFromDeck();
			me.getCards().add(pickedCard);
			System.out.println("Card from deck: " + pickedCard.getDisplayNumber());
		}
		game.setOpenCard(droppingCard);
		game.getDeck().addDroppedCard(droppingCard);
		System.out.println("You dropped: " + droppingCard.getDisplayNumber());
	}

	private void challengeGame(Player player1, Player player2) {
		int points = game.calculatePoints(player1);
		int points2 = game.calculatePoints(player2);

		System.out.println();
		System.out.println(player1.getName() + " challenged for : " + points + " points");
		System.out.println(player2.getName() + " points: " + points2);
		System.out.println();

		if (points < points2) {
			System.out.println(player1.getName() + " won the game");
			player2.updatePoints(points2);
		} else if (points2 < points) {
			System.out.println(player2.getName() + " won the game");
			player1.updatePoints(points);
		} else if (points == points2) {
			System.out.println("Both are equal, " + player1.getName() + " lost game");
			player1.updatePoints(points);
		}
		displayPoints();
	}

	private void displayPoints() {
		System.out.println("Your game points: " + me.getPoints());
		System.out.println("System game points: " + systemPlayer.getPoints());
	}

	private void letSystemPlay() {
		if (game.getOpenCard().getValue() >= 6) {
			List<Integer> cardIndices = game.getCardIndicesSameOfOpenCard(systemPlayer);
			if (cardIndices.size() > 0) {
				dropSameCards(systemPlayer, cardIndices);
				System.out.println("System dropped same cards: " + game.getOpenCard().getDisplayNumber());
			} else {
				challengeOrPickBySystem();
			}
		} else {
			challengeOrPickBySystem();
		}
	}

	public void challengeOrPickBySystem() {
		if (game.calculatePoints(systemPlayer) <= game.getSystemChallengingPoint()) {
			challengeBySystem();
		} else {
			pickEitherOpenCardOrFromDeck();
		}
	}

	private void challengeBySystem() {
		challengeGame(systemPlayer, me);
		game.restart();
		initiateGame();
	}

	private void pickEitherOpenCardOrFromDeck() {
		Card droppingCard = game.getBiggestCard(systemPlayer);
		systemPlayer.dropCard(droppingCard);
		System.out.println("System dropped: " + droppingCard.getDisplayNumber());
		if (game.getOpenCard().getValue() <= 5) {
			systemPlayer.getCards().add(game.getOpenCard());
			System.out.println("System picked open card");
		} else {
			Card card = game.pickCardFromDeck();
			systemPlayer.getCards().add(card);
			System.out.println("System picked card from deck");
		}
		game.setOpenCard(droppingCard);
	}

	private void dropSameCards(Player player, List<Integer> cardIndices) {
		for (int i = 0; i < cardIndices.size(); i++) {
			Card card = player.getCards().get(cardIndices.get(i));
			game.getDeck().addDroppedCard(card);
			player.getCards().remove((int) cardIndices.get(i));
		}
		// System.out.println("after dropping...");
		// showCards(player);
	}

	private void initiateGame() {
		System.out.println();
		round++;
		if (!myTurn)
			System.out.println("System is shuffling cards...");
		else
			System.out.println("You are shuffling cards...");
		game.startGame();
		game.showRandomJokerCard();
		Card card = game.pickCardFromDeck();
		game.setOpenCard(card);
		System.out.println("Open card: " + game.getOpenCard().getDisplayNumber());
		myTurn = !myTurn;

		showCards(me);
		// showCards(systemPlayer);

		if (!myTurn)
			letSystemPlay();
	}

	private void showCards(Player player) {
		System.out.print(player.getName() + " Cards: ");
		for (Card card : player.getCards()) {
			System.out.print(card.getDisplayNumber() + "  ");
		}
		System.out.println();
	}

	private void listMyCards() {
		System.out.println("My Cards: ");
		for (int i = 0; i < me.getCards().size(); i++) {
			Card card = me.getCards().get(i);
			System.out.println((i + 1) + ". " + card.getDisplayNumber());
		}
	}

	private void addPlayers() {
		System.out.println("Enter your name");

		String name = scanner.nextLine();
		me = new Player();
		me.setName(name);
		me.setId(1);

		systemPlayer = new Player();
		systemPlayer.setName("System");
		systemPlayer.setId(2);

		game.addPlayer(me);
		game.addPlayer(systemPlayer);
	}
}
