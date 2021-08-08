package interfaces;

import algorithm.genetic.Population;

public interface ISelection {
	public Population getParentPopulation(Population pop);
}
