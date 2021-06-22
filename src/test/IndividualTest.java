package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import algorithm.genetic.Individual;
import beans.CoefficientParameter;

class IndividualTest {
	private static Individual individual;
	private static final double DELTA = 1e-09;
	
	@BeforeAll
	public static void setupAll() {
		CoefficientParameter[] coefParamArray = createCoefficientParameters();
		Individual.setGenotypes(coefParamArray);
		
		individual = createIndividualWithOwnSetOfGenes();
	}
	
	public static CoefficientParameter[] createCoefficientParameters() {
		CoefficientParameter coefParam1 = new CoefficientParameter(1.2, 5.4, 0.1);
		CoefficientParameter coefParam2 = new CoefficientParameter(0.1, 3.8, 0.2);
		CoefficientParameter coefParam3 = new CoefficientParameter(0.1, 0.8, 0.02);
		
		CoefficientParameter[] coefParamArray = new CoefficientParameter[3];
		coefParamArray[0] = coefParam1;
		coefParamArray[1] = coefParam2;
		coefParamArray[2] = coefParam3;

		return coefParamArray;
	}
	
	public static Individual createIndividualWithOwnSetOfGenes() {
		Individual newIndividual = Individual.createRandomIndividual();
		for(int i = 0; i < Individual.getGeneLength(); ++i) {
			if(i < 6) {
				newIndividual.setGene(i, (byte) 1);
				continue;
			}
			if(i < 11) {
				newIndividual.setGene(i, (byte) 0);
				continue;
			}
			newIndividual.setGene(i, (byte) 1);
		}
		return newIndividual;
	}
	
	@Test
	@DisplayName("Check number of genotypes in individual")
	public void checkNumberOfGenotypesInIndividual() {
		assertEquals(3, Individual.getGenotypeCount());
	}
	
	@Test
	@DisplayName("Check total number of genes")
	public void checkTotalNumberOfGenes() {
		assertEquals(17, Individual.getGeneLength());
	}
	
	@DisplayName("Repeat random Individual createion 100 times to check gene array")
	@RepeatedTest(100)
	public void repeatCreateIndividualToCheckGeneArray() {
		Individual newIndividual = Individual.createRandomIndividual();
		byte[] genes = newIndividual.getGenes();
		for(int i = 0; i < Individual.getGeneLength(); ++i) {
			byte gene = genes[i];
			assertTrue(gene == 0 || gene == 1);
		}
	}
	
	@Test
	@DisplayName("Convert Individual to string")
	public void convertIndiviudalToString() {
		assertEquals("111111 00000 111111", individual.toString());
	}
	
	@DisplayName("Get coeficient array of individual")
	@ParameterizedTest
	@CsvSource({
		"5.4, 0",
		"0.1, 1",
		"0.8, 2"
	})
	public void checkCoefficientArrayOfIndividual(double value, int index){
		double[] coefficientArray = individual.getDecodeArray();
		assertEquals(value, coefficientArray[index], DELTA);
	}
}
