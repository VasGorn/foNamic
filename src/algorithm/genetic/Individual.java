package algorithm.genetic;

import beans.CoefficientParameter;

public class Individual {
	private static Genotype[] genotypes;
	private static int genotypeCount;
	private static int geneLength;

	private byte[] genes;
	private double fitness;
	
	private Individual() {
		this.genes = new byte[geneLength];
		this.fitness = -1.0;
	}

	public static Individual createIndividual() {
		Individual newIndividual = new Individual();
		return newIndividual;
	}
	
	public static Individual createRandomIndividual() {
		Individual newIndividual = new Individual();
		for(int i = 0; i < geneLength; ++i) {
			byte gene = (byte) Math.round(Math.random());
			newIndividual.setGene(i, gene);
		}
		return newIndividual;
	}
	
	public static void setGenotypes(CoefficientParameter[] paramCoefArray) {
		genotypeCount = paramCoefArray.length;
		genotypes = new Genotype[genotypeCount];
		
		geneLength = 0;
		for(int i = 0; i < genotypeCount; ++i) {
			Genotype subGenotype = new Genotype(paramCoefArray[i]);
			geneLength += subGenotype.getLength();

			genotypes[i] = subGenotype;
		}
	}
	
	public static int getGenotypeCount() { return genotypeCount; }

	public static Genotype[] getGenotypes() { return genotypes; }
	
	public static int getGeneLength() { return geneLength; }

	public byte[] getGenes() { return genes; }
	
	public byte getGene(int index) { return genes[index]; }
	
	public void setGene(int index, byte value) {
		genes[index] = value;
		this.fitness = -1.0;
	}
	
	public double getFitness() {
		if(this.fitness < 0.0) {
			// TODO: implement "Fitness" class
			//this.fitness = Fitness.getValue(this);
		}
		return fitness;
	}
	
	public double[] getDecodeArray() {
		double[] array = new double[genotypeCount];
		
		int offset = 0;
		for(int i = 0; i < genotypeCount; ++i) {
			int length = genotypes[i].getLength();
			byte[] genesOfGenotype = getGenesOfGenotype(offset, length);
			
			array[i] = genotypes[i].decodeGenotype(genesOfGenotype);
			offset += length;
		}
		return array;
	}
	
	private byte[] getGenesOfGenotype(int offset, int length){
		byte[] subGene = new byte[length];
		int j = 0;
		for(int i = offset; i < (length + offset); ++i) {
			subGene[j]= genes[i];
			++j;
		}
		return subGene;
	}
	
	@Override
	public String toString() {
		String geneString = "";
		
		int offset = 0;
		for(int i = 0; i < genotypeCount; ++i) {
			int length = genotypes[i].getLength();
			if (i != 0) geneString += " ";
			
			byte[] genes = getGenes();
			for(int j = offset; j < (offset + length); ++j) {
				geneString += genes[j];
			}
			offset += length;
		}
		return geneString;
	}

}
