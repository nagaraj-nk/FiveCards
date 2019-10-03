package com.fivecards.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Deck {

	private static final String[] TYPES = { "H", "D", "S", "C" };
	private static final String[] CODES = { "A", "J", "Q", "K" };

	private Map<String, Integer> codeValueMap = new HashMap<>();
	private List<Card> deckCards;
	private List<Card> droppedCards;

	public List<Card> getDroppedCards() {
		return droppedCards;
	}

	public void setDroppedCards(List<Card> droppedCards) {
		this.droppedCards = droppedCards;
	}
	
	public void addDroppedCard(Card card) {
		this.droppedCards.add(card);
	}

	public Deck() {
		this.deckCards = new ArrayList<>();
		this.droppedCards = new ArrayList<>();
		initCodeValueMap();
		prepareDeck();
		shuffleCards();
	}

	public void initCodeValueMap() {
		codeValueMap.put(CODES[0], 1);
		codeValueMap.put(CODES[1], 10);
		codeValueMap.put(CODES[2], 10);
		codeValueMap.put(CODES[3], 10);
	}

	public int getCodeValue(String key) {
		return codeValueMap.get(key);
	}

	public List<Card> prepareDeck() {
		List<Card> cards = new ArrayList<>();

		for (int i = 0; i < TYPES.length; i++) {
			createNumericCard(TYPES[i]);
			createSpecialCard(TYPES[i]);
		}
		
		addJokerCard();
		return cards;
	}

	private void addJokerCard() {
		Card jokerCard = new Card();
		jokerCard.setCode("0-Joker");
		jokerCard.setType("");
		jokerCard.setValue(0);
		
		deckCards.add(jokerCard);
	}

	public void shuffleCards() {
		Collections.shuffle(deckCards);
	}

	private void createSpecialCard(String type) {
		for (int i = 0; i < CODES.length; i++) {
			Card card = new Card();
			card.setCode(CODES[i]);
			card.setValue(getCodeValue(CODES[i]));
			card.setType(type);
			deckCards.add(card);
		}
	}

	private void createNumericCard(String type) {
		for (int i = 2; i <= 10; i++) {
			Card card = new Card();
			card.setValue(i);
			card.setType(type);
			card.setCode(String.valueOf(i));
			deckCards.add(card);
		}
	}

	public List<Card> getCardsForPlayer() {
		List<Card> cards = new ArrayList<>();

		Iterator<Card> iterator = deckCards.iterator();
		int count = 0;

		while (iterator.hasNext()) {
			Card card = iterator.next();
			cards.add(card);
			iterator.remove();

			count++;
			if (count == Game.CARD_COUNT) {
				break;
			}
		}

		return cards;
	}

	public List<Card> getCards() {
		return deckCards;
	}

	public void setCards(List<Card> cards) {
		this.deckCards = cards;
	}

	public void showDeck() {
		for (Card card : deckCards) {
			System.out.println(card.getDisplayNumber());
		}
	}

	public void reshuffle() {
		Collections.shuffle(droppedCards);
		deckCards = droppedCards;
		droppedCards = new ArrayList<>();
	}
}