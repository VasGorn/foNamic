package beans;

import algorithm.genetic.Individual;

public class ChampionRecord {
	private int numberOfGeneration;
	private double fitness;
	private double[] paramArray;

	public ChampionRecord(int numberOfGeneration, Individual indiv) {
		this.numberOfGeneration = numberOfGeneration;
		this.fitness = indiv.getFitness();
		this.paramArray = indiv.getDecodeArray(); 
	}
	
	public ChampionRecord(int numberOfGeneration, double fitness, double[] paramArray) {
		this.numberOfGeneration = numberOfGeneration;
		this.fitness = fitness;
		this.paramArray = paramArray;
	}

	public int getNumberOfGeneration() {
		return numberOfGeneration;
	}

	public void setNumberOfGeneration(int numberOfGeneration) {
		this.numberOfGeneration = numberOfGeneration;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double[] getParamArray() {
		return paramArray;
	}

	public void setParamArray(double[] paramArray) {
		this.paramArray = paramArray;
	}

}
