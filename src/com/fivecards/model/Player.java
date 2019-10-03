package com.fivecards.model;

import java.util.List;

public class Player {

    private String name;
    private int id;
    private List<Card> cards;

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

    
}