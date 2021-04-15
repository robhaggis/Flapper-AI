package com.haggis.graphics;

import java.util.ArrayList;

import com.haggis.utils.Rand;

public class GA {

	static ArrayList<Bird> nextGeneration(ArrayList<Bird> oldG) {

		calculateFitness(oldG);

		ArrayList<Bird> newG = new ArrayList<Bird>();
		for (int i = 0; i < Game.POPULATIONSIZE; i++) {
			newG.add(pickOne(oldG));
		}
		return newG;
	}

	static Bird pickOne(ArrayList<Bird> pop) {
		int index = 0;
		float r = (float) Math.random();
		while (r > 0) {
			r -= pop.get(index).fitness;
			index++;
		}
		index--;
		Bird b = pop.get(index);
		Bird child = new Bird(b.brain);
		child.mutate();
		return child;
	}

	static void calculateFitness(ArrayList<Bird> oldG) {
		int sum = 0;
		for (Bird b : oldG) {
			sum += b.score;
		}

		for (Bird b : oldG) {
			b.fitness = b.score / sum;
		}
	}
}
