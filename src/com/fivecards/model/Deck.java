package com.fivecards.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck {

    private static final String[] TYPES = { "H", "D", "S", "C" };
    private static final String[] CODES = { "A", "J", "Q", "K" };

    private Map<String, Integer> codeValueMap = new HashMap<>();
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initCodeValueMap();
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
        return cards;
    }
    
    public void shuffleCards() {
    	Collections.shuffle(cards);
    }

    private void createSpecialCard(String type) {
        for (int i = 0; i < CODES.length; i++) {
            Card card = new Card();
            card.setCode(CODES[i]);
            card.setValue(getCodeValue(CODES[i]));
            card.setType(type);
            cards.add(card);
        }
    }

    private void createNumericCard(String type) {
        for (int i = 2; i <= 10; i++) {
            Card card = new Card();
            card.setValue(i);
            card.setType(type);
            card.setCode(String.valueOf(i));
            cards.add(card);
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void showDeck() {
        for (Card card : cards) {
            System.out.println(card.getDisplayNumber());
        }
    }
}