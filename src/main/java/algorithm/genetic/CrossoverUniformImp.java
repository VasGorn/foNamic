package algorithm.genetic;

import interfaces.ICrossover;

public class CrossoverUniformImp implements ICrossover{
	private double uniformRate = 0.5;

	public CrossoverUniformImp(double uniformRate) {
		this.uniformRate = uniformRate;
	}

	public Individual crossover(Individual parentA, Individual parentB) {
		Individual offspring = Individual.createIndividual();
		int genotypeCount = Individual.getGenotypeCount();
		Genotype[] genotypeArray = Individual.getGenotypes();
		int offset = 0;
		for (int j = 0; j < genotypeCount; ++j) {
			int length = genotypeArray[j].getLength();
			for (int i = offset; i < (offset + length); ++i) {
				if (Math.random() <= uniformRate) {
					offspring.setGene(i, parentA.getGene(i));
				} else {
					offspring.setGene(i, parentB.getGene(i));
				}
			}
			offset += length;
		}
		return offspring;
	}
}
