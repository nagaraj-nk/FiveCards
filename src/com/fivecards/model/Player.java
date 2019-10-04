package com.fivecards.model;

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

}