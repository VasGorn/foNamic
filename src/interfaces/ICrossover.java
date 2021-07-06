package interfaces;

import algorithm.genetic.Individual;

public interface ICrossover {
	public Individual crossover(Individual parentA, Individual parentB);
}
