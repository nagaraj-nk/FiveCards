package com.fivecards.app;

import java.util.List;
import java.util.Scanner;

import com.fivecards.model.Card;
import com.fivecards.model.Game;
import com.fivecards.model.Player;
import com.fivecards.utils.RandomGenerator;

public class GameRunner {

	private Game game = new Game();
	Scanner scanner = new Scanner(System.in);
	private Player me, systemPlayer;

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
			showCards(me);
			showCards(systemPlayer);
			showPoints();
			System.out.println("What are you going to do?");

			System.out.println("1. Challenge for points: " + game.calculatePoints(me));
			System.out.println("2. Pick Open card: " + game.getOpenCard().getDisplayNumber() + ", and Drop my choice");
			System.out.println("3. Pick card from deck, and Drop my choice");
			System.out.println("4. Drop same cards");
			option = Integer.parseInt(scanner.nextLine());
			dispatchGameOption(option);
		} while (option >= 1 && option <= 2);
	}

	private void showPoints() {
		int points = game.calculatePoints(me);
		System.out.println("My Points: " + points);
		System.out.println("System points: " + game.calculatePoints(systemPlayer));
		System.out.println();
	}

	private void dispatchGameOption(int option) {
		switch (option) {
		case 1: {
			challengeGame();
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
			dropSameCards(me, game.getCardIndicesSameOfOpenCard(me));
			System.out.println("You dropped same cards");
			letSystemPlay();
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
		else
			me.getCards().add(game.pickCardFromDeck());
		game.setOpenCard(droppingCard);
		game.getDeck().addDroppedCard(droppingCard);
		System.out.println("You dropped: " + droppingCard.getDisplayNumber());
	}

	private void challengeGame() {
		int points = game.calculatePoints(me);
		System.out.println();
		System.out.println("You are challenging for points: " + points);
		System.out.println();

		int systemPoints = game.calculatePoints(systemPlayer);

		if (points < systemPoints) {
			System.out.println("You won the game");
			systemPlayer.updatePoints(systemPoints);
		} else {
			System.out.println("System won the game");
			me.updatePoints(points);
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
				pickEitherOpenCardOrFromDeck();
			}
		} else {
			pickEitherOpenCardOrFromDeck();
		}
	}

	private void pickEitherOpenCardOrFromDeck() {
		int option = RandomGenerator.generateRandomNumber(1, 2);
		Card droppingCard = game.getBiggestCard(systemPlayer);
		systemPlayer.dropCard(droppingCard);
		game.setOpenCard(droppingCard);
		System.out.println("System dropped: " + game.getOpenCard().getDisplayNumber());
		if (option == 1) {
			systemPlayer.getCards().add(game.getOpenCard());
			System.out.println("System picked open card");
		} else if (option == 2) {
			Card card = game.pickCardFromDeck();
			systemPlayer.getCards().add(card);
			System.out.println("System picked card from deck");
		}
	}

	private void dropSameCards(Player player, List<Integer> cardIndices) {
		for (int i = 0; i < cardIndices.size(); i++) {
			Card card = player.getCards().get(cardIndices.get(i));
			game.getDeck().addDroppedCard(card);
			player.getCards().remove((int) cardIndices.get(i));
		}
	}

	private void initiateGame() {
		System.out.println();
		System.out.println("System is shuffling cards...");
		game.startGame();
		game.showRandomJokerCard();
		Card card = game.pickCardFromDeck();
		game.setOpenCard(card);
		System.out.println("Open card: " + game.getOpenCard().getDisplayNumber());
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
