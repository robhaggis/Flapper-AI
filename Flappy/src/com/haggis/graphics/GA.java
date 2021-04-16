package com.haggis.graphics;

public class GA {

	static void nextGeneration() {
		Game.Gens++;
		calculateFitness();
		for (int i = 0; i < Game.TOTAL; i++) {
			Game.population.add(pickOne());
		}
		Game.saved.clear();
	}

	static Bird pickOne(){
		  int index = 0;
		  float r = (float) Math.random();
		  while (r > 0) {
		    r -= Game.saved.get(index).fitness;
		    index++;
		  }
		  index--;
		  Bird b = Game.saved.get(index);
		  Bird child = new Bird(b.brain);
		  child.mutate();
		  return child;
		}

	static void calculateFitness() {
		float sum = 0;
		for (Bird b : Game.saved) {
			sum += b.score;
		}

		for (Bird b : Game.saved) {
			b.fitness = b.score / sum;
		}
	}
}
