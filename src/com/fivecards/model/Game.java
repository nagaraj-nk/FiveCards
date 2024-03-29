package com.fivecards.model;

import java.util.ArrayList;
import java.util.List;

import com.fivecards.utils.RandomGenerator;

public class Game {

	public static final int CARD_COUNT = 5;

	private List<Player> players;
	private Deck deck;
	private Card randomJokerCard;
	private Card openCard;
	private int systemChallengingPoint;

	public Card getRandomJokerCard() {
		return randomJokerCard;
	}

	public void setRandomJokerCard(Card randomJokerCard) {
		this.randomJokerCard = randomJokerCard;
	}

	public int getSystemChallengingPoint() {
		return systemChallengingPoint;
	}

	public void setSystemChallengingPoint(int systemChallengingPoint) {
		this.systemChallengingPoint = systemChallengingPoint;
	}

	public Card getOpenCard() {
		return openCard;
	}

	public void setOpenCard(Card openCard) {
		this.openCard = openCard;
	}

	public Game() {
		super();
		this.players = new ArrayList<>();
		this.deck = new Deck();
		chooseSystemChallengingPoint();
	}

	public void restart() {
		chooseSystemChallengingPoint();
		deck.resetDeck();
	}

	private void chooseSystemChallengingPoint() {
		systemChallengingPoint = RandomGenerator.generateRandomNumber(1, 50);
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

	public void pickJokerCard() {
		double num = deck.getCards().size();
		int randomInt = (int) (num * Math.random());
		randomJokerCard = deck.getCards().get(randomInt);
		deck.getCards().remove(randomInt);
	}

	public Card pickCardFromDeck() {
		Card card = null;
		if (deck.getCards().size() > 0) {
			card = deck.getCards().get(0);
			deck.getCards().remove(0);
			deck.addDroppedCard(openCard);
		} else {
			System.out.println("Deck is empty, reshuffling cards...");
			deck.reshuffle();
			pickCardFromDeck();
		}
		return card;
	}

	public int calculatePoints(Player player) {
		int points = 0;
		for (int i = 0; i < player.getCards().size(); i++) {
			if (!randomJokerCard.getCode().equals(player.getCards().get(i).getCode()))
				points = points + player.getCards().get(i).getValue();
		}
		return points;
	}

	public Card getBiggestCard(Player player) {
		if (player.getCards().size() == 0)
			return null;
		Card card = player.getCards().get(0);
		for (int i = 1; i < player.getCards().size(); i++) {
			Card card1 = player.getCards().get(i);
			if (card1.getValue() >= card.getValue() && !card.getCode().equals(randomJokerCard.getCode())
					&& !card1.getCode().equals(randomJokerCard.getCode())) {
				card = card1;
			}
		}
		return card;
	}

	public List<Card> getCardsSameOfOpenCard(Player systemPlayer) {
		List<Card> sameCards = new ArrayList<>();
		for (int i = 0; i < systemPlayer.getCards().size(); i++) {
			Card card = systemPlayer.getCards().get(i);
			if (card.getCode().equals(openCard.getCode())) {
				sameCards.add(card);
			}
		}
		return sameCards;
	}

	public boolean hasSameCards(Player player) {
		boolean flag = false;
		List<Card> cards = getCardsSameOfOpenCard(player);
		if (cards.size() > 0)
			flag = true;
		return flag;
	}

	public boolean hasRummyCards(Player player) {
		return player.hasRummyCards();
	}
}
