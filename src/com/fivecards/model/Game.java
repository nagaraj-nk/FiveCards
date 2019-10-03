package com.fivecards.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

	public static final int CARD_COUNT = 5;

	private List<Player> players;
	private Deck deck;
	private Card randomJokerCard;
	private Card openCard;

	public Game() {
		super();
		this.players = new ArrayList<>();
		this.deck = new Deck();
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void startGame() {
		for (int i = 0; i < players.size(); i++) {
			List<Card> cards = deck.getCardsForPlayer();
			players.get(i).setCards(cards);
		}
	}

	public void showPlayerCards() {
		for (int i = 0; i < players.size(); i++) {
			System.out.println("Player: " + players.get(i).getName());
			List<Card> cards = players.get(i).getCards();
			for (int j = 0; j < cards.size(); j++) {
				System.out.println(cards.get(j).getDisplayNumber());
			}
			System.out.println();
		}
	}

	public void showRandomJokerCard() {
		double num = deck.getCards().size();
		int randomInt = (int) (num * Math.random());
		randomJokerCard = deck.getCards().get(randomInt);
		deck.getCards().remove(randomInt);
		System.out.println("Joker card: " + randomJokerCard.getDisplayNumber());
	}

	public void pickCardFromDeck() {
		double num = deck.getCards().size();
		int randomInt = (int) (num * Math.random());
		openCard = deck.getCards().get(randomInt);
		deck.getCards().remove(randomInt);
		System.out.println("Open card: " + openCard.getDisplayNumber());
	}

	public int calculatePoints(Player player) {
		int points = 0;
		for (int i = 0; i < player.getCards().size(); i++) {
			points = points + player.getCards().get(i).getValue();
		}
		return points;
	}
}
