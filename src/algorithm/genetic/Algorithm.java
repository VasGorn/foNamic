package algorithm.genetic;

import interfaces.ICrossover;
import interfaces.ISelection;

public class Algorithm {
	private ISelection selection;
	private ICrossover crossover;
	private boolean elitism;
	private double mutationRate;
	
	public Algorithm(ISelection selection, ICrossover crossover, boolean elitism,
			double mutationRate) {
		this.selection = selection;
		this.crossover = crossover;
		this.elitism = elitism;
		this.mutationRate = mutationRate;
	}
	
	public Population evolvePopulation(Population pop) {
		Population newGeneration = new Population(pop.size(), false);
		
		if(elitism) newGeneration.saveIndividual(0, pop.getFittest());
		int startIndex = elitism ? 1 : 0;
		
		Population parentPop = selection.getParentPopulation(pop);
		
		int random;
		for(int i = startIndex; i < pop.size(); ++i) {
			random = randomWithRange(0, pop.size() - 1);
			Individual parentA = parentPop.getIndividual(random);
			
			random = randomWithRange(0, pop.size() - 1);
			Individual parentB = parentPop.getIndividual(random);
			
			Individual offspring = crossover.crossover(parentA, parentB);
			newGeneration.saveIndividual(i, offspring);
		}
		
		for(int i = startIndex; i < newGeneration.size(); ++i) {
			mutate(newGeneration.getIndividual(i));
		}
		
		return newGeneration;
	}
	
	private void mutate(Individual indiv) {
		int size = Individual.getGeneLength();
		for(int i = 0; i < size; ++i) {
			if(Math.random() <= mutationRate) {
				byte gene = indiv.getGene(i);
				byte mutateGene = (byte)((gene == 0) ? 1 : 0);
				indiv.setGene(i, mutateGene);
			}
		}
	}
	
	private int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range + min);
	}

}
