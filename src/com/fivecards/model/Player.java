package com.fivecards.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

	private String name;
	private int id;
	private List<Card> cards;
	private int points;

	@Override
	public String toString() {
		return "Player [name=" + name + ", id=" + id + ", cards=" + cards + ", points=" + points + "]";
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void updatePoints(int points) {
		setPoints(this.points + points);
	}

	public Player() {
		super();
	}

	public Player(String name, int id, List<Card> cards) {
		this.name = name;
		this.id = id;
		this.cards = cards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void dropCard(Card droppingCard) {
		Iterator<Card> iterator = cards.iterator();
		while (iterator.hasNext()) {
			Card card = iterator.next();
			if (card.getDisplayNumber().equals(droppingCard.getDisplayNumber())) {
				iterator.remove();
				break;
			}
		}
	}

	public List<Card> getRummyCards() {
		List<Card> rummyCards = new ArrayList<>();
		Card bigCard = null;
		bigCard = cards.get(cards.size() - 1);

		for (int index = cards.size() - 2; index >= 0; index--) {
			if (bigCard.getCode().equals(cards.get(index).getCode())) {
				rummyCards.add(cards.get(index));
			} else {
				if (rummyCards.size() > 0) {
					break;
				} else {
					bigCard = cards.get(index);
				}
			}
		}

		if (rummyCards.size() >= 1) {
			rummyCards.add(bigCard);
		}
		return rummyCards;
	}

	public boolean hasRummyCards() {
		boolean flag = false;
		if (getRummyCards().size() > 0)
			flag = true;
		return flag;
	}
}