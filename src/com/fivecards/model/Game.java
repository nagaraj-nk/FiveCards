package com.fivecards.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	public static final int CARD_COUNT = 5;

	private List<Player> players;
	private Deck deck;

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
			System.out.println("Player: "+ players.get(i).getName());
			List<Card> cards = players.get(i).getCards();
			for (int j = 0; j < cards.size(); j++) {
				System.out.println(cards.get(j).getDisplayNumber());
			}
			System.out.println();
		}
	}
}
