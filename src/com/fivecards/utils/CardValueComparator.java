package com.fivecards.utils;

import java.util.Comparator;

import com.fivecards.model.Card;

public class CardValueComparator implements Comparator<Card> {

	@Override
	public int compare(Card arg0, Card arg1) {
		// TODO Auto-generated method stub
		if (arg0.getValue() > arg1.getValue())
			return 1;
		else if (arg0.getValue() > arg1.getValue())
			return 0;
		else
			return -1;
	}

}
