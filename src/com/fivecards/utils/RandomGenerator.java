package com.fivecards.utils;

import java.util.Random;

public class RandomGenerator {

	public static int generateRandomNumber(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
